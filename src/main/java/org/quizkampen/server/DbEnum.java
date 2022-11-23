package org.quizkampen.server;

public enum DbEnum {

    PROGRAMMERING("Programmering"),
    LITTERATURKONST("Litteraturkonst"),
    GEOGRAFI("Geografi"),
    HISTORIA("Historia");

    private final String category;

    DbEnum(String typ) {

        this.category = typ;
    }

    public String getCategory() {
        return category;
    }
}


