package com.lobach.movielounge.command;

import com.lobach.movielounge.servlet.RequestContent;

public interface ActionCommand {

    String execute(RequestContent contentManager);
}
