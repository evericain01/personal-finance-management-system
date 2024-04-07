package com.ebra.PFMS.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// For Polymorphism. Will be used for error messages.
public class ErrorMessage implements Message {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void addMessage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", getMessage());
    }
}
