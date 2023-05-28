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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/orders/service-types")
public class ServiceTypeController extends AController {

    @Autowired
    private ServiceTypeService service;

    @GetMapping({"/add-service-type", "/update-service-type"})
    public ModelAndView renderServiceType(Long id) {
        return getRenderView((id == null) ? new ServiceType() : service.getServiceType(id));
    }

    @PostMapping("/save-service-type")
    public RedirectView saveServiceType(@Valid @ModelAttribute("serviceType") ServiceTypeDto type, BindingResult bindingResult,
                                        RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError("name").getDefaultMessage());
            return new RedirectView("view");
        }


        service.saveServiceType(Mapper.map(type, new ServiceType()));

        var end = (type.getId() == null) ? "saved" : "updated";
        var message = "Service type '" + type.getName() + "' has been " + end;
        attributes.addFlashAttribute("alertMessage", message);
        return new RedirectView("view");
    }

    private ModelAndView getRenderView(Object type) {
        var mv = new ModelAndView();
        mv.addObject("serviceType", type);
        mv.setViewName("order/service-type/save-service-type");

        return mv;
    }

    @RequestMapping("/view")
    public ModelAndView getServiceTypes() {
        var mv = new ModelAndView();
        mv.addObject("serviceTypes", service.findAllServiceTypes());
        mv.addObject("serviceType", new ServiceType());
        mv.setViewName("order/service-type/service-type");

        return mv;
    }

    @GetMapping("/delete-service-type")
    public RedirectView deleteServiceType(Long id, RedirectAttributes attributes) {
        var type = service.deleteServiceType(id);

        var message = "Service type '" + type.getName() + "' has been deleted";
        attributes.addFlashAttribute("alertMessage", message);
        return new RedirectView("view");
    }

    @RequestMapping("/error")
    public ModelAndView error(String alertMessage, RedirectAttributes attributes) {
        attributes.addFlashAttribute("alertMessage", alertMessage);
        return new ModelAndView("forward:view");
    }
}
