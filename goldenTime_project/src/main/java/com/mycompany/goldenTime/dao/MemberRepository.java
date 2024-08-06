package com.mycompany.goldenTime.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.mycompany.goldenTime.model.MemberVO;

@Repository
public class MemberRepository implements IMemberRepository {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertMember(MemberVO vo) {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			sqlSession.insert("com.mycompany.goldenTime.dao.IMemberRepository.insertMember", vo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int confirmId(String id) {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			return sqlSession.selectOne("com.mycompany.goldenTime.dao.IMemberRepository.confirmId", id);
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int userCheck(String id, String pw) {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Map<String, String> map = new HashMap<>();
			map.put("id", id);
			map.put("pw", pw);
			return sqlSession.selectOne("com.mycompany.goldenTime.dao.IMemberRepository.userCheck", map);
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public MemberVO getMember(String id) {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			return sqlSession.selectOne("com.mycompany.goldenTime.dao.IMemberRepository.getMember", id);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	@Override
	public void updateMember(MemberVO vo) {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			sqlSession.selectOne("com.mycompany.goldenTime.dao.IMemberRepository.updateMember", vo);
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
