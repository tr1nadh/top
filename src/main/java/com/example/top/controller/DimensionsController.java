package com.example.top.controller;

import com.example.top.dto.order.DimensionsDto;
import com.example.top.entity.order.Dimensions;
import com.example.top.service.DimensionsService;
import com.example.top.util.mapper.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/orders/dimensions")
public class DimensionsController extends AController {

    @Autowired
    private DimensionsService service;

    @GetMapping("/add-dimensions")
    public String renderDimensions() {
        return "order/dimensions/save-dimensions";
    }

    @PostMapping("/save-dimensions")
    public RedirectView saveDimensions(@Valid @ModelAttribute("dimensions") DimensionsDto dimensions, BindingResult bindingResult,
                                       RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError("name").getDefaultMessage());
            return new RedirectView("view");
        }

        var response = service.saveDimensions(Mapper.map(dimensions, new Dimensions()));

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new RedirectView("view");
    }

    @RequestMapping("/view")
    public ModelAndView getDimensions(@RequestParam(defaultValue = "0") int page) {
        var mv = new ModelAndView();
        mv.addObject("dimensions", service.findAllDimensions(page));
        mv.addObject("currentPage", page);
        mv.setViewName("order/dimensions/dimensions");

        return mv;
    }

    @GetMapping("/delete-dimensions")
    public RedirectView deleteDimensions(Long id, RedirectAttributes attributes) {
        var response = service.deleteDimensions(id);

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new RedirectView("view");
    }
}
