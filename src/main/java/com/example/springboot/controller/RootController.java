package com.example.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class RootController {

	@GetMapping("/hello")
	public String index(Model model) {
		return "root/hello";
	}

	@GetMapping("/bonjour")
	public String bonjour(Model model) {
		model.addAttribute("message", "Bonjour le monde!");
		return "root/bonjour";
	}
}
