package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;
@Service
public class MemberServiceImpl implements MemberService {
	//session관리.(connection)
	//connection생성, connection.close(), 트랜젝션처리
	@Autowired
	SqlSessionTemplate session;
	@Autowired
	MemberDao dao;
	
	@Override
	public Member selectMemberOne(Member m) {
		return dao.selectMemberOne(session,m);
	}

	@Override
	public int insertMember(Member m) {
		return dao.insertMember(session, m);
	}

}






