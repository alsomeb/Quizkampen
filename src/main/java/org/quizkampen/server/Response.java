package org.quizkampen.server;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> categories;

    private Question questions;

    public Response(List<String> categories) {
        this.categories = categories;
    }

    public Response(Question question) {
        this.questions = question;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Question getQuestions() {
        return questions;
    }
}
