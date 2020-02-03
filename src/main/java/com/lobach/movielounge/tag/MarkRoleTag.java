package com.lobach.movielounge.tag;


import com.lobach.movielounge.model.UserRole;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class MarkRoleTag extends TagSupport {
    private static final String COLOR_RED = "ff0000";
    private static final String COLOR_GREEN = "008000";
    private static final String COLOR_BLUE = "0000ff";
    private static final String COLOR_BLACK = "000000";
    private UserRole role;

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        if (role != null) {
            try {
                String color;
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
                pageContext.getOut().write(" <div class=\"hr\" style=\"background-color: " + color
                        + "\"; color: " + color + " ></div>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}