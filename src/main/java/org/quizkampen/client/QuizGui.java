package org.quizkampen.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizGui extends JFrame implements ActionListener {

    private final JPanel mainPanel = new JPanel();
    private final JPanel welcomePanel = new JPanel();
    private final JPanel waitingRoomPanel = new JPanel();
    private final JPanel categoryPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();
    private final JPanel resultPanel = new JPanel();

    private JLabel waitingRoomMsg = new JLabel("Waiting for 1 more player");
    private final JLabel welcomeMsg = new JLabel("Welcome to Quizkampen!");
    private final JButton startGameBtn = new JButton("Find a game for me");

    private final JButton categoryOneButton = new JButton("Category 1");
    private final JButton categoryTwoButton = new JButton("Category 2");
    private final JButton categoryThreeButton = new JButton("Category 3");

    private final JButton answerButtonOne = new JButton("Answer 1");
    private final JButton answerButtonTwo = new JButton("Answer 2");
    private final JButton answerButtonThree = new JButton("Answer 3");
    private final JButton answerButtonFour = new JButton("Answer 4");

    private List<String> categories;

    private boolean hasChosenCategory = false;

    private String response;

    public QuizGui() {
        categories = new ArrayList<>();


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

    public void loadCategoryPanel(){

        mainPanel.removeAll();
        mainPanel.add(categoryPanel);

        welcomeMsg.setText("Choose a category");
        categoryPanel.setLayout(new GridBagLayout());
        categoryPanel.add(categoryOneButton);
        categoryPanel.add(categoryTwoButton);
        categoryPanel.add(categoryThreeButton);

        Collections.shuffle(categories);
        categoryOneButton.setText(categories.get(0));
        categoryTwoButton.setText(categories.get(1));
        categoryThreeButton.setText(categories.get(2));

        categoryOneButton.addActionListener(this);
        categoryTwoButton.addActionListener(this);
        categoryThreeButton.addActionListener(this);
    }

    public void loadGamePanel() {
        mainPanel.removeAll();
        mainPanel.add(gamePanel);

        welcomeMsg.setText("Game starts now!");
        gamePanel.setLayout(new GridBagLayout());

        gamePanel.add(welcomeMsg);
        gamePanel.add(answerButtonOne);
        gamePanel.add(answerButtonTwo);
        gamePanel.add(answerButtonThree);
        gamePanel.add(answerButtonFour);
        answerButtonOne.addActionListener(this);
        answerButtonTwo.addActionListener(this);
        answerButtonThree.addActionListener(this);
        answerButtonFour.addActionListener(this);
        repaint();
        revalidate();
    }

    public void loadDisconnectMsg() {
        welcomeMsg.setText("The other player disconnected during game start, please restart");
        welcomeMsg.setForeground(Color.RED);
        startGameBtn.setVisible(false);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public JLabel getWaitingRoomMsg() {
        return waitingRoomMsg;
    }

    public void setWaitingRoomMsg(String waitingRoomMsg) {
        this.waitingRoomMsg.setText(waitingRoomMsg);
    }

    public JButton getCategoryOneButton() {
        return categoryOneButton;
    }

    public JButton getCategoryTwoButton() {
        return categoryTwoButton;
    }

    public JButton getCategoryThreeButton() {
        return categoryThreeButton;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean hasChosenCategory() {
        return hasChosenCategory;
    }

    public void setHasChosenCategory(boolean hasChosenCategory) {
        this.hasChosenCategory = hasChosenCategory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameBtn) {
            loadWaitingRoomPanel();
        }
        if (e.getSource() == categoryOneButton) {
            hasChosenCategory = true;
            response = categoryOneButton.getText();

        }
/*            System.out.println(categoryOneButton.getText());
            String temp = "";
            temp = categoryOneButton.getText();
            loadGamePanel();
            Collections.shuffle(getCategories());
            answerButtonOne.setText("");*/
        }

}

