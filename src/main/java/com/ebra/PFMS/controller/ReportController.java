package com.ebra.PFMS.controller;

import com.ebra.PFMS.entity.Expense;
import com.ebra.PFMS.entity.Report;
import com.ebra.PFMS.service.ExpenseService;
import com.ebra.PFMS.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExpenseService expenseService;

    // Helper method to get the current user ID from the session
    private Long getCurrentUserId(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("userId");
    }

    // Shows the reports page
    @GetMapping("/reports")
    public String showReports() {
        return "reports";
    }

    // Downloads the report as a CSV file and saves it in the database
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) {

        Long userId = getCurrentUserId(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Getting the expenses for the user and date range
        List<Expense> expenses = expenseService.findExpensesByUserAndDateRange(userId, startDate, endDate);
        String reportData = reportService.createReportData(expenses);

        // Saving the report in the database
        Report savedReport = reportService.saveReport(userId, startDate, endDate, reportData);

        // Converting the report data to a CSV format (or any required format)
        ByteArrayResource resource = new ByteArrayResource(reportData.getBytes());

        // Returning the report as a CSV file
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report_" + savedReport.getId() + ".csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}
