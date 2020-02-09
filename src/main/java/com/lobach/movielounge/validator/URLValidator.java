package com.lobach.movielounge.validator;

public class URLValidator {
    private static final String IMAGE_URL_REGEX = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)";

    public static boolean validateUrl(String url) {
        return url.matches(IMAGE_URL_REGEX);
    }
}
