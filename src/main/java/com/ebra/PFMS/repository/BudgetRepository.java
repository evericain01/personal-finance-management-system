package com.ebra.PFMS.repository;

import com.ebra.PFMS.entity.Budget;
import com.ebra.PFMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    // Finds the most recent budget for a user
    Budget findFirstByUserOrderByCreatedAtDesc(User user);
}
