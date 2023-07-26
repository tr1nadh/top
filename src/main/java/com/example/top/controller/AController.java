package com.example.top.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public abstract class AController {

    @Autowired
    private HttpServletRequest request;

    private String getCurrentMapping() {
        return request.getRequestURI();
    }

    public ModelAndView getAlertView(String message, String toMapping) {
        var mv = new ModelAndView();
        mv.addObject("message", message);
        mv.addObject("toMapping", toMapping);
        mv.setViewName("alert-n-continue");

        return mv;
    }
}
