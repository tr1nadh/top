package com.example.top.controller;

import com.example.top.entity.order.ServiceType;
import com.example.top.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService service;

    @GetMapping("/add-service-type")
    public ModelAndView addServiceType() {
        var mv = new ModelAndView();
        mv.addObject("serviceType", new ServiceType());
        mv.setViewName("");

        return mv;
    }

    @PostMapping("/save-service-type")
    public RedirectView saveServiceType(ServiceType type) {
        service.saveServiceType(type);

        return new RedirectView("add-service-type");
    }

    @RequestMapping("/service-types")
    public ModelAndView getServiceTypes() {
        var mv = new ModelAndView();
        mv.addObject("serviceTypes", service.findAllServiceTypes());
        mv.setViewName("");

        return mv;
    }

    @GetMapping("/edit-service-type")
    public ModelAndView editServiceType(Long id) {
        var serviceType = service.getServiceType(id);

        var mv = new ModelAndView();
        mv.addObject("serviceType", serviceType);
        mv.setViewName("");

        return mv;
    }

    @PostMapping("/update-service-type")
    public RedirectView updateServiceType(ServiceType type) {
        service.updateServiceType(type);

        return new RedirectView("service-types");
    }

    @GetMapping("/delete-service-type")
    public RedirectView deleteServiceType(Long id) {
        service.deleteServiceType(id);

        return new RedirectView("service-types");
    }
}
