package com.kh.spring.demo.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.demo.model.vo.Dev;

@Repository
public class DemoDaoImpl implements DemoDao{

	@Override
	public int insertDemo(SqlSessionTemplate session, Dev dev) {
		return session.insert("dev.insertDemo",dev);
	}

	@Override
	public List<Dev> selectDevList(SqlSessionTemplate session) {
		return session.selectList("dev.selectDevList");
	}
	
	
	
	
}


