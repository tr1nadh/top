package com.example.top.controller;

import com.example.top.entity.order.Dimensions;
import com.example.top.service.DimensionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DimensionsController {

    @Autowired
    private DimensionsService service;

    @GetMapping({"/add-dimensions", "/edit-dimensions"})
    public ModelAndView renderDimensions(Long id) {
        var dimensions = (id == null) ? new Dimensions() : service.getDimensions(id);

        var mv = new ModelAndView();
        mv.addObject("dimensions", dimensions);
        mv.setViewName("order/dimensions/save-dimensions");

        return mv;
    }

    @PostMapping({"/save-dimensions", "/update-dimensions"})
    public RedirectView saveDimensions(Dimensions dimensions) {
        service.saveDimensions(dimensions);

        if (dimensions.getDimensionsId() != null) return new RedirectView("dimensions");

        return new RedirectView("order/dimensions/save-dimensions");
    }

    @RequestMapping("/dimensions")
    public ModelAndView getDimensions() {
        var mv = new ModelAndView();
        mv.addObject("dimensions", service.findAllDimensions());
        mv.setViewName("order/dimensions/dimensions");

        return mv;
    }

    @GetMapping("/delete-dimensions")
    public RedirectView deleteDimensions(Long id) {
        service.deleteDimensions(id);

        return new RedirectView("dimensions");
    }
}
