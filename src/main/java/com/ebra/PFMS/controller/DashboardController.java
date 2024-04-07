package com.ebra.PFMS.controller;

import com.ebra.PFMS.service.BudgetService;
import com.ebra.PFMS.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class DashboardController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private ExpenseService expenseService;

    // Show the dashboard page
    @GetMapping("/dashboard")
    public String showDashboard(HttpServletRequest request, Model model) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        BigDecimal maxValue = expenseService.getMaxCategorySpending(userId);

        // Add attributes to the model to be used in the dashboard page
        model.addAttribute("currentBudget", budgetService.getCurrentBudget(userId));
        model.addAttribute("recentExpenses", expenseService.findRecentExpensesByUserId(userId, 5));
        model.addAttribute("totalExpensesThisMonth", expenseService.getTotalExpensesThisMonth(userId));
        model.addAttribute("spendingByCategory", expenseService.getSpendingByCategory(userId));
        model.addAttribute("maxValue", maxValue);

        // Get the current month and add it to the model
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        String currentMonth = LocalDate.now().format(monthFormatter);
        model.addAttribute("currentMonth", currentMonth);

        return "dashboard";
    }
}
