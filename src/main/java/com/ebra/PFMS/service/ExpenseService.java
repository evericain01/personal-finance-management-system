package com.ebra.PFMS.service;

import com.ebra.PFMS.entity.Budget;
import com.ebra.PFMS.entity.Expense;
import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.repository.BudgetRepository;
import com.ebra.PFMS.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    // Finds all expenses for a user within a date range
    public List<Expense> findExpensesByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    // Saves an expense and updates the latest budget's amount
    public void addExpense(Expense expense) {
        expenseRepository.save(expense);

        // After saving the expense, update the latest budget's amount
        User user = expense.getUser();
        Budget latestBudget = budgetRepository.findFirstByUserOrderByCreatedAtDesc(user);
        if (latestBudget != null) {
            latestBudget.setAmount(latestBudget.getAmount().subtract(expense.getAmount()));
            budgetRepository.save(latestBudget);
        }
    }

    // Finds the most recent expenses for a user by user id
    public List<Expense> findRecentExpensesByUserId(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("date").descending());
        return expenseRepository.findRecentExpensesByUserId(userId, pageable).getContent();
    }


    // Gets the total expenses for a user this month
    public BigDecimal getTotalExpensesThisMonth(Long userId) {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return expenseRepository.sumExpensesByUserAndDateRange(userId, startOfMonth, endOfMonth);
    }

    // Gets the total expenses for a user last month by user id
    public Map<String, BigDecimal> getSpendingByCategory(Long userId) {
        List<Object[]> results = expenseRepository.findSpendingByCategory(userId);
        Map<String, BigDecimal> spendingByCategory = new HashMap<>();
        for (Object[] result : results) {
            String category = (String) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            spendingByCategory.put(category, amount);
        }
        return spendingByCategory;
    }

    // Gets the maximum spending for a category by user id
    public BigDecimal getMaxCategorySpending(Long userId) {
        return expenseRepository.findMaxSpendingByCategory(userId).orElse(BigDecimal.ZERO);
    }

    // Deletes an expense by expense id
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
