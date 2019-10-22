package com.kh.spring.member.model.service;

import org.springframework.stereotype.Component;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	int insertMember(Member m);
	Member selectMemberOne(Member m);
	
}





