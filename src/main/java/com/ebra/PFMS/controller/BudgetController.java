package com.ebra.PFMS.controller;

import com.ebra.PFMS.service.BudgetService;
import com.ebra.PFMS.service.ErrorMessage;
import com.ebra.PFMS.service.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // Method is called when the user navigates to the budget page
    @GetMapping
    public String showBudgetPage(HttpServletRequest request, Model model) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // Redirect to the login page
        }
        model.addAttribute("currentBudget", budgetService.getCurrentBudget(userId));
        return "budget";
    }

    // Method is called when the user submits the budget form
    @PostMapping
    public String setBudget(@RequestParam BigDecimal amount, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Long userId = (Long) request.getSession().getAttribute("userId");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            new ErrorMessage("Budget amount cannot be lower than 0 dollars.").addMessage(redirectAttributes);
            return "redirect:/budget";
        }

        budgetService.setCurrentBudget(userId, amount);
        new SuccessMessage("New budget has been successfully set.").addMessage(redirectAttributes);
        return "redirect:/budget";
    }
}