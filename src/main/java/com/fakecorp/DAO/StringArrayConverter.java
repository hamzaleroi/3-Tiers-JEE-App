package com.fakecorp.DAO;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<String[], String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(String[] stringList) {
        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public String[] convertToEntityAttribute(String string) {
        return string.split(SPLIT_CHAR);
    }
}