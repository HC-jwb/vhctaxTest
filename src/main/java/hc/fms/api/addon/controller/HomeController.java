package hc.fms.api.addon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping("")
	public String root() {		
		return "redirect:/login.html";
	}
	

}
