package org.quizkampen.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class QuizPlayer {
    private Socket socket;
    private ObjectOutputStream out;
    private BufferedReader in;
    private GameService gameService;

    private String lastChosenCategory = "";

    private int score;


    public QuizPlayer(Socket socket, GameService gameService) {
        this.socket = socket;
        this.gameService = gameService;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendResponseToClient(Object response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String receiveFromClient() {
        String message = "";

        try {
            message = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return message;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLastChosenCategory() {
        return lastChosenCategory.toLowerCase();
    }

    public void setLastChosenCategory(String lastChosenCategory) {
        this.lastChosenCategory = lastChosenCategory;
    }
}
