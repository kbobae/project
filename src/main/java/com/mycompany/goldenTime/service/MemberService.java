package com.mycompany.goldenTime.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.goldenTime.dao.IMemberRepository;
import com.mycompany.goldenTime.model.MemberVO;

@Service
public class MemberService implements IMemberService {

	@Autowired
	IMemberRepository memberRepository;
	
	@Override
	public int insertMember(MemberVO vo) {
		int ri = 0;
		try {
			//아이디 중복 확인
			int count = memberRepository.confirmId(vo.getId());
			if(count > 0) {
				//이미 존재하는 아이디인 경우
				ri = -1;
			} else {
				//존재하지 않는 경우
				memberRepository.insertMember(vo);
				ri = 1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ri;
	}

	@Override
	public int userCheck(String id, String pw) {
		int ri = 0;
		try {
			//아이디 확인
			int count = memberRepository.confirmId(id);
			if( count == 0) {
				return -1; // 아이디가 존재하지 않을 때
			} else {
				count = memberRepository.userCheck(id, pw);
				if(count == 1) {
					return 1; // 로그인 성공
				} else {
					return 0; //비밀번호 오류
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return ri;
	}

	@Override
	public MemberVO getMember(String id) {
		try {
			return memberRepository.getMember(id);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Override
	public void updateMember(MemberVO vo) {
		try {
			memberRepository.updateMember(vo);
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}

}
