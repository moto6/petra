package com.carrot.article.util;

import com.carrot.article.exception.InvalidArticleQueryException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum SearchType {

    EVERY("[EVERY] : every contents will be returned - ignore any condition"),
    SALE("[SALE] : only ON-SALE contents response option - filtered goods only");

    final String description;

    public static Map<String, SearchType> typeMapping = new HashMap<>();

    static {
        typeMapping.put(EVERY.name(), EVERY);
        typeMapping.put(SALE.name(), SALE);
    }


    SearchType(String description) {
        this.description = description;
    }

    public static Optional<SearchType> SearchTypeAdaptor(String query) {

        if(query == null) {
            return Optional.empty();
        }

        String filteredString = query.toUpperCase();

        if(typeMapping.get(filteredString) == null) {
            throw new InvalidArticleQueryException(filteredString);
        }

        return Optional.of(typeMapping.get(filteredString));
    }
}
