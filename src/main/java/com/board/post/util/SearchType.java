package com.board.post.util;

public enum SearchType {

    EVERY("every contents response option : ignoring any condition"),
    SALE("only ON-SALE contents response option : filtered goods only");

    final String description;

    SearchType(String description) {
        this.description = description;
    }
}
