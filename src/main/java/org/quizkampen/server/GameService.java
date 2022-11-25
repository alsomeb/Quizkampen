package org.quizkampen.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class GameService extends Thread {

    private Database db = new Database();
    private Properties p = new Properties();
    private Map<String, List<Integer>> playerScores = new HashMap<>();
    private QuizPlayer quizPlayer1;
    private QuizPlayer quizPlayer2;
    private QuizPlayer activePlayer;
    private QuizPlayer nonActivePlayer;
    private int state = 0;
    private int amountOfRounds;
    //private String amountOfRounds;


    public GameService(Socket player1Socket, Socket player2Socket) {
        // TODO LÄS FRÅN PROPS FIL
        quizPlayer1 = new QuizPlayer(player1Socket, this); // TODO ANVÄNDS ENS GAMESERVICE OBJEKT I QUIZPLAYER?
        quizPlayer2 = new QuizPlayer(player2Socket, this);
        System.out.println(player1Socket.getInetAddress().getHostAddress() + " Connected");
        System.out.println(player2Socket.getInetAddress().getHostAddress() + " Connected");
        loadProps();
        amountOfRounds = Integer.parseInt(p.getProperty("amountOfRounds", "2"));

    }

    private void loadProps() {
        try {
            p.load(new FileInputStream("src/main/resources/game.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAllConnected() {
        if (quizPlayer1.getOut() == null) {
            quizPlayer2.sendResponseToClient(new Initiator(false));
            return false;
        }

        if (quizPlayer2.getOut() == null) {
            quizPlayer1.sendResponseToClient(new Initiator(false));
            return false;
        }

        return true;
    }


    // TODO ANTAL RUNDOR SOM MARK SA, FRÅN PROPS FILEN, SÄTTS I CONSTRUCTOR,
    private void gameLoop(QuizPlayer activePlayer) {
        int totalRoundsPlayers = 0;
        boolean roundOver = false;
        String msgFromClient = "";
        QuizPlayer tempPlayer;

        while (true) {
            if (state == 0) {
                System.out.println("PROPS TOTAL ROUNDS TO PLAY: " + amountOfRounds);
                System.out.println("Startar state 0");
                quizPlayer1.sendResponseToClient(new Initiator(true));
                quizPlayer2.sendResponseToClient(new Initiator(true));
                state++;
                System.out.println("testar state initial");
            }

            if (state == 1) {
                System.out.println("Startar state 1");
                activePlayer.sendResponseToClient(new Response(db.getCategories()));
                activePlayer.sendResponseToClient("Skickat kategorier");
                System.out.println("Skickat kategorier");
                state++;
            }

            if (state == 2) {
                System.out.println("Startar state 2");
                msgFromClient = activePlayer.receiveFromClient();
                System.out.println("Spelaren valde " + msgFromClient);
                activePlayer.setLastChosenCategory(msgFromClient);
                state++;
            }

            if (state == 3) {
                if (!roundOver) { //nonActivePlayer == null
                    msgFromClient = msgFromClient.toLowerCase();
                    System.out.println("Startar state 3, skickar Frågor till spelaren");
                    switch (msgFromClient) {
                        case "litteraturkonst" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.LITTERATURKONST)));
                        case "programmering" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.PROGRAMMERING)));
                        case "geografi" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.GEOGRAFI)));
                        case "historia" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.HISTORIA)));
                    }
                } else {
                    switch (nonActivePlayer.getLastChosenCategory()) {
                        case "litteraturkonst" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.LITTERATURKONST)));
                        case "programmering" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.PROGRAMMERING)));
                        case "geografi" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.GEOGRAFI)));
                        case "historia" ->
                                activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.HISTORIA)));
                    }
                    roundOver = false;
                }
                state++;
            }

            if (state == 4) {
                msgFromClient = activePlayer.receiveFromClient();
                int activePlayerScore = activePlayer.getCurrentScore();

                if (msgFromClient.equalsIgnoreCase("correct")) {
                    System.out.println(msgFromClient);
                    activePlayerScore++;
                    activePlayer.setCurrentScore(activePlayerScore);
                    System.out.println(activePlayer.getCurrentScore());
                } else if (msgFromClient.equalsIgnoreCase("wrong")) {
                    System.out.println(msgFromClient);
                }

                if (msgFromClient.equalsIgnoreCase("switch") && !roundOver) {
                    if ((totalRoundsPlayers / 2) < amountOfRounds ) {
                        System.out.println(msgFromClient);
                        // Mellan lagring
                        tempPlayer = nonActivePlayer;
                        nonActivePlayer = activePlayer;
                        activePlayer = tempPlayer;
                        state = 3;
                        roundOver = true; // blir true när spelare 1 har svarat
                        totalRoundsPlayers++;
                        System.out.println("Rounds Total: " + totalRoundsPlayers);
                    }
                }

                // om båda spelat 1 gång, ge runda resultat (DENNA HOPPAR IN NU KORREKT)
                if(totalRoundsPlayers % 2 == 0 && roundOver) {
                    System.out.println("MODULUS SKIT BYTER STATE 5");
                    state = 5;
                    roundOver = false; // resetta den boolean
                }

                // Skicka slut res om alla rundor från props är körda (EJ PROVAT, REFACTORISERA STATE 6 ?)
                if ((totalRoundsPlayers / 2) == amountOfRounds) {
                    System.out.println("Byter till state 6");
                    state = 6;
                }
            }

            if (state == 5) {
                System.out.println("State 5");
                // Rundans Poäng
                int currentScorePlayer1 = quizPlayer1.getCurrentScore();
                int currentScorePlayer2 = quizPlayer2.getCurrentScore();
                quizPlayer1.addScoreToTotal(currentScorePlayer1);
                quizPlayer2.addScoreToTotal(currentScorePlayer2);

                // Efter vi sparat rundans score i Map reset den
                quizPlayer1.setCurrentScore(0);
                quizPlayer2.setCurrentScore(0);

                // Totala som skickas
                playerScores.put("Player 1", quizPlayer1.getTotalScore());
                playerScores.put("Player 2", quizPlayer2.getTotalScore());

                quizPlayer1.sendResponseToClient(new Response(true, playerScores));
                quizPlayer2.sendResponseToClient(new Response(true, playerScores));
                System.out.println(quizPlayer1.getTotalScore() + " Player 1 scores");
                System.out.println(quizPlayer2.getTotalScore() + " Player 2 scores");

                System.out.println(msgFromClient);
                if(msgFromClient.equalsIgnoreCase("switch")) {
                    state = 6;
                }
            }
            //Test av commit
            if (state == 6) {
                // Avsluta när alla rundor
                System.out.println("state 6");
                if (amountOfRounds == (totalRoundsPlayers / 2)) {
                    // Hämta total Hashmap och skicka den här till slutskärmen
                    // Readline också hämta svar från client, som säger bara bye
                    // if bye ++ state

                    // Totala scores
                    quizPlayer1.sendResponseToClient(new Response(playerScores));
                    quizPlayer2.sendResponseToClient(new Response(playerScores));

                    state++;
                } else {
                    //reset spel, skicka kanske ngn response till activePlayer att den skall välja kategori!
                    // medans innactive player får resultat samt waiting screen
                    tempPlayer = nonActivePlayer;
                    nonActivePlayer = activePlayer;
                    activePlayer = tempPlayer;
                    roundOver = false;
                    state = 0;
                }
            }
        }
    }

    @Override
    public void run() {
        if (checkAllConnected()) {
            activePlayer = quizPlayer1;
            nonActivePlayer = quizPlayer2;
            gameLoop(activePlayer);
        }
    }
}

