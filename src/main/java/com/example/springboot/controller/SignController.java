package com.example.springboot.controller;

import com.example.springboot.UserDetailsImpl;
import com.example.springboot.UserDetailsServiceImpl;
import com.example.springboot.form.SignupForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class SignController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SignController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("userName", userDetails.getUsername());
        return "sign/index";
    }

    @GetMapping("/login")
    public String login() {
        return "sign/login";
    }

    @GetMapping("/signup")
    public String newSignup(SignupForm signupForm) {
        return "sign/signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated SignupForm signupForm, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "sign/signup";
        }

        if (userDetailsServiceImpl.isExistUser(signupForm.getUsername())) {
            model.addAttribute("signupError", "ユーザー名 " + signupForm.getUsername() + "は既に登録されています");
            return "sign/signup";
        }

        try {
            userDetailsServiceImpl.register(signupForm.getUsername(), signupForm.getEmail(), signupForm.getPassword(), "ROLE_USER");
        } catch (DataAccessException e) {
            System.out.println(e);
            model.addAttribute("signupError", "ユーザー登録に失敗しました");
            return "sign/signup";
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityContextHolder.clearContext();
        }

        try {
            request.login(signupForm.getUsername(), signupForm.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
