package com.example.top;

import com.example.top.controller.ControllerHelper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler extends ControllerHelper {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException ex) {
        var mv = new ModelAndView();
        mv.addObject("message", ex.getMessage());
        mv.setViewName("error");

        return mv;
    }
}
