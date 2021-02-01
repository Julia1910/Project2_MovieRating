package com.cursor.model.enums;

public enum Category {
    DRAM("Drama"),
    COM("Comedy"),
    MUS("Musical"),
    ROM("Romance"),
    ROM_COM("Romantic comedy"),
    DET("Detective"),
    ACT("Action"),
    THR("Thriller"),
    HOR("Horror"),
    SCI_FI("Science fiction");

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    Category(String fullName) {
        this.fullName = fullName;
    }
}
