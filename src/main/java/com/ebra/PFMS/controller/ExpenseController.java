package com.ebra.PFMS.controller;

import com.ebra.PFMS.entity.Category;
import com.ebra.PFMS.entity.Expense;
import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.repository.CategoryRepository;
import com.ebra.PFMS.repository.ExpenseRepository;
import com.ebra.PFMS.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    // Showing the expenses page with the list of expenses and the form to add a new expense
    @GetMapping
    public String showExpenses(Model model, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        // Use findRecentExpensesByUserId to get expenses ordered by date descending;
        List<Expense> expenses = expenseService.findRecentExpensesByUserId(userId, 50);

        model.addAttribute("expenses", expenses);
        model.addAttribute("expenseForm", new Expense());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("currentDate", LocalDate.now().toString()); // Add the current date. Used to set max date on Thymeleaf html page.

        return "expenses";
    }

    // Add a new expense to the database and update the budget
    @PostMapping
    public String addExpense(@ModelAttribute("expenseForm") Expense expense, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                // Setting the user of the expense to the current user
                Long userId = (Long) request.getSession().getAttribute("userId");
                User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                expense.setUser(user);

                // Parsing the date string to LocalDate
                String dateString = request.getParameter("date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dateString, formatter);
                expense.setDate(date);

                // Retrieving category from the database using the provided category ID
                Long categoryId = Long.parseLong(request.getParameter("category"));
                Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
                expense.setCategory(category);

                expense.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                // Checking if the user has enough budget to add this expense
                BigDecimal currentBudget = budgetService.getCurrentBudget(userId);
                System.out.println("Current budget: " + currentBudget);
                System.out.println("Expense amount: " + expense.getAmount());
                System.out.println("Expense: " + expense);
                if (currentBudget.compareTo(expense.getAmount()) < 0) {
                    new ErrorMessage("Insufficient budget to add this expense.").addMessage(redirectAttributes);
                    return "redirect:/expenses";  // Return to the expenses page and show the error
                }

                // Use ExpenseService to add the expense and update the budget
                expenseService.addExpense(expense);
                budgetService.updateBudgetAfterExpense(expense);

            } catch (DateTimeParseException | NumberFormatException e) {
                model.addAttribute("dateError", "The date provided is invalid");
                return "expenses";
            }
        } else {
            System.out.println("Validation errors:");
            result.getAllErrors().forEach(error -> System.out.println(error.getObjectName() + " - " + error.getDefaultMessage()));
            model.addAttribute("errors", result.getAllErrors());
            return "expenses";
        }
        new SuccessMessage("Expense added successfully!").addMessage(redirectAttributes);
        return "redirect:/expenses";
    }

    // Delete an expense from the database and update the budget
    @PostMapping("/delete/{expenseId}")
    public String deleteExpense(@PathVariable Long expenseId, RedirectAttributes redirectAttributes) {
        try {
            expenseService.deleteExpense(expenseId);
            new SuccessMessage("Expense deleted successfully!").addMessage(redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMessage("Error occurred while deleting the expense.").addMessage(redirectAttributes);
        }
        return "redirect:/expenses";
    }
}
