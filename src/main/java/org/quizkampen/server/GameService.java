package org.quizkampen.server;

import java.net.Socket;

public class GameService extends Thread{

    private Database db = new Database();
    private QuizPlayer quizPlayer1;
    private QuizPlayer quizPlayer2;

    private boolean player1Turn;
    private final int INITIAL = 0;
    private final int SENDCATEGORIES = 1;
    private final int SENDQUESTION = 2;
    private int state = INITIAL;

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



    @Override
    public void run() {
        if(checkAllConnected()) {
            if(state == INITIAL) {
                quizPlayer1.sendResponseToClient(new Initiator(true));
                quizPlayer2.sendResponseToClient(new Initiator(true));
                state = SENDCATEGORIES;
                player1Turn = true;
                System.out.println("testar state initial");
            }

            while (player1Turn) {
                if(state == SENDCATEGORIES) {
                    quizPlayer1.sendResponseToClient(new Response(db.getCategories()));
                    String category = quizPlayer1.receiveFromClient();
                    if(category != null) {
                        System.out.println(category);
                        state = SENDQUESTION;
                        System.out.println("testar state initial");
                    }
                }
                if(state == SENDQUESTION) {
                    System.out.println("Skickar frågor");
                }
                player1Turn = false;
            }
        }
    }
}
