package org.quizkampen.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSidePlayer extends Thread {
    private int player;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ServerSidePlayer(Socket socket, int player) {
        this.socket = socket;
        this.player = player;

        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output.println("WELCOME " + player);
            output.println("MESSAGE Waiting for opponent to connect");
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    @Override
    public void run() {
        try {
            output.println("MESSAGE All players connected");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
