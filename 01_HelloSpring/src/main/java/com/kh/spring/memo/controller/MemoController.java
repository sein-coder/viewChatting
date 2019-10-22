package com.kh.spring.memo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.memo.model.service.MemoService;
@Controller
public class MemoController {

	@Autowired
	private MemoService service;
	
	@RequestMapping("/memo/memo.do")
	public String selectMemo(Model model) {
		model.addAttribute("memo",service.selectMemo());
		return "memo/memo";
	}
	@RequestMapping("/memo/insertMemo.do")
	public String insertMemo(@RequestParam Map map,Model model)
	{
		int result=service.insertMemo(map);
		String msg=result>0?"입력성공":"입력실패";
		String loc="/memo/memo.do";
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		return "common/msg";
	}
}







