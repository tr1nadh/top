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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/orders/dimensions")
public class DimensionsController extends AController {

    @Autowired
    private DimensionsService service;

    @GetMapping({"/add-dimensions", "/update-dimensions"})
    public ModelAndView renderDimensions(Long id) {
        return getRenderView((id == null) ? new Dimensions() : service.getDimensions(id));
    }

    @PostMapping("/save-dimensions")
    public RedirectView saveDimensions(@Valid @ModelAttribute("dimensions") DimensionsDto dimensions, BindingResult bindingResult,
                                       RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError("name").getDefaultMessage());
            return new RedirectView("view");
        }

        service.saveDimensions(Mapper.map(dimensions, new Dimensions()));

        var end = (dimensions.getId() == null) ? "saved" : "updated";
        var message = "Dimensions '" + dimensions.getName() + "' has been " + end;
        attributes.addFlashAttribute("alertMessage", message);
        return new RedirectView("view");
    }

    private ModelAndView getRenderView(Object dimensions) {
        var mv = new ModelAndView();
        mv.addObject("dimensions", dimensions);
        mv.setViewName("order/dimensions/save-dimensions");

        return mv;
    }

    @RequestMapping("/view")
    public ModelAndView getDimensions() {
        var mv = new ModelAndView();
        mv.addObject("dimensions", service.findAllDimensions());
        mv.addObject("dimensionsObj", new Dimensions());
        mv.setViewName("order/dimensions/dimensions");

        return mv;
    }

    @GetMapping("/delete-dimensions")
    public RedirectView deleteDimensions(Long id, RedirectAttributes attributes) {
        var dimensions = service.deleteDimensions(id);

        var message = "Dimensions '" + dimensions.getName() + "' has been deleted";
        attributes.addFlashAttribute("alertMessage", message);
        return new RedirectView("view");
    }

    @RequestMapping("/error")
    public ModelAndView error(String alertMessage, RedirectAttributes attributes) {
        attributes.addFlashAttribute("alertMessage", alertMessage);
        return new ModelAndView("forward:view");
    }
}
