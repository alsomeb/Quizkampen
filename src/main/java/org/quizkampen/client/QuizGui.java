package org.quizkampen.client;

import org.quizkampen.server.Question;
import org.quizkampen.server.Questions;
import org.quizkampen.static_variable.CustomColors;
import org.quizkampen.static_variable.CustomFonts;
import org.quizkampen.static_variable.CustomSizes;
import org.quizkampen.static_variable.Property_Loader;
import org.quizkampen.timerpanel.TimerPanel;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class QuizGui extends JFrame implements ActionListener {

    private List<JButton> answerBtnList = new ArrayList<>();
    //private final javax.swing.Timer timer20=new javax.swing.Timer(1500, e -> {checkIfMoreQuestions(); });
    private final Timer timer = new Timer(350, e -> {checkIfMoreQuestions(); });

    private final TimerPanel stopwatch = new TimerPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel welcomePanel = new JPanel();
    private final JPanel waitingRoomPanel = new JPanel();
    private final JPanel categoryPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();
    private final JPanel resultPanel = new JPanel();

    private final JLabel textLabelOne = new JLabel("Waiting for 1 more player");
    private final JLabel textLabelTwo = new JLabel("Welcome to Quizkampen!");
    private final JLabel questionLabel = new JLabel();


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
    private final Properties p = new Properties();

    private JLabel currentScoreLabel = new JLabel();
    private JTextArea currentScoreArea = new JTextArea(40, 40);
    private JScrollPane scrollPain = new JScrollPane(currentScoreArea);

    private List<Integer> player1Scores;
    private List<Integer> player2Scores;

    private List<Integer> totalEndScore;

    public QuizGui() {
        loadProps();
        totalEndScore = new ArrayList<>();
        player1Scores = new ArrayList<>();
        player2Scores = new ArrayList<>();
        playerScore = new HashMap<>();
        categories = new ArrayList<>();
        amountOfQuestions = Integer.parseInt(p.getProperty("amountOfQuestions", "3"));
        totalQuestions = amountOfQuestions - 1; // PROPS SETTINGS
        // Welcome Panel
        loadWelcomePanel();

        // Frame
        this.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(welcomePanel, BorderLayout.CENTER);
        this.setSize(CustomSizes.width, CustomSizes.height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Quizkampen");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Property_Loader.loadproperty();
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
        welcomePanel.setBackground(CustomColors.background_Clr);
        welcomePanel.setLayout(new GridBagLayout());
        // Label
        textLabelTwo.setFont(CustomFonts.current_Font_Label);


        welcomePanel.add(textLabelTwo);
        repaint();
        revalidate();
    }

    public void loadWaitingRoomPanel() {
        mainPanel.removeAll();
        mainPanel.add(waitingRoomPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
        waitingRoomPanel.setBackground(CustomColors.background_Clr);
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
        resultPanel.setBackground(CustomColors.background_Clr);

        System.out.println(playerScore.get("Player 1"));
        System.out.println(playerScore.get("Player 2"));

        player1Scores = playerScore.get("Player 1");
        player2Scores = playerScore.get("Player 2");

        for (int i = 0; i < player1Scores.size(); i++) {
            currentScoreArea.setBackground((CustomColors.background_Clr));
            currentScoreArea.setFont(CustomFonts.current_Font_Label);
            currentScoreArea.setLineWrap(true);
            currentScoreArea.setEditable(false);
            currentScoreArea.append("\t"+"\t"+"Round "+ (i +1 ) + ": " + player1Scores.get(i) + " - " + player2Scores.get(i) + "\n"+"\n"+"\n"+"\n");
            resultPanel.add(scrollPain);
            revalidate();
            repaint();
        }

    }
    public void loadGameOverPanel(){
        mainPanel.removeAll();
        resultPanel.removeAll();
        resultPanel.setLayout(new GridBagLayout());
        mainPanel.add(resultPanel);

        JLabel player1 = new JLabel("Player 1 total: " + totalEndScore.get(0));
        player1.setFont(CustomFonts.standard_Sansserif_Label);
        JLabel player2 = new JLabel("Player 2 total: " + totalEndScore.get(1));
        player2.setFont(CustomFonts.standard_Sansserif_Label);

        resultPanel.add(player1);
        resultPanel.add(Box.createHorizontalStrut(40));
        resultPanel.add(player2);
        revalidate();
        repaint();
    }

    public void loadCategoryPanel() {

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(categoryPanel, BorderLayout.CENTER);
        mainPanel.add(textLabelTwo, BorderLayout.NORTH);
        mainPanel.setBackground(CustomColors.background_Clr);
        textLabelTwo.setText("Välj en kategori");
        textLabelTwo.setHorizontalAlignment(JLabel.CENTER);

        categoryPanel.setBackground(CustomColors.background_Clr);
        categoryPanel.setLayout(new GridBagLayout());
        categoryPanel.add(categoryOneButton);
        categoryPanel.add(Box.createHorizontalStrut(15));
        categoryPanel.add(categoryTwoButton);
        categoryPanel.add(Box.createHorizontalStrut(15));
        categoryPanel.add(categoryThreeButton);
        categoryPanel.add(Box.createHorizontalStrut(15));
        categoryPanel.add(categoryFourButton);

        categoryOneButton.setFont(CustomFonts.current_Font_Button);
        categoryOneButton.setBackground(CustomColors.btn_Clr);
        categoryTwoButton.setFont(CustomFonts.current_Font_Button);
        categoryTwoButton.setBackground(CustomColors.btn_Clr);
        categoryThreeButton.setFont(CustomFonts.current_Font_Button);
        categoryThreeButton.setBackground(CustomColors.btn_Clr);
        categoryFourButton.setFont(CustomFonts.current_Font_Button);
        categoryFourButton.setBackground(CustomColors.btn_Clr);
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
        mainPanel.add(gamePanel, BorderLayout.SOUTH);
        mainPanel.setBackground(CustomColors.background_Clr);
        mainPanel.add(questionLabel, BorderLayout.CENTER);
        gamePanel.setBackground(CustomColors.background_Clr);
        //mainPanel.add(stopwatch,BorderLayout.SOUTH);

        textLabelTwo.setText("Game starts now!");
        gamePanel.setLayout(new GridBagLayout());

        // Fråga Text
        List<Question> allQuestions = currentQuestions.getCurrentQuestions();
        String questionText = allQuestions.get(questionCounter).getQuestionText();
        questionLabel.setText(questionText);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setFont(CustomFonts.standard_Sansserif_Label);

        // Alternativ Listan
        List <String> allaAlternativ = allQuestions.get(questionCounter).getAnswers();
        Collections.shuffle(allaAlternativ);

        // Loopar igenom alla alternativ och målar upp knapparna
        for (int i = 0; i < allaAlternativ.size(); i++) {
            JButton alternativBtn = new JButton();
            alternativBtn.setText(allaAlternativ.get(i));
            alternativBtn.setBackground(CustomColors.btn_Clr);
            alternativBtn.setFont(CustomFonts.current_Font_Button);
            alternativBtn.setFocusable(false);
            alternativBtn.addActionListener(this);
            answerBtnList.add(alternativBtn);
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
        textLabelTwo.setForeground(CustomColors.error_Clr);

    }

    public void checkIfMoreQuestions() {
        if (questionCounter < totalQuestions) {
            questionCounter++;
            loadGamePanel();
            timer.stop();
        } else {
            // skicka starta player 2 ? sätt player 1 i waiting room.
            loadWaitingRoomPanel();
            timer.stop();
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

    public void setTotalEndScore(List<Integer> totalEndScore) {
        this.totalEndScore = totalEndScore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedBtn = (JButton) e.getSource();


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
                selectedBtn.setBackground(CustomColors.right);
                outputStream.println("correct");
                timer.start();
            } else {
                System.out.println("wrong");
                outputStream.println("wrong");
                selectedBtn.setBackground(CustomColors.wrong);
                System.out.println(currentRightAnswer);
                for (JButton b : answerBtnList) {
                    if (b.getText().equalsIgnoreCase(currentRightAnswer)){
                        b.setBackground(CustomColors.right);
                    }
                }

                timer.start();
            }

        }
    }

}

