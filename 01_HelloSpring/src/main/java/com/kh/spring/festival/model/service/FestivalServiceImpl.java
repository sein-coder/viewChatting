package com.kh.spring.festival.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.festival.model.dao.FestivalDao;
import com.kh.spring.festival.model.vo.Festival;

@Service
public class FestivalServiceImpl implements FestivalService {
	@Autowired
	SqlSessionTemplate session;
	@Autowired
	FestivalDao dao;
	
	@Override
	public int insertFestival(Festival festival) {
		
		return dao.insertFestival(session,festival);
	}

	@Override
	public List<Festival> selectFestivalList() {
		return dao.selectFestivalList(session);
	}
	
	
	
}
