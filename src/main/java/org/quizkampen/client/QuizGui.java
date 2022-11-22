package org.quizkampen.client;

import org.quizkampen.server.Question;
import org.quizkampen.server.Questions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
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
    private final JButton categoryFourButton = new JButton("Category 4");

    private List<String> categories;

    private PrintWriter outputStream;


    private Questions currentQuestions;

    private int questionCounter;
    private int totalQuestions; // TODO ANVÄND PROPERTY VAR HÄR SOM STANNAR EFTER X ANTAL

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
        categoryPanel.add(categoryFourButton);

        Collections.shuffle(categories);
        categoryOneButton.setText(categories.get(0));
        categoryTwoButton.setText(categories.get(1));
        categoryThreeButton.setText(categories.get(2));
        categoryFourButton.setText(categories.get(3));


        categoryOneButton.addActionListener(this);
        categoryTwoButton.addActionListener(this);
        categoryThreeButton.addActionListener(this);
        categoryFourButton.addActionListener(this);
    }

    public void loadGamePanel() {
        mainPanel.removeAll();
        mainPanel.add(gamePanel);

        welcomeMsg.setText("Game starts now!");
        gamePanel.setLayout(new GridBagLayout());

        // Fråga Text
        List<Question> allQuestions = currentQuestions.getCurrentQuestions();
        String questionText = allQuestions.get(questionCounter).getQuestionText();
        questionLabel.setText(questionText);
        gamePanel.add(questionLabel);

        // Alternativ Listan
        List<String> allaAlternativ = allQuestions.get(questionCounter).getAnswers();
        Collections.shuffle(allaAlternativ);

        // Loopar igenom alla alternativ och målar upp knapparna
        for (int i = 0; i < allaAlternativ.size(); i++) {
            JButton alternativBtn = new JButton();
            alternativBtn.setText(allaAlternativ.get(i));
            alternativBtn.addActionListener(this);
            gamePanel.add(alternativBtn);
            System.out.println("Lägger till knapp: " + allaAlternativ.get(i));
            repaint();
            revalidate();
        }

        // TODO VI KANSKE BEHÖVER RÄTTA SVARET SOM GLOBAL VARIABEL, FÖR ATT KOMMA ÅT DEN I LYSSNAREN
    }

    public void loadDisconnectMsg() {
        welcomeMsg.setText("The other player disconnected during game start, please restart");
        welcomeMsg.setForeground(Color.RED);
        startGameBtn.setVisible(false);
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public void setWaitingRoomMsg(String waitingRoomMsg) {
        this.waitingRoomMsg.setText(waitingRoomMsg);
    }


    public void setOutputStream(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }

    public Questions getCurrentQuestions() {
        return currentQuestions;
    }

    public void setCurrentQuestions(Questions currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedBtn = (JButton) e.getSource();

        if (e.getSource() == startGameBtn) {
            loadWaitingRoomPanel();
        }
        if (e.getSource() == categoryOneButton) {
            response = categoryOneButton.getText();
            outputStream.println(response);
            System.out.println(response);
        }
        if (e.getSource() == categoryTwoButton) {
            response = categoryTwoButton.getText();
            outputStream.println(response);
            System.out.println(response);
        }
        if (e.getSource() == categoryThreeButton) {
            response = categoryThreeButton.getText();
            outputStream.println(response);
            System.out.println(response);
        }
        if (e.getSource() == categoryFourButton) {
            response = categoryFourButton.getText();
            outputStream.println(response);
            System.out.println(response);
        }
/*            System.out.println(categoryOneButton.getText());
            String temp = "";
            temp = categoryOneButton.getText();
            loadGamePanel();
            Collections.shuffle(getCategories());
            answerButtonOne.setText("");*/
        }

}

