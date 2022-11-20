package org.quizkampen.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class QuizGui extends JFrame implements ActionListener {
    private final JPanel mainPanel = new JPanel();
    private final JPanel welcomePanel = new JPanel();
    private final JPanel waitingRoomPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();
    private final JPanel resultPanel = new JPanel();

    private final JLabel waitingRoomMsg = new JLabel("Waiting for 1 more player");
    private final JLabel welcomeMsg = new JLabel("Welcome to Quizkampen!");
    private final JButton startGameBtn = new JButton("Find a game for me");

    public QuizGui() {
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
    }

    public void loadWelcomePanel() {
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

    public void loadWaitingRoomPanel() {

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

    public void loadGamePanel() {
        mainPanel.removeAll();
        mainPanel.add(gamePanel);

        welcomeMsg.setText("Game starts now!");
        gamePanel.add(welcomeMsg);
    }

    public void loadDisconnectMsg() {
        welcomeMsg.setText("The other player disconnected during game start, please restart");
        welcomeMsg.setForeground(Color.RED);
        startGameBtn.setVisible(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameBtn) {
            loadWaitingRoomPanel();
        }
    }
}