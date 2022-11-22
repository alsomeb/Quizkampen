package org.quizkampen.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Questions implements Serializable {
    private List<Question> currentQuestions;

    public Questions(DbEnum category) {
        currentQuestions = new ArrayList<>();
        switch (category) {
            case PROGRAMMERING -> setProgrammingQuestions();
            case LITTERATURKONST -> setLitteraturKonstQuestions();
            case GEOGRAFI -> setGeografiQuestions();
            case HISTORIA -> setHistoriaQuestions();
        }
    }

    private void setProgrammingQuestions() {
        currentQuestions.add(new Question("Vad är Java?", "Programmerings språk", "Kaffe",
                "Te", "Apa"));
        currentQuestions.add(new Question("Vad är en algoritm?", "En serie av instruktion",
                "Ett språk", "Ett mattetal", "En robot"));
        currentQuestions.add(new Question("Vem anses som världens första programmerare?",
                "Ada Lovelace", "Ellen Bark", "Bill Gates", "Grace Hopper"));
    }

    private void setLitteraturKonstQuestions() {
        currentQuestions.add(new Question("Vem målade Mona Lisa", "Leonardo Da Vinci",
                "Michelangelo", "Picasso", "Edvard Munch"));
        currentQuestions.add(new Question("Vilken bokserie var den mest sålda under 2000-talet?",
                "Harry Potter, J.K Rowling", "Sagan om ringen", "Twilight",
                "Fifty shades"));
        currentQuestions.add(new Question("Från vilket land kommer graffitikonstnären Banksy?",
                "England", "Frankrike", "USA", "Norge"));
    }

    private void setGeografiQuestions() {
        currentQuestions.add(new Question("Vart ligger Sverige?", "Här", "Mars", "Däråt", "Ute?"));
        currentQuestions.add(new Question("Vilket är världens minsta land?", "Vatikanstaten", "San Marino", "Monaco", "Lichtenstein"));
        currentQuestions.add(new Question("Vad heter Kanadas huvudstad", "Ottawa", "Vancouver", "Detroit", "Washington"));
    }

    private void setHistoriaQuestions() {
        currentQuestions.add(new Question("När öppnade London sin tunnelbana?", "1863", "1898", "1793", "1912"));
        currentQuestions.add(new Question("Vem uppfann World Wide Web, och när?", "Tim Berners-Lee, 1990", "Bill Gates", "Elon Musk", "Donald Trump"));
        currentQuestions.add(new Question("Vilket år sjönk Titanic?", "1912", "1914", "1924", "1899"));
    }

    public List<Question> getCurrentQuestions() {
        return currentQuestions;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "currentQuestions=" + currentQuestions +
                '}';
    }
}
