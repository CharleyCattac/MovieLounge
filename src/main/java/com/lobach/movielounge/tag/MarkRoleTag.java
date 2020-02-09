package com.lobach.movielounge.tag;


import com.lobach.movielounge.model.UserRole;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class MarkRoleTag extends TagSupport {
    private static final String COLOR_RED = "ff0000";
    private static final String COLOR_GREEN = "83be54";
    private static final String COLOR_BLUE = "00a7e3";
    private static final String COLOR_BLACK = "000000";
    private UserRole role;

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        String color;
        try {
            if (role == null) {
                color = COLOR_BLACK;
            } else {
                switch (role) {
                    case GUEST:
                        color = COLOR_RED;
                        break;
                    case USER:
                        color = COLOR_GREEN;
                        break;
                    case ADMIN:
                        color = COLOR_BLUE;
                        break;
                    default:
                        color = COLOR_BLACK;
                        break;
                }
            }
            pageContext.getOut().write(" <div class=\"hr\" style=\"background-color: " + color
                    + "\"; color: " + color + " ></div>");
            } catch (IOException e) {
                throw new JspException(e);
        }

        return SKIP_BODY;
    }
}