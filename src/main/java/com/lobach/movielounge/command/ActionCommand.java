package com.lobach.movielounge.command;

import com.lobach.movielounge.servlet.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ActionCommand {

    Logger logger = LogManager.getLogger();

    String ATTRIBUTE_LOCALE = "locale";

    String execute(RequestContent contentManager);

    default LocaleType defineLocale(RequestContent content) {
        String locale = (String) content.getSessionAttribute(ATTRIBUTE_LOCALE);
        return LocaleType.valueOf(locale.toUpperCase());
    }
}
