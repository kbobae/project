package com.mycompany.goldenTime.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.mycompany.goldenTime.model.BoardVO;

public interface IBoardRepository {
	List<BoardVO> list(@Param("startRow") int startRow, @Param("pageSize") int pageSize);
	int getTotalRecords();
	void write(@Param("bName") String bName, @Param("bTitle") String bTitle, @Param("bContent") String bContent, @Param("bAuthorId") String bAuthorId);
	BoardVO contentView(@Param("bId") int bId);
	void modify(@Param("bId") int bId, @Param("bTitle") String bTitle, @Param("bContent") String bContent);
	void delete(@Param("bId") int bId);
	BoardVO reply_view(@Param("bId") int str);
	void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);
	void replyShape(@Param("bGroup") int bGroup, @Param("bStep") int bStep);
	void upHit(@Param("bId") int bId);
}
