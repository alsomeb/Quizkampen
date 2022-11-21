package org.quizkampen.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener {
    private final int port = 12345;
    private ServerSocket serverSocket;

    public ServerListener() throws IOException {
        System.out.println("Quizkampen server running on port: " + port);
        serverSocket = new ServerSocket(port);
        while (true) {
            try {
            GameService gameService = new GameService(serverSocket.accept(), serverSocket.accept());
            gameService.start();
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
