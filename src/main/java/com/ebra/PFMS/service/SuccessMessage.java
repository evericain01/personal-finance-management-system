package com.ebra.PFMS.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// For Polymorphism. Will be used for success messages.
public class SuccessMessage implements Message {
    private String message;

    public SuccessMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void addMessage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", getMessage());
    }
}