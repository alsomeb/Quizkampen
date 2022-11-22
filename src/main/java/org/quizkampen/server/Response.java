package org.quizkampen.server;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> categories;

    private Questions questions;

    public Response(List<String> categories) {
        this.categories = categories;
    }

    public Response(Questions questions) {
        this.questions = questions;
    }

    public Questions getQuestions() {
        return questions;
    }

    public List<String> getCategories() {
        return categories;
    }


}
