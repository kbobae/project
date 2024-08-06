package com.mycompany.goldenTime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.goldenTime.dao.IBoardRepository;
import com.mycompany.goldenTime.model.BoardVO;

@Service
public class BoardService implements IBoardService {
	
	@Autowired
	IBoardRepository boardRepository;
	
	@Override
	public List<BoardVO> list(int page) {
		int startRow = (page - 1) * PAGE_SIZE;
		System.out.println("StartRow: " + startRow);
		List<BoardVO> result = boardRepository.list(startRow, PAGE_SIZE);
		System.out.println("Returned data size: " + result.size());
		return result;
	}

	@Override
	public int getTotalRecords() {
		return boardRepository.getTotalRecords();
	}

	@Override
	public void write(String bName, String bTitle, String bContent, String bAuthorId) {
		boardRepository.write(bName, bTitle, bContent, bAuthorId);
	}

	@Override
	public BoardVO contentView(int bId) {
		return boardRepository.contentView(bId);
	}

	@Override
	public void modify(int bId, String bTitle, String bContent) {
		boardRepository.modify(bId, bTitle, bContent);
	}

	@Override
	public void delete(int bId) {
		boardRepository.delete(bId);
	}

	@Override
	public BoardVO reply_view(int str) {
		return boardRepository.reply_view(str);
	}

	@Override
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep,
			int bIndent) {
		boardRepository.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
	}

	@Override
	public void replyShape(int bGroup, int bStep) {
		boardRepository.replyShape(bGroup, bStep);
	}

	@Override
	public void upHit(int bId) {
		boardRepository.upHit(bId);
	}

}
