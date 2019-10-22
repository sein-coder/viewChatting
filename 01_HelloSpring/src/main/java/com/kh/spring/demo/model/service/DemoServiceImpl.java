package com.kh.spring.demo.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kh.spring.demo.model.dao.DemoDao;
import com.kh.spring.demo.model.vo.Dev;

@Service
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	private DemoDao dao;
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public List<Dev> selectDevList() {
		return dao.selectDevList(session);
	}



	@Override
	public int insertDemo(Dev dev) {
		return dao.insertDemo(session,dev);
	}
	
	
	
	
}





