package org.quizkampen.server;

import java.net.Socket;

public class GameService extends Thread{
    private QuizPlayer quizPlayer1;
    private QuizPlayer quizPlayer2;

    private QuizPlayer currentPlayer;
    private final int INITIAL = 0;
    private final int SENDQUESTIONS = 1;
    private int state = INITIAL;

    // Ladda antal frågor osv

    public GameService(Socket player1Socket, Socket player2Socket){
        quizPlayer1 = new QuizPlayer(player1Socket);
        quizPlayer2 = new QuizPlayer(player2Socket);
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
                state = SENDQUESTIONS;
            }
        }
    }
}
