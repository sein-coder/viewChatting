package com.kh.spring.memo.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

public interface MemoDao {
	List<Map> selectMemo(SqlSessionTemplate session);
	int insertMemo(SqlSessionTemplate session,Map map);
	
}




