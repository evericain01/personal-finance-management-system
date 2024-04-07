package com.ebra.PFMS.service;

import com.ebra.PFMS.entity.Expense;
import com.ebra.PFMS.entity.Report;
import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.repository.ReportRepository;
import com.ebra.PFMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    // Creating a report from a list of expenses
    public String createReportData(List<Expense> expenses) {
        StringBuilder reportBuilder = new StringBuilder();
        BigDecimal totalSpent = BigDecimal.ZERO;
        Map<String, BigDecimal> categoryTotals = new HashMap<>();

        // Adding headers
        reportBuilder.append("User ID:, Description:, Price:, Category:, Date:, Created At:\n");

        // Looping through all expenses to append their data in text format
        for (Expense expense : expenses) {
            reportBuilder
                    .append(expense.getUser().getId())
                    .append(", ")
                    .append(expense.getDescription())
                    .append(", ")
                    .append("$" + expense.getAmount())
                    .append(", ")
                    .append(expense.getCategory().getName())
                    .append(", ")
                    .append(expense.getDate())
                    .append(", ")
                    .append(expense.getCreatedAt())
                    .append("\n");

            // Calculating total spent
            totalSpent = totalSpent.add(expense.getAmount());

            // Calculating total spent per category
            categoryTotals.merge(expense.getCategory().getName(), expense.getAmount(), BigDecimal::add);
        }

        // Finding the most used category
        String mostUsedCategory = categoryTotals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        // Appending total spent and most used category at the end of the report
        reportBuilder.append("\nTotal Spent:,")
                .append("$" + totalSpent)
                .append("\nMost Expensive Category:,")
                .append(mostUsedCategory);

        return reportBuilder.toString();
    }

    // Saves a report to the database
    @Transactional
    public Report saveReport(Long userId, LocalDate startDate, LocalDate endDate, String reportData) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();
        report.setUser(user);
        report.setFilterStartDate(startDate);
        report.setFilterEndDate(endDate);
        report.setGeneratedAt(new Timestamp(System.currentTimeMillis()));
        report.setData(reportData);

        return reportRepository.save(report);
    }
}