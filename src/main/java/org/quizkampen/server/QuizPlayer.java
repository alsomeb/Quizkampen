package org.quizkampen.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizPlayer {
    private Socket socket;
    private ObjectOutputStream out;
    private BufferedReader in;
    private GameService gameService; // TODO används ej ?

    private String lastChosenCategory = "";

    private int currentScore;

    private List<Integer> totalScore;


    public QuizPlayer(Socket socket, GameService gameService) {
        this.socket = socket;
        this.gameService = gameService; // TODO ANVÄNDER ANLDRIG GS HÄR INNE
        totalScore = new ArrayList<>();

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendResponseToClient(Object response) {
        try {
            out.reset();
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

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public String getLastChosenCategory() {
        return lastChosenCategory.toLowerCase();
    }

    public List<Integer> getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(List<Integer> totalScore) {
        this.totalScore = totalScore;
    }

    public void addScoreToTotal(int score) {
        totalScore.add(score);
    }

    public void setLastChosenCategory(String lastChosenCategory) {
        this.lastChosenCategory = lastChosenCategory;
    }
}
