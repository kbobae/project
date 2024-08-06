package com.mycompany.goldenTime.dao;

import org.apache.ibatis.annotations.Param;

import com.mycompany.goldenTime.model.MemberVO;

public interface IMemberRepository {
	void insertMember(MemberVO vo);
	int confirmId(String id);
	int userCheck(String id, String pw);
	MemberVO getMember(String id);
	void updateMember(MemberVO vo);
}
