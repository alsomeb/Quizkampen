package org.quizkampen.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
Ver 1:
    Ett simpelt bräde i Swing GUI (Client)
    - 4 knappar, 1 label
    - Current Score
 */
public class Client extends JFrame implements ActionListener {


    private final JPanel mainPanel = new JPanel();
    private final JPanel welcomePanel = new JPanel();
    private final JPanel waitingRoomPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();
    private final JPanel resultPanel = new JPanel();

    private final JLabel waitingRoomMsg = new JLabel("Waiting for 1 more player to connect..");
    private final JLabel welcomeMsg = new JLabel("Welcome to Quizkampen!");
    private final JButton startGameBtn = new JButton("Find a game for me");

    // Network
    private final int port = 12345;
    private Socket socket;
    private final String serverAdress = "127.0.0.1";
    private BufferedReader in;
    private PrintWriter out;
    private String userName;

    public Client() throws IOException {
        socket = new Socket(serverAdress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Welcome Panel
        loadWelcomePanel();

        // Frame
        this.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(welcomePanel, BorderLayout.CENTER);
        this.setSize(800, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Quizkampen");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Den startar server innan man hinner ge namn, fixa något här!
        // Kanske ngt protokoll med statisk counter, när 2st spelare är klara så ladda gamePanel
        listenToServer();
    }

    private void loadWelcomePanel() {
        // Panel Layout
        welcomePanel.setLayout(new GridBagLayout());

        // Label
        welcomeMsg.setFont(new Font("Sans-serif", Font.BOLD, 22));

        // Button
        startGameBtn.setFocusable(false);
        startGameBtn.setFont(new Font("Sans-serif", Font.BOLD, 22));
        startGameBtn.addActionListener(this);

        welcomePanel.add(welcomeMsg);
        welcomePanel.add(Box.createHorizontalStrut(15));
        welcomePanel.add(startGameBtn);
    }

    private void loadWaitingRoomPanel() {

        mainPanel.removeAll();
        mainPanel.add(waitingRoomPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
        waitingRoomPanel.setLayout(new BorderLayout());
        waitingRoomPanel.add(waitingRoomMsg, BorderLayout.CENTER);
        waitingRoomMsg.setFont(new Font("Sans-serif", Font.BOLD, 22));
        waitingRoomMsg.setHorizontalAlignment(JLabel.CENTER);
        waitingRoomMsg.setVerticalAlignment(JLabel.CENTER);
    }

    private void listenToServer() throws IOException {
        String msgFromServer;
        while ((msgFromServer = in.readLine()) != null) {
            System.out.println(msgFromServer);
            // MESSAGE All players connected
            if(msgFromServer.equalsIgnoreCase("MESSAGE All players connected")) {
                loadGamePanel();
            }

        }
    }

    private void loadGamePanel(){
        mainPanel.removeAll();
        mainPanel.add(gamePanel);

        welcomeMsg.setText("Nu börjar spelet! Lycka till");
        gamePanel.add(welcomeMsg);
    }

    private String prompt(String messageInPrompt) {
        boolean run = true;
        while (run) {
            String name = JOptionPane.showInputDialog(messageInPrompt);
            if (name == null) {
                run = false;
            } else if (name.isBlank()) {
                JOptionPane.showMessageDialog(null, "Namn Får ej va tomt!");
            } else {
                return name;
            }
        }
        return null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameBtn) {
            userName = prompt("Enter player name");
            if (userName != null) {
                // Felhantering görs innan detta nedan
                loadWaitingRoomPanel();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
