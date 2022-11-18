package org.quizkampen.server;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerListener {
    private ServerSocket serverSocket;
    private final int port = 12345;


    public ServerListener() throws IOException {
        serverSocket = new ServerSocket(port);
        startServer();
    }

    private void startServer() {
        System.out.println("Quizkampen server running on port: " + port);
        while (true) {
            try {
                // serverSocket.accept() == returns the new Socket for Player X
                ServerSidePlayer player1 = new ServerSidePlayer(serverSocket.accept(), 1);
                ServerSidePlayer player2 = new ServerSidePlayer(serverSocket.accept(), 2);

                player1.start();
                player2.start();

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new ServerListener();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

