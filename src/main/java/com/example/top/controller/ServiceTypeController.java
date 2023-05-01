package com.example.top.controller;

import com.example.top.dto.order.ServiceTypeDto;
import com.example.top.entity.order.ServiceType;
import com.example.top.service.ServiceTypeService;
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
public class ServiceTypeController extends AController {

    @Autowired
    private ServiceTypeService service;

    @GetMapping({"/add-service-type", "/edit-service-type"})
    public ModelAndView renderServiceType(Long id) {
        return getRenderView((id == null) ? new ServiceType() : service.getServiceType(id));
    }

    @PostMapping("/save-service-type")
    public ModelAndView saveServiceType(@Valid @ModelAttribute("serviceType") ServiceTypeDto type, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return getRenderView(type);

        service.saveServiceType(Mapper.map(type, new ServiceType()));

        return getAlertView("Service type saved", "/service-types");
    }

    private ModelAndView getRenderView(Object type) {
        var mv = new ModelAndView();
        mv.addObject("serviceType", type);
        mv.setViewName("order/service-type/save-service-type");

        return mv;
    }

    @RequestMapping("/service-types")
    public ModelAndView getServiceTypes() {
        var mv = new ModelAndView();
        mv.addObject("serviceTypes", service.findAllServiceTypes());
        mv.setViewName("order/service-type/service-type");

        return mv;
    }

    @GetMapping("/delete-service-type")
    public ModelAndView deleteServiceType(Long id) {
        service.deleteServiceType(id);

        return new ModelAndView("redirect:/service-types");
    }
}
