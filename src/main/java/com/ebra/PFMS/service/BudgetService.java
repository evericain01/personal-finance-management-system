package com.ebra.PFMS.service;

import com.ebra.PFMS.entity.Budget;
import com.ebra.PFMS.entity.Expense;
import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.repository.BudgetRepository;
import com.ebra.PFMS.repository.ExpenseRepository;
import com.ebra.PFMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    // To set the current budget for a user
    public Budget setCurrentBudget(Long userId, BigDecimal amount) {
        Budget budget = new Budget();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        budget.setUser(user);
        budget.setAmount(amount);
        budget.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return budgetRepository.save(budget);
    }

    // To get the current budget for a user
    public BigDecimal getCurrentBudget(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Budget latestBudget = budgetRepository.findFirstByUserOrderByCreatedAtDesc(user);
        if (latestBudget == null) {
            return BigDecimal.ZERO;
        }
        return latestBudget.getAmount();
    }

    // To update the budget after an expense is added
    public void updateBudgetAfterExpense(Expense expense) {
        User user = expense.getUser();
        Budget currentBudget = budgetRepository.findFirstByUserOrderByCreatedAtDesc(user);
        budgetRepository.save(currentBudget);
    }
}
