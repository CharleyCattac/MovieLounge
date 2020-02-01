package com.lobach.movielounge.tag;


import com.lobach.movielounge.model.UserRole;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class MarkRoleTag extends TagSupport {
    private UserRole role;
    public int doStartTag() throws JspException {
        String roleString = (String) pageContext.getSession().getAttribute("userRole");
        role = UserRole.valueOf(roleString.toLowerCase());

        String str = "Size =<B>( " + role + " )</B>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(str);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}