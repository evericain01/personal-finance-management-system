package com.ebra.PFMS.controller;

import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.service.ErrorMessage;
import com.ebra.PFMS.service.SuccessMessage;
import com.ebra.PFMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    // Getting to the create account page
    @GetMapping("/create-account")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("user", new User());
        return "create-account";
    }

    // Creating a new account
    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute User user, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        // Checking if the password and confirm password match
        if (!user.getPassword().equals(confirmPassword)) {
            new ErrorMessage("Passwords do not match.").addMessage(redirectAttributes);
            return "redirect:/create-account";
        }

        // Check if the username already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            new ErrorMessage("Username already exists.").addMessage(redirectAttributes);
            return "redirect:/create-account";
        }

        try {
            userService.createUser(user);
            new SuccessMessage("Account created successfully. Please log in.").addMessage(redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMessage("There was an error creating the account.").addMessage(redirectAttributes);
            return "redirect:/create-account";
        }

        // Redirecting to the login page
        return "redirect:/";
    }
}

