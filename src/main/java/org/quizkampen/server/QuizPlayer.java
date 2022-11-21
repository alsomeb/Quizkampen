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

    private int currentScore;
    private int totalScore;

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
            System.out.println(message);
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
}
