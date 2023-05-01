package com.example.top.controller;

import com.example.top.dto.order.DimensionsDto;
import com.example.top.entity.order.Dimensions;
import com.example.top.service.DimensionsService;
import com.example.top.util.mapper.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DimensionsController extends AController {

    @Autowired
    private DimensionsService service;

    @GetMapping({"/add-dimensions", "/update-dimensions"})
    public ModelAndView renderDimensions(Long id) {
        return getRenderView((id == null) ? new Dimensions() : service.getDimensions(id));
    }

    @PostMapping("/save-dimensions")
    public ModelAndView saveDimensions(@Valid @ModelAttribute("dimensions") DimensionsDto dimensions, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return getRenderView(dimensions);

        service.saveDimensions(Mapper.map(dimensions, new Dimensions()));

        return getAlertView("Dimensions saved", "/dimensions");
    }

    private ModelAndView getRenderView(Object dimensions) {
        var mv = new ModelAndView();
        mv.addObject("dimensions", dimensions);
        mv.setViewName("order/dimensions/save-dimensions");

        return mv;
    }

    @RequestMapping("/dimensions")
    public ModelAndView getDimensions() {
        var mv = new ModelAndView();
        mv.addObject("dimensions", service.findAllDimensions());
        mv.setViewName("order/dimensions/dimensions");

        return mv;
    }

    @GetMapping("/delete-dimensions")
    public ModelAndView deleteDimensions(Long id) {
        service.deleteDimensions(id);

        return new ModelAndView("redirect:/dimensions");
    }
}
