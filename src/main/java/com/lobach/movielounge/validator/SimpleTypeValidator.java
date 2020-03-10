package com.lobach.movielounge.validator;

public class SimpleTypeValidator {
    public enum SimpleType {
        BOOLEAN, INTEGER, FLOAT;
    }

    public static boolean stringHasValidValue(SimpleType type, String stringValue) {
        try {
            switch (type) {
                case BOOLEAN: {
                    Boolean.parseBoolean(stringValue);
                    break;
                }
                case INTEGER: {
                    Integer.parseInt(stringValue);
                    break;
                }
                case FLOAT: {
                    Float.parseFloat(stringValue);
                    break;
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
