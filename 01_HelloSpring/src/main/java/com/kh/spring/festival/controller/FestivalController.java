package com.kh.spring.festival.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.festival.model.service.FestivalService;

@Controller
public class FestivalController {
	private Logger logger=LoggerFactory.getLogger(FestivalController.class);
	@Autowired
	FestivalService service;
	
	
	@RequestMapping("/festival/festivalList")
	public String FestivalList(Model model) {
		return "festival/festivalList"; 
	}
	 
	@RequestMapping("/festival/festivalForm")
	public String FestivalForm() {
		return "festival/festivalForm";
	}
	  
 		
	
	

}
