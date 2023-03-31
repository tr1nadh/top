package com.example.top.controller;

import com.example.top.dto.ServiceTypeDto;
import com.example.top.entity.order.ServiceType;
import com.example.top.service.ServiceTypeService;
import com.example.top.util.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService service;

    @GetMapping({"/add-service-type", "/edit-service-type"})
    public ModelAndView renderServiceType(Long id) {
        var serviceType = (id == null) ? new ServiceType() : service.getServiceType(id);

        var mv = new ModelAndView();
        mv.addObject("serviceType", serviceType);
        mv.setViewName("order/service-type/save-service-type");

        return mv;
    }

    @PostMapping("/save-service-type")
    public ModelAndView saveServiceType(@Valid @ModelAttribute("serviceType") ServiceTypeDto type, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var mv = new ModelAndView("order/service-type/save-service-type");
            mv.addObject("serviceType", type);
            return mv;
        }

        service.saveServiceType(Mapper.map(type, new ServiceType()));

        return new ModelAndView("redirect:/service-types");
    }

    @RequestMapping("/service-types")
    public ModelAndView getServiceTypes() {
        var mv = new ModelAndView();
        mv.addObject("serviceTypes", service.findAllServiceTypes());
        mv.setViewName("order/service-type/service-type");

        return mv;
    }

    @GetMapping("/delete-service-type")
    public RedirectView deleteServiceType(Long id) {
        service.deleteServiceType(id);

        return new RedirectView("service-types");
    }
}
