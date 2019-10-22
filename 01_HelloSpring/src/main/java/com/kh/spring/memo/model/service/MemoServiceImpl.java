package com.kh.spring.memo.model.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.memo.model.dao.MemoDao;
@Service
public class MemoServiceImpl implements MemoService {
	@Autowired
	MemoDao dao;
	@Autowired
	SqlSessionTemplate session;
	@Override
	public List<Map> selectMemo() {
		return dao.selectMemo(session);
	}

	@Override
	public int insertMemo(Map map) {
		return dao.insertMemo(session, map);
	}

	
}
