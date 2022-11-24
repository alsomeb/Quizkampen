package org.quizkampen.server;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Response implements Serializable {
    private List<String> categories;
    private Questions questions;

    private Map<String, List<Integer>> playerScores;

    private boolean roundIsOver; // TODO RESETTA questionCounter index till 0 igen

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

    public Questions getQuestions() {
        return questions;
    }

    public List<String> getCategories() {
        return categories;
    }

    public boolean roundIsOver() {
        return roundIsOver;
    }

    public Map<String, List<Integer>> getPlayerScores() {
        return playerScores;
    }
}
