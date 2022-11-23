package org.quizkampen.server;

import java.net.Socket;

public class GameService extends Thread {

    private Database db = new Database();
    private QuizPlayer quizPlayer1;
    private QuizPlayer quizPlayer2;
    private QuizPlayer activePlayer;
    private QuizPlayer nonActivePlayer;

    private int state = 0;

    private int amountOfRounds = 2;

    // Ladda antal frågor osv

    public GameService(Socket player1Socket, Socket player2Socket) {
        // TODO LÄS FRÅN PROPS FIL
        quizPlayer1 = new QuizPlayer(player1Socket, this);
        quizPlayer2 = new QuizPlayer(player2Socket, this);
        System.out.println(player1Socket.getInetAddress().getHostAddress() + " Connected");
        System.out.println(player2Socket.getInetAddress().getHostAddress() + " Connected");
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
    private void gameLoop(QuizPlayer activePlayer, int amountOfRounds) {
        int currentRound = 0;
        boolean roundOver = false;
        String msgFromClient = "";
        QuizPlayer tempPlayer;

        while (currentRound <= amountOfRounds) {
            if (state == 0) {
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
                        case "litteraturkonst" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.LITTERATURKONST)));
                        case "programmering" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.PROGRAMMERING)));
                        case "geografi" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.GEOGRAFI)));
                        case "historia" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.HISTORIA)));
                    }
                } else {
                    switch (nonActivePlayer.getLastChosenCategory()) {
                        case "litteraturkonst" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.LITTERATURKONST)));
                        case "programmering" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.PROGRAMMERING)));
                        case "geografi" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.GEOGRAFI)));
                        case "historia" -> activePlayer.sendResponseToClient(new Response(db.getQuestionsListByName(DbEnum.HISTORIA)));
                    }
                }
                state++;
            }

            if (state == 4) {
                msgFromClient = activePlayer.receiveFromClient();
                int activePlayerScore = activePlayer.getScore();

                if (msgFromClient.equalsIgnoreCase("correct")) {
                    System.out.println(msgFromClient);
                    activePlayerScore++;
                    activePlayer.setScore(activePlayerScore);
                    System.out.println(activePlayer.getScore());
                }

                if (msgFromClient.equalsIgnoreCase("switch") && !roundOver) {
                    System.out.println(msgFromClient);
                    // Mellan lagring
                    tempPlayer = nonActivePlayer;
                    nonActivePlayer = activePlayer;
                    activePlayer = tempPlayer;
                    state = 3;
                    roundOver = true;
                    currentRound++; // TODO annat ställe kanske ?
                    System.out.println(currentRound);
                }
            }
        }
    }


    @Override
    public void run() {
        if (checkAllConnected()) {
            activePlayer = quizPlayer1;
            nonActivePlayer = quizPlayer2;
            gameLoop(activePlayer, amountOfRounds);

            System.out.println("Spelare 2 tur");
            // byt skärm för pl 2

            activePlayer = quizPlayer2;
            nonActivePlayer = quizPlayer1;
            gameLoop(activePlayer, amountOfRounds);

            // Skicka res till båda spelare och ge dem resultPanel, spela igen ?
            System.out.println("spel slut");

        }
    }
}

