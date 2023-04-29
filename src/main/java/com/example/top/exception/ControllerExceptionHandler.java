package com.example.top.exception;

import com.example.top.controller.AController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler extends AController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException ex) {
        var mv = new ModelAndView();
        mv.addObject("message", ex.getMessage());
        mv.setViewName("error");

        return mv;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var mv = new ModelAndView();
        mv.addObject("message", getCustomErrorMessage(ex.getMostSpecificCause().getMessage()));
        mv.setViewName("error");

        return mv;
    }

    private String getCustomErrorMessage(String errorMessage) {
        var duplicateEntryMessage = checkDuplicateEntry(errorMessage);
        if (Objects.nonNull(duplicateEntryMessage)) return duplicateEntryMessage;

        var foreignRelationMessage = checkForeignRelation(errorMessage);
        if (Objects.nonNull(foreignRelationMessage)) return foreignRelationMessage;

        return errorMessage;
    }

    private String checkForeignRelation(String errorMessage) {
        if (!errorMessage.contains("foreign key constraint fails")) return null;

        var listOfChar = errorMessage.split("`");
        var child = listOfChar[9];
        var parent = listOfChar[3];
        return "Cannot delete '" + child + "' because it is referenced by an '" + parent + "' ";
    }

    private String checkDuplicateEntry(String errorMessage) {
        if (!errorMessage.startsWith("Duplicate")) return null;
        var duplicateValue = errorMessage.split(" ")[2];

        return "Cannot insert or update: " + duplicateValue + " already exists";
    }
}
