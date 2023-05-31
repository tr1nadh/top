package com.example.top.exception;

import com.example.top.controller.AController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler extends AController {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(RuntimeException.class)
    public RedirectView handleException(RuntimeException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("alertMessage", ex.getMessage());
        return new RedirectView(request.getHeader("Referer"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public RedirectView handleDataIntegrityViolationException(DataIntegrityViolationException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("alertMessage", getCustomErrorMessage(ex.getMostSpecificCause().getMessage()));
        return new RedirectView(request.getHeader("Referer"));
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
