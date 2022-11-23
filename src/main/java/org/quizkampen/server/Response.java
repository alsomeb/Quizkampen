package org.quizkampen.server;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> categories;
    private Questions questions;

    private List<Integer> playerScores;

    private boolean gameIsOver;

    public Response(List<String> categories) {
        this.categories = categories;
    }

    public Response(Questions questions) {
        this.questions = questions;
    }

    public Response(boolean gameIsOver, List<Integer> playerScores) {
        this.gameIsOver = gameIsOver;
        this.playerScores = playerScores;
    }

    public Questions getQuestions() {
        return questions;
    }

    public List<String> getCategories() {
        return categories;
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }

    public List<Integer> getPlayerScores() {
        return playerScores;
    }
}
