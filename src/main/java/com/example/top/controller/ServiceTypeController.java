package com.example.top.controller;

import com.example.top.dto.order.ServiceTypeDto;
import com.example.top.entity.order.ServiceType;
import com.example.top.service.ServiceTypeService;
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
@RequestMapping("/orders/service-types")
public class ServiceTypeController extends AController {

    @Autowired
    private ServiceTypeService service;

    @GetMapping("/add-service-type")
    public String renderServiceType() {
        return "order/service-type/save-service-type";
    }

    @PostMapping("/save-service-type")
    public RedirectView saveServiceType(@Valid @ModelAttribute("serviceType") ServiceTypeDto type, BindingResult bindingResult,
                                        RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError("name").getDefaultMessage());
            return new RedirectView("view");
        }


        var response = service.saveServiceType(Mapper.map(type, new ServiceType()));

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new RedirectView("view");
    }

    @RequestMapping("/view")
    public ModelAndView getServiceTypes(@RequestParam(defaultValue = "0") int page) {
        var mv = new ModelAndView();
        mv.addObject("serviceTypes", service.findAllServiceTypes(page));
        mv.addObject("currentPage", page);
        mv.setViewName("order/service-type/service-type");

        return mv;
    }

    @GetMapping("/delete-service-type")
    public RedirectView deleteServiceType(Long id, RedirectAttributes attributes) {
        var response = service.deleteServiceType(id);

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new RedirectView("view");
    }
}
