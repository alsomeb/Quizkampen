package org.quizkampen.server;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Response implements Serializable {
    private List<String> categories;
    private Questions questions;

    private Map<String, List<Integer>> playerScores;
    private boolean roundIsOver;

    private boolean gameIsOver;

    private List<Integer> totalScore;

    public Response(List<String> categories) {
        this.categories = categories;
    }

    public Response(Questions questions) {
        this.questions = questions;
    }

    public Response(boolean roundIsOver, Map<String, List<Integer>> playerScores) {
        this.roundIsOver = roundIsOver;
        this.playerScores = playerScores;
    }

    public Response(boolean gameIsOver, List<Integer> totalScore) {
        this.gameIsOver = gameIsOver;
        this.totalScore = totalScore;
    }

    public Questions getQuestions() {
        return questions;
    }

    public List<String> getCategories() {
        return categories;
    }

    public boolean roundIsOver() {
        return roundIsOver;
    }

    public boolean isGameIsOver() {
        return gameIsOver;
    }

    public List<Integer> getTotalScore() {
        return totalScore;
    }

    public Map<String, List<Integer>> getPlayerScores() {
        return playerScores;
    }
}
