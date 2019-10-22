package com.kh.spring.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.demo.model.service.DemoService;
import com.kh.spring.demo.model.vo.Dev;

@Controller
public class DemoController {
	
	@Autowired
	private DemoService service;
	//화면전환용 HandlerMapping
	
//	@RequestMapping("주소값 mapping값")
	@RequestMapping("/demo/demo.do")
	public String demo() {
		System.out.println("/demo/demo.do가 호출됨.!");
		return "demo/demo";//==/WEB-INF/views/demo/demo.jsp
	}
	
	@RequestMapping("/demo/demo1.do")
	public String demo1(HttpServletRequest req) {
		
		System.out.println(req.getParameter("devName"));
		System.out.println(req.getParameter("devAge"));
		System.out.println(req.getParameter("devEmail"));
		System.out.println(req.getParameter("devGender"));
		System.out.println(req.getParameter("devLang"));
		Dev dev=new Dev();
		dev.setDevName(req.getParameter("devName"));
		dev.setDevAge(Integer.parseInt(req.getParameter("devAge")));
		dev.setDevEmail(req.getParameter("devEmail"));
		dev.setDevGender(req.getParameter("devGender"));
		dev.setDevLang(req.getParameterValues("devLang"));
		req.setAttribute("dev", dev);		
		
		return "demo/demoView";
	}
	
	/* @RequestParam을 이용하여 파라미터 받기 */
	@RequestMapping("/demo/demo2.do")
//	public String demo2(
//				@RequestParam(value="devName") String devName,
//				@RequestParam(value="devAge",required=false,defaultValue="19") int devAge,
//				@RequestParam String devEmail,
//				@RequestParam String devGender,
//				@RequestParam(value="devLang",required=false) String[] devLang,
//				HttpServletRequest req) {
	public String demo2(String devName, int devAge, 
			String devEmail, String devGender,String[] devLang
			,HttpServletRequest req) {
		System.out.println(devName);
		System.out.println(devAge);
		System.out.println(devEmail);
		System.out.println(devGender);
		System.out.println(devLang);
		req.setAttribute("dev", new Dev(devName,devAge,devEmail,devGender,devLang));
		return "demo/demoView";
	}
	
	@RequestMapping("/demo/demo3.do")
	public String demo3(@RequestParam Map map,String[] devLang,HttpServletRequest req) {
		System.out.println(map);
		System.out.println(devLang[0]);
		System.out.println(devLang[1]);
		req.setAttribute("dev", map);
		return "demo/demoView";
	}
	
	//command객체로 받기
	@RequestMapping("/demo/demo4.do")
	public String demo4(Dev dev,HttpServletRequest req) {
		req.setAttribute("dev", dev);		
		return "demo/demoView";
	}
	
	@RequestMapping("/demo/insertDemo.do")
	public String insertDemo(Dev dev,Model model) {
		
		int result=service.insertDemo(dev);
		System.out.println(result);
		
		
		//redirect하기!
		return "redirect:/index.jsp";
	}
	
	//리스트받아오기
	@RequestMapping("/demo/selectDevList.do")
	public String devList(Model model) {
		List<Dev> list=service.selectDevList();
		//Model은 request객체처럼 데이터를 보관하는 객체
		model.addAttribute("list",list);
		return "demo/demoList";
	}
	
	
}





