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

    @GetMapping({"/add-service-type", "/edit-service-type"})
    public ModelAndView renderServiceType(Long id) {
        var serviceType = (id == null) ? new ServiceType() : service.getServiceType(id);

        var mv = new ModelAndView();
        mv.addObject("serviceType", serviceType);
        mv.setViewName("order/service-type/save-service-type");

        return mv;
    }

    @PostMapping({"/save-service-type", "/update-service-type"})
    public RedirectView saveServiceType(ServiceType type) {
        service.saveServiceType(type);

        if (type.getServiceTypeId() != null) return new RedirectView("service-types");

        return new RedirectView("add-service-type");
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
