package com.mycompany.goldenTime.model;

import java.sql.Timestamp;

public class BoardVO {
	private int bId; //게시글 ID
	private String bAuthorId; //작성자 ID
	private String bName;
	private String bTitle;
	private String bContent;
	private Timestamp bDate;
	private int bHit;
	private int bGroup;
	private int bStep;
	private int bIndent;
	
	public BoardVO() {
	}
	
	public BoardVO(String bName, String bTitle, String bContent, String bAuthorId) {
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bAuthorId = bAuthorId;
	}
	
	public BoardVO(int bId, String bTitle, String bContent) {
		this.bId = bId;
		this.bTitle = bTitle;
		this.bContent = bContent;
	}
	
	public BoardVO(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent) {
		this.bId = bId;
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bGroup = bGroup;
		this.bStep = bStep;
		this.bIndent = bIndent;
	}

	public BoardVO(int bGroup, int bStep) {
		this.bGroup = bGroup;
		this.bStep = bStep;
	}
	
	public BoardVO(int bId, String bName, String bTitle, String bContent, Timestamp bDate, int bHit, int bGroup, int bStep, int bIndent) {
		this.bId = bId;
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bDate = bDate;
		this.bHit = bHit;
		this.bGroup = bGroup;
		this.bStep = bStep;
		this.bIndent = bIndent;
	}

	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getbAuthorId() {
		return bAuthorId;
	}
	public void setbAuthorId(String bAuthorId) {
		this.bAuthorId = bAuthorId;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getbTitle() {
		return bTitle;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public Timestamp getbDate() {
		return bDate;
	}
	public void setbDate(Timestamp bDate) {
		this.bDate = bDate;
	}
	public int getbHit() {
		return bHit;
	}
	public void setbHit(int bHit) {
		this.bHit = bHit;
	}
	public int getbGroup() {
		return bGroup;
	}
	public void setbGroup(int bGroup) {
		this.bGroup = bGroup;
	}
	public int getbStep() {
		return bStep;
	}
	public void setbStep(int bStep) {
		this.bStep = bStep;
	}
	public int getbIndent() {
		return bIndent;
	}
	public void setbIndent(int bIndent) {
		this.bIndent = bIndent;
	}

}
