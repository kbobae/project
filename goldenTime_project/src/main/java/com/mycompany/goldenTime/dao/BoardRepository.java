package com.mycompany.goldenTime.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.goldenTime.dao.IBoardRepository;
import com.mycompany.goldenTime.model.BoardVO;

@Repository
public class BoardRepository implements IBoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<BoardVO> list(int startRow, int pageSize) {
		Map<String, Integer> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("pageSize", pageSize);
		// BoardMapper 호출
		return sqlSession.selectList("com.mycompany.goldenTime.dao.IBoardRepository.list", map);
	}
	
	@Override
	public int getTotalRecords() {
		return sqlSession.selectOne("com.mycompany.goldenTime.dao.IBoardRepository.getTotalRecords");
	}

	@Override
	public void write(String bName, String bTitle, String bContent, String bAuthorId) {
		sqlSession.insert("com.mycompany.goldenTime.dao.IBoardRepository.write", new BoardVO(bName, bTitle, bContent, bAuthorId));
	}

	@Override
	public BoardVO contentView(int bId) {
		upHit(bId);
		return sqlSession.selectOne("com.mycompany.goldenTime.dao.IBoardRepository.contentView", bId);
	}

	@Override
	public void modify(int bId, String bTitle, String bContent) { 
		sqlSession.update("com.mycompany.goldenTime.dao.IBoardRepository.modify", new BoardVO(bId, bTitle, bContent));
	}

	@Override
	public void delete(int bId) {
		sqlSession.update("com.mycompany.goldenTime.dao.IBoardRepository.delete", bId);
	}

	@Override
	public BoardVO reply_view(int str) {
		return sqlSession.selectOne("com.mycompany.goldenTime.dao.IBoardRepository.contentView", str);
	}

	@Override
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent) {
		replyShape(bGroup, bStep);
		BoardVO board = new BoardVO(bId, bName, bTitle, bContent, bGroup, bStep + 1, bIndent + 1);
		sqlSession.insert("com.mycompany.goldenTime.dao.IBoardRepository.reply", board);
	}

	@Override
	public void replyShape(@Param("bGroup") int bGroup, @Param("bStep") int bStep) {
		BoardVO board = new BoardVO(bGroup, bStep);
	    sqlSession.update("com.mycompany.goldenTime.dao.IBoardRepository.replyShape", board);
	}

	@Override
	public void upHit(int bId) {
		sqlSession.update("com.mycompany.goldenTime.dao.IBoardRepository.upHit", bId);
	}

}
