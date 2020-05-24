package com.heytusar.cbg.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiController {
	@RequestMapping("/")
	public String index(Model model) {
		String copyrightInfo = "<strong>&copy; 2020</strong> All rights protected."; //apiService.getCopyrightInfo();
		model.addAttribute("copyrightInfo", copyrightInfo);
		
		List<String> tasks = new ArrayList<String>();
		tasks.add("Eat");
		tasks.add("Sleep");
		tasks.add("Code");
		model.addAttribute("tasks", tasks);
		return "index";
	}
}
