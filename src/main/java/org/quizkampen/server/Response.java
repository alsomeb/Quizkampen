package org.quizkampen.server;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> categories;

    private Question question;

    public Response(List<String> categories) {
        this.categories = categories;
    }

    public Response(Question question) {
        this.question = question;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Question getQuestion() {
        return question;
    }
}
