package com.mycompany.goldenTime.service;

import java.util.List;

import com.mycompany.goldenTime.model.BoardVO;

public interface IBoardService {
	public static final int PAGE_SIZE = 7;
	List<BoardVO> list(int page);
	int getTotalRecords();
	void write(String bName, String bTitle, String bContent, String bAuthorId);
	BoardVO contentView(int bId);
	void modify(int bId, String bTitle, String bContent);
	void delete(int bId);
	BoardVO reply_view(int str);
	void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);
	void replyShape(int bGroup, int bStep);
	void upHit(int bId);
}