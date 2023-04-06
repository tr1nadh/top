package com.example.top.controller;

import org.springframework.web.servlet.ModelAndView;

public abstract class ControllerHelper {

    public ModelAndView getAlertView(String message, String toMapping) {
        var mv = new ModelAndView();
        mv.addObject("message", message);
        mv.addObject("toMapping", toMapping);
        mv.setViewName("alert-n-continue");

        return mv;
    }
}
