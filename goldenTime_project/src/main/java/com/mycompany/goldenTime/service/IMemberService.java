package com.mycompany.goldenTime.service;

import com.mycompany.goldenTime.model.MemberVO;

public interface IMemberService {
	int insertMember(MemberVO vo);
	int userCheck(String id, String pw);
	MemberVO getMember(String id);
	void updateMember(MemberVO vo);
}
