package org.quizkampen.server;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private final String questionText;
    private final String rightAnswer;
    private final ArrayList<String> answers = new ArrayList<>();

    public Question (String question, String rightAnswer, String wrongAnswerOne,
                    String wrongAnswerTwo, String wrongAnswerThree) {
        this.questionText = question;
        this.rightAnswer = rightAnswer;
        answers.add(rightAnswer);
        answers.add(wrongAnswerOne);
        answers.add(wrongAnswerTwo);
        answers.add(wrongAnswerThree);
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}
