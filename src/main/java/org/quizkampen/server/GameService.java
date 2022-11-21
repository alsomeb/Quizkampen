package org.quizkampen.server;

import java.net.Socket;

public class GameService extends Thread{

    private Database db = new Database();
    private QuizPlayer quizPlayer1;
    private QuizPlayer quizPlayer2;

    private int currentPlayer = 1;
    private int state = 0;

    // Ladda antal frågor osv

    public GameService(Socket player1Socket, Socket player2Socket){
        quizPlayer1 = new QuizPlayer(player1Socket, this);
        quizPlayer2 = new QuizPlayer(player2Socket, this);
        System.out.println(player1Socket.getInetAddress().getHostAddress() + " Connected");
        System.out.println(player2Socket.getInetAddress().getHostAddress() + " Connected");
    }

    private boolean checkAllConnected() {
        if(quizPlayer1.getOut() == null) {
            quizPlayer2.sendResponseToClient(new Initiator(false));
            return false;
        }

        if (quizPlayer2.getOut() == null) {
            quizPlayer1.sendResponseToClient(new Initiator(false));
            return false;
        }

        return true;
    }

/*
    private String processInput(QuizPlayer quizPlayer) {
        String fromClient = null;

*/
/*        if(state == SENDCATEGORIES) {
            quizPlayer1.sendResponseToClient(new Response(db.getCategories()));
            String category = quizPlayer1.receiveFromClient();
            if(category != null) {
                System.out.println(category);
                state = SENDQUESTION;
                System.out.println("testar state initial");
            }
        } else if (state == SENDQUESTION) {
            System.out.println("Skickar frågor");
        }

        return fromClient;*//*

    }

*/


    @Override
    public void run() {
        if(checkAllConnected()) {
            String msgFromClient = "";

            while (true) {
                if (state == 0) {
                    System.out.println("Startar state 0");
                    quizPlayer1.sendResponseToClient(new Initiator(true));
                    quizPlayer2.sendResponseToClient(new Initiator(true));
                    state++;
                    System.out.println("testar state initial");
                }

                if (state == 1) {
                    System.out.println("Startar state 1");
                    quizPlayer1.sendResponseToClient(new Response(db.getCategories()));
                    quizPlayer1.sendResponseToClient("Skickat kategorier");
                    System.out.println("Skickat kategorier");
                    state++;
                }

                if(state == 2) {
                    System.out.println("Startar state 2");
                    msgFromClient = quizPlayer1.receiveFromClient();
                    System.out.println(msgFromClient);
                    state++;
                }

                if(state == 3) {
                    msgFromClient = msgFromClient.toLowerCase();
                    System.out.println("Startar state 3");
                    switch (msgFromClient) {
                        case "dans" -> db.getDans().forEach(question -> quizPlayer1.sendResponseToClient(new Response(question)));
                        case "programmering" -> db.getProgrammering().forEach(question -> quizPlayer1.sendResponseToClient(new Response(question)));
                        case "geografi" -> db.getGeografi().forEach(question -> quizPlayer1.sendResponseToClient(new Response(question)));
                        case "hundar" -> db.getHundar().forEach(question -> quizPlayer1.sendResponseToClient(new Response(question)));
                    }
                    state++;
                }



            }
        }
    }
}
