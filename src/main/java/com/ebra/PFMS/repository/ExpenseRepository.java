package com.ebra.PFMS.repository;

import com.ebra.PFMS.entity.Expense;
import com.ebra.PFMS.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Finds all expenses by user id
    List<Expense> findByUserId(Long userId);

    // Finds all expenses by user id and date
    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    // Finds all expenses by user id and category id and date
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId ORDER BY e.date DESC")
    Page<Expense> findRecentExpensesByUserId(@Param("userId") Long userId, Pageable pageable);

    // Gets the total amount of expenses for a user
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate")
    BigDecimal sumExpensesByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Finds all expenses by user id and category id and date
    @Query("SELECT c.name, SUM(e.amount) FROM Expense e JOIN e.category c WHERE e.user.id = :userId GROUP BY c.name")
    List<Object[]> findSpendingByCategory(@Param("userId") Long userId);

    // Finds the maximum spending by category for a user
    @Query(value = "SELECT MAX(total) FROM (SELECT SUM(e.amount) as total FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.user_id = :userId GROUP BY c.name) as sub", nativeQuery = true)
    Optional<BigDecimal> findMaxSpendingByCategory(@Param("userId") Long userId);
}