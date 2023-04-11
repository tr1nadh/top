package com.example.top.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public abstract class ControllerHelper {

    @Autowired
    private HttpServletRequest request;

    public ModelAndView getAlertView(String message, String toMapping) {
        var mv = new ModelAndView();
        mv.addObject("message", message);
        mv.addObject("toMapping", toMapping);
        mv.setViewName("alert-n-continue");

        return mv;
    }

    public ModelAndView getAlertView(String message) {
        var mv = new ModelAndView();
        mv.addObject("message", message);
        mv.addObject("toMapping", getCurrentMapping());
        mv.setViewName("alert-n-continue");

        return mv;
    }

    private String getCurrentMapping() {
        return request.getRequestURI();
    }
}
