package org.quizkampen.client;

import org.quizkampen.server.Initiator;
import org.quizkampen.server.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private QuizGui gui;
    private final int port = 12345;
    private Socket socket;
    private final String serverAdress = "127.0.0.1";
    private PrintWriter out;
    private ObjectInputStream in;

    public Client() throws IOException, ClassNotFoundException {
        gui = new QuizGui();
        runClient();
    }

    private void runClient() throws IOException, ClassNotFoundException {
        socket = new Socket(serverAdress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new ObjectInputStream(socket.getInputStream());
        // lägger in PrintWriter i GUI så vi kan skicka där på actionListener
        gui.setOutputStream(out);
        listenToServer();
    }

    private void listenToServer() throws IOException, ClassNotFoundException {
        Object msgFromServer;

        while ((msgFromServer = in.readObject()) != null) {
            if (msgFromServer instanceof Initiator initiator) {
                if (initiator.allConnected()) {
                    System.out.println("All connected, game starts");
                    gui.loadWaitingRoomPanel();
                    gui.setWaitingRoomMsg("Waiting for your turn");
                } else {
                    gui.loadDisconnectMsg();
                    System.out.println("Other part Disconnected, restart this client");
                }
            }

            if (msgFromServer instanceof Response response) {
                if(response.isGameIsOver()) {
                    gui.setTotalEndScore(response.getTotalScore());
                    gui.loadGameOverPanel();
                    System.out.println("Slut res received");
                }

                if (response.getCategories() != null && !response.isGameIsOver()) {
                    gui.setCategories(response.getCategories());
                    gui.loadCategoryPanel();
                    System.out.println(response.getCategories());
                }
                if (response.getQuestions() != null && !response.isGameIsOver()) {
                    System.out.println(response.getQuestions());
                    gui.setCurrentQuestions(response.getQuestions());
                    gui.loadGamePanel();
                }
                if(response.roundIsOver() && !response.isGameIsOver()) {
                    if(response.getPlayerScores() != null) {
                        gui.setPlayerScore(response.getPlayerScores());
                        System.out.println(response.getPlayerScores());
                        gui.loadResultPanel();
                        try {
                            Thread.sleep(5000);
                            gui.resetScoreArea();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Resettar questionCounter");
                        gui.setQuestionCounter(0); // resetta counter på questionCounter
                        gui.getCurrentQuestions().getCurrentQuestions().removeAll(gui.getCurrentQuestions().getCurrentQuestions()); // resettar listan ?
                        System.out.println(gui.getCurrentQuestions().getCurrentQuestions());
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
