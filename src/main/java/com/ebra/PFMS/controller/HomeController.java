package com.ebra.PFMS.controller;

import com.ebra.PFMS.config.PasswordUtil;
import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.service.ErrorMessage;
import com.ebra.PFMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    // Renders the login page
    @GetMapping("/")
    public String login() {
        return "index";
    }

    // Checking the username and password and redirect to the dashboard page if successful
    @PostMapping("/login")
    public String performLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && PasswordUtil.checkPassword(password, user.get().getPassword())) {
            request.getSession().setAttribute("userId", user.get().getId());
            System.out.println("User ID: " + user.get().getId() + " is logged in");
            return "redirect:/dashboard";  // Redirect to dashboard page upon successful login
        } else {
            new ErrorMessage("Invalid username or password.").addMessage(redirectAttributes);
            return "redirect:/"; // Remain on the login page if authentication fails
        }
    }

    // Logs out the user and redirects to the login page
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // Invalidating the session to clear stored attributes
        }
        return "redirect:/";  // Redirect to login page
    }
}
