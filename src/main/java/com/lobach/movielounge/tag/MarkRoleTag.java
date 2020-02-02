package com.lobach.movielounge.tag;


import com.lobach.movielounge.model.UserRole;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class MarkRoleTag extends TagSupport {
    private static final String COLOR_RED = Color.RED.toString();
    private static final String COLOR_GREEN = Color.GREEN.toString();
    private static final String COLOR_BLUE = Color.BLUE.toString();
    private static final String COLOR_BLACK = Color.BLACK.toString();
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
                pageContext.getOut().write(" <i class=\"fas fa-user-tag\"></i><b style=\"color: " + color + "\">" + role.value + "</b>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}