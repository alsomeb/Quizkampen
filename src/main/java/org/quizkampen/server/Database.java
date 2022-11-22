package org.quizkampen.server;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<String> categories = new ArrayList<>(List.of("Programmering", "Geografi", "Litteraturkonst","Historia"));

    private Questions questions;

    public List<String> getCategories() {
        return categories;
    }

    public Questions getQuestionsListByName(DbEnum category) {
        questions = new Questions(category);
        return questions;
    }


}
