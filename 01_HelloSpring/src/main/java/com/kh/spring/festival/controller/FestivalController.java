package com.kh.spring.festival.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.festival.model.service.FestivalService;
import com.kh.spring.festival.model.vo.Festival;
@Controller
public class FestivalController {
	private Logger logger=LoggerFactory.getLogger(FestivalController.class);
	@Autowired
	FestivalService service;
	
	@RequestMapping("/festival/festivalForm")
	public String FestivalForm() {
		return "/festival/festivalForm";
	}
	
	@RequestMapping("/festival/festivalList")
	public String insertFestival(Festival festival,Model model) {
		
		int result=service.insertFestival(festival);
		logger.debug(""+result);
		
		String msg="";
		String loc="/";
		
		if(result>0) {
			msg="등록을 성공하셨습니다.";
		}else {
			msg="등록을 실패하셨습니다.";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		return "common/msg";
	}
	
	@RequestMapping("/festival/festivalFormEnd.do")
	public List<Festival> selectFestivalList(Model model){
		return service.selectFestivalList();
	}
	

}
