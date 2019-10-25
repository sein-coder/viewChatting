package com.kh.spring.festival.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.kh.spring.festival.model.vo.Festival;

public interface FestivalDao {

	int insertFestival(SqlSessionTemplate session, Festival festival);

	List<Festival> selectFestivalList(SqlSessionTemplate session);
	
	

}
