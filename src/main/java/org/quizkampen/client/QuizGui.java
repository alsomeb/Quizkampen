package org.quizkampen.client;

import org.quizkampen.server.Question;
import org.quizkampen.server.Questions;
import org.quizkampen.static_variable.CustomCollors;
import org.quizkampen.static_variable.CustomFonts;
import org.quizkampen.static_variable.CustomSizes;
import org.quizkampen.static_variable.Pr;
import org.quizkampen.timerpanel.TimerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class QuizGui extends JFrame implements ActionListener {
    private TimerPanel stopwatch=new TimerPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel welcomePanel = new JPanel();
    private final JPanel waitingRoomPanel = new JPanel();
    private final JPanel categoryPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();
    private final JPanel resultPanel = new JPanel();

    private final JLabel textLabelOne = new JLabel("Waiting for 1 more player");
    private final JLabel textLabelTwo = new JLabel("Welcome to Quizkampen!");
    private final JLabel questionLabel = new JLabel();
    private final JButton startGameBtn = new JButton("Find a game for me");


    private final JButton categoryOneButton = new JButton("Category 1");
    private final JButton categoryTwoButton = new JButton("Category 2");
    private final JButton categoryThreeButton = new JButton("Category 3");
    private final JButton categoryFourButton = new JButton("Category 4");

    private List<String> categories;

    private PrintWriter outputStream;

    private Questions currentQuestions;

    private int questionCounter;
    private int totalQuestions;
    private String response;
    private int amountOfQuestions;

    private Map<String, List<Integer>> playerScore;
    private Properties p = new Properties();

    private JLabel currentScoreLabel = new JLabel();
    private JTextArea currentScoreArea = new JTextArea(40, 40);
    private JScrollPane scrollPain = new JScrollPane(currentScoreArea);


    public QuizGui() {
        loadProps();
        playerScore = new HashMap<>();
        categories = new ArrayList<>();
        amountOfQuestions = Integer.parseInt(p.getProperty("amountOfQuestions", "3"));
        totalQuestions = amountOfQuestions - 1; // PROPS SETTINGS
        // Welcome Panel
        loadWelcomePanel();
        startGameBtn.setBackground(CustomCollors.Beigh);

        // Frame
        this.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(welcomePanel, BorderLayout.CENTER);
        this.setSize(CustomSizes.WIDTH, CustomSizes.HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Quizkampen");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Pr.loadproperty();
    }
    private void loadProps() {
        try {
            p.load(new FileInputStream("src/main/resources/game.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWelcomePanel() {
        // Panel Layout
        welcomePanel.setBackground(CustomCollors.Background_clr);
        welcomePanel.setLayout(new GridBagLayout());
        // Label
        textLabelTwo.setFont(CustomFonts.current_Font_Label);
        // Button
        startGameBtn.setFocusable(false);
        startGameBtn.setFont(CustomFonts.current_Font_Label);
        startGameBtn.addActionListener(this);

        welcomePanel.add(textLabelTwo);
        welcomePanel.add(startGameBtn);
    }

    public void loadWaitingRoomPanel() {
        mainPanel.removeAll();
        mainPanel.add(waitingRoomPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
        waitingRoomPanel.setBackground(CustomCollors.Background_clr);
        waitingRoomPanel.setLayout(new BorderLayout());
        waitingRoomPanel.add(textLabelOne, BorderLayout.CENTER);
        textLabelOne.setFont(CustomFonts.current_Font_Label);
        textLabelOne.setHorizontalAlignment(JLabel.CENTER);
        textLabelOne.setVerticalAlignment(JLabel.CENTER);
        repaint();
        revalidate();
    }
    public void loadResultPanel(){
        mainPanel.removeAll();
        mainPanel.add(resultPanel);
        resultPanel.setBackground(CustomCollors.Background_clr);

        System.out.println(playerScore.get("Player 1"));
        System.out.println(playerScore.get("Player 2"));

        List<Integer> player1Scores = playerScore.get("Player 1");
        List<Integer> player2Scores = playerScore.get("Player 2");

        for (int i = 0; i < player1Scores.size(); i++) {
            currentScoreArea.setBackground((CustomCollors.Background_clr));
            currentScoreArea.setFont(CustomFonts.current_Font_Label);
            currentScoreArea.setLineWrap(true);
            currentScoreArea.setEditable(false);
            currentScoreArea.append("\t"+"\t" +"     Round "+ (i +1 ) + ": " + player1Scores.get(i) + " - " + player2Scores.get(i) + "\n"+"\n"+"\n"+"\n");
            resultPanel.add(scrollPain);
            revalidate();
            repaint();
        }

    }

    public void loadCategoryPanel() {

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(categoryPanel, BorderLayout.CENTER);
        mainPanel.add(textLabelTwo, BorderLayout.NORTH);
        mainPanel.setBackground(CustomCollors.Background_clr);
        textLabelTwo.setText("Välj en kategori");
        textLabelTwo.setHorizontalAlignment(JLabel.CENTER);

        categoryPanel.setBackground(CustomCollors.Background_clr);
        categoryPanel.setLayout(new GridBagLayout());
        categoryPanel.add(categoryOneButton);
        categoryPanel.add(Box.createHorizontalStrut(15));
        categoryPanel.add(categoryTwoButton);
        categoryPanel.add(Box.createHorizontalStrut(15));
        categoryPanel.add(categoryThreeButton);
        categoryPanel.add(Box.createHorizontalStrut(15));
        categoryPanel.add(categoryFourButton);

        categoryOneButton.setFont(CustomFonts.current_Font_Button);
        categoryOneButton.setBackground(CustomCollors.Beigh);
        categoryTwoButton.setFont(CustomFonts.current_Font_Button);
        categoryTwoButton.setBackground(CustomCollors.Beigh);
        categoryThreeButton.setFont(CustomFonts.current_Font_Button);
        categoryThreeButton.setBackground(CustomCollors.Beigh);
        categoryFourButton.setFont(CustomFonts.current_Font_Button);
        categoryFourButton.setBackground(CustomCollors.Beigh);
        Collections.shuffle(categories);
        categoryOneButton.setText(categories.get(0));
        categoryTwoButton.setText(categories.get(1));
        categoryThreeButton.setText(categories.get(2));
        categoryFourButton.setText(categories.get(3));

        categoryOneButton.addActionListener(this);
        categoryTwoButton.addActionListener(this);
        categoryThreeButton.addActionListener(this);
        categoryFourButton.addActionListener(this);
        repaint();
        revalidate();
    }

    public void loadGamePanel() {

        mainPanel.removeAll();
        gamePanel.removeAll();
        mainPanel.setBackground(CustomCollors.Background_clr);
        mainPanel.add(questionLabel, BorderLayout.CENTER);

        mainPanel.add(gamePanel, BorderLayout.SOUTH);
        gamePanel.setBackground(CustomCollors.Background_clr);
        //mainPanel.add(stopwatch,BorderLayout.SOUTH);

        textLabelTwo.setText("Game starts now!");
        gamePanel.setLayout(new GridBagLayout());

        // Fråga Text
        List<Question> allQuestions = currentQuestions.getCurrentQuestions();
        String questionText = allQuestions.get(questionCounter).getQuestionText();
        questionLabel.setText(questionText);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setFont(new Font("Sans-serif", Font.BOLD, 22));

        // Alternativ Listan
        List<String> allaAlternativ = allQuestions.get(questionCounter).getAnswers();
        Collections.shuffle(allaAlternativ);

        // Loopar igenom alla alternativ och målar upp knapparna
        for (int i = 0; i < allaAlternativ.size(); i++) {
            JButton alternativBtn = new JButton();
            alternativBtn.setText(allaAlternativ.get(i));
            alternativBtn.setBackground(CustomCollors.Beigh);
            alternativBtn.setFont(CustomFonts.current_Font_Button);
            alternativBtn.setFocusable(false);
            alternativBtn.addActionListener(this);
            gamePanel.add(alternativBtn);
            gamePanel.add(Box.createHorizontalStrut(15));
            gamePanel.add(Box.createVerticalStrut(300));
            System.out.println("Lägger till knapp: " + allaAlternativ.get(i));
            repaint();
            revalidate();
        }
    }

    public void loadDisconnectMsg() {
        textLabelTwo.setText("The other player disconnected during game start, please restart");
        textLabelTwo.setForeground(CustomCollors.Brown);
        startGameBtn.setVisible(false);
    }

    public void checkIfMoreQuestions() {
        if (questionCounter < totalQuestions) {
            questionCounter++;
            loadGamePanel();
        } else {
            // skicka starta player 2 ? sätt player 1 i waiting room.
            loadWaitingRoomPanel();
            outputStream.println("switch");
            System.out.println("Next player turn");
        }
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public void setWaitingRoomMsg(String waitingRoomMsg) {
        this.textLabelOne.setText(waitingRoomMsg);
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

    public void setPlayerScore(Map<String, List<Integer>> playerScore) {
        this.playerScore = playerScore;

    }

    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }

    public void resetScoreArea() {
        currentScoreArea.setText("");
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

        if (currentQuestions != null) {
            System.out.println(questionCounter + " questionCounter");
            System.out.println(currentQuestions.getCurrentQuestions() + " Listan");
            String currentRightAnswer = currentQuestions.getCurrentQuestions().get(questionCounter).getRightAnswer();

            if (selectedBtn.getText().equalsIgnoreCase(currentRightAnswer)) {
                System.out.println("Du svara rätt!");
                selectedBtn.setBackground(Color.GREEN);

                outputStream.println("correct");

                checkIfMoreQuestions();
            } else {
                System.out.println("wrong");
                outputStream.println("wrong");
                selectedBtn.setBackground(Color.RED);
                checkIfMoreQuestions();
            }

        }
    }

}

