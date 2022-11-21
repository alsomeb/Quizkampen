package org.quizkampen.server;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<String> categories = new ArrayList<>(List.of("Programmering", "Geografi", "Dans","Hundar"));

    private List<Question> programmering = new ArrayList<>(
            List.of(
                    new Question("Vad är Java?", "programmering språk", "kaffe", "te", "apa")
            )
    );

    private List<Question> geografi = new ArrayList<>(
            List.of(
                    new Question("Vart ligger Sverige?", "här", "mars", "däråt", "ute?")
            )
    );

    private List<Question> dans = new ArrayList<>(
            List.of(
                    new Question("Vad är en karuchi?", "springa in i väggen", "folkdans", "te", "apa")
            )
    );

    private List<Question> hundar = new ArrayList<>(
            List.of(
                    new Question("vilken hund ser ut som en korv", "tax", "labrador", "bulldog", "rottweiler")
            )
    );

    public List<String> getCategories() {
        return categories;
    }

    public List<Question> getProgrammering() {
        return programmering;
    }

    public List<Question> getGeografi() {
        return geografi;
    }

    public List<Question> getDans() {
        return dans;
    }

    public List<Question> getHundar() {
        return hundar;
    }

    /*   private final List<String> fåglarFrågor = new ArrayList<>(List.of("Vilken fråga är rätt?", "svarar du rätt?"));
    private final List<String> programmeringFrågor = new ArrayList<>(List.of("Vilken fråga är rätt?", "svarar du rätt?"));
    private final List<String> cycklarFrågor = new ArrayList<>(List.of("Vilken fråga är rätt?", "svarar du rätt?"));
    private final List<String> landFrågor = new ArrayList<>(List.of("Vilken fråga är rätt?", "svarar du rätt?"));
    private final List<String> hästarFrågor = new ArrayList<>(List.of("Vilken fråga är rätt?", "svarar du rätt?"));


    private final List<String> fåglarSvar = new ArrayList<>(List.of("rätt" , "fel", "fel", "fel"));
    private final List<String> programmeringSvar = new ArrayList<>(List.of("rätt" , "fel", "fel", "fel"));
    private final List<String> cycklarSvar = new ArrayList<>(List.of("rätt" , "fel", "fel", "fel"));
    private final List<String> landSvar = new ArrayList<>(List.of("rätt" , "fel", "fel", "fel"));
    private final List<String> hästarSvar = new ArrayList<>(List.of("rätt" , "fel", "fel", "fel"));

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getFåglarFrågor() {
        Collections.shuffle(fåglarFrågor);
        return fåglarFrågor;
    }

    public List<String> getProgrammeringFrågor() {
        return programmeringFrågor;
    }

    public List<String> getCycklarFrågor() {
        return cycklarFrågor;
    }

    public List<String> getLandFrågor() {
        return landFrågor;
    }

    public List<String> getHästarFrågor() {
        return hästarFrågor;
    }

    public List<String> getFåglarSvar() {
        return fåglarSvar;
    }

    public List<String> getProgrammeringSvar() {
        return programmeringSvar;
    }

    public List<String> getCycklarSvar() {
        return cycklarSvar;
    }

    public List<String> getLandSvar() {
        return landSvar;
    }

    public List<String> getHästarSvar() {
        return hästarSvar;
    }*/
}
