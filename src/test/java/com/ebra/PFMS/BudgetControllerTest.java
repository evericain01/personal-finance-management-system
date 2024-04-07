package com.ebra.PFMS;

import com.ebra.PFMS.controller.BudgetController;
import com.ebra.PFMS.service.BudgetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BudgetController.class)
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    // Testing the setNegativeBudget method
    @Test
    public void setNegativeBudget() throws Exception {
        this.mockMvc.perform(post("/budget")
                        .param("amount", "-100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/budget"))
                .andExpect(flash().attribute("errorMessage", "Budget amount cannot be lower than 0 dollars."));

        System.out.println("setNegativeBudget test passed successfully.");
    }

    // Testing the setValidBudget method
    @Test
    public void setValidBudget() throws Exception {
        mockMvc.perform(post("/budget")
                        .param("amount", "100"))
                .andExpect(redirectedUrl("/budget"))
                .andExpect(flash().attributeExists("successMessage"));

        System.out.println("setValidBudget test passed successfully.");
    }
}