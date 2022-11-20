package org.quizkampen.client;


import org.quizkampen.server.Initiator;

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

        listenToServer();
    }

    private void listenToServer() throws IOException, ClassNotFoundException {
        Object msgFromServer;
        while ((msgFromServer = in.readObject()) != null) {
            if (msgFromServer instanceof Initiator initiator) {
                if(initiator.allConnected()) {
                    System.out.println("All connected, game starts");
                    gui.loadGamePanel();
                } else {
                    gui.loadDisconnectMsg();
                    System.out.println("Other part Disconnected, restart this client");
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
