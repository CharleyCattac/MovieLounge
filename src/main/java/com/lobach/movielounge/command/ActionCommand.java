package com.lobach.movielounge.command;

import com.lobach.movielounge.servlet.RequestContent;

public interface ActionCommand {

    String ATTRIBUTE_LOCALE = "locale";

    String execute(RequestContent contentManager);

    default LocaleType defineLocale(RequestContent content) {
        String locale = (String) content.getSessionAttribute(ATTRIBUTE_LOCALE);
        return LocaleType.valueOf(locale.toUpperCase());
    }
}
