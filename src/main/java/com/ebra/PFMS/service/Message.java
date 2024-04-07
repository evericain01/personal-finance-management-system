package com.ebra.PFMS.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// For Polymorphism. Check SuccessMessage.java and ErrorMessage.java.
public interface Message {
    String getMessage();
    void addMessage(RedirectAttributes redirectAttributes);
}
