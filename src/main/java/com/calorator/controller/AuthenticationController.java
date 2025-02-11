package com.calorator.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("userId") == null && session.getAttribute("role") == null) {
            return "redirect:/login";
        } else if (session.getAttribute("role").equals("user")) {
            return "dashboard";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        if (session.getAttribute("userId") == null && session.getAttribute("role") == null) {
            return "redirect:/login";
        } else if (session.getAttribute("role").equals("admin")) {
            return "admin";
        } else {
            return "redirect:/dashboard";

        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}
