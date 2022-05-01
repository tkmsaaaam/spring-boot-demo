package com.example.springboot;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RootController {

	@GetMapping("/hello")
	public String index(Model model) {
		return "hello";
	}

	@GetMapping("/bonjour")
	public String bonjour(Model model) {
		model.addAttribute("message", "Bonjour le monde!");
		return "bonjour";
	}

}
