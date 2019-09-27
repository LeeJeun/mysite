package kr.co.itcen.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainCotroller {
	
	@RequestMapping({"", "/main"})
	public String index() {
		return "main/index";
	}
}
