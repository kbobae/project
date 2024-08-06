package com.mycompany.goldenTime.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.goldenTime.model.BoardVO;
import com.mycompany.goldenTime.model.MemberVO;
import com.mycompany.goldenTime.service.IBoardService;

@Controller
public class BoardController {

//private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private IBoardService boardService;
	
	// 게시글 목록 페이지 이동
	@GetMapping("/list")
	public String listPage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {		
		List<BoardVO> boardList = boardService.list(page);

		// 전체 게시글 수 가져오기
        int totalPosts = boardService.getTotalRecords();

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalPosts / boardService.PAGE_SIZE);

        // 모델에 현재 페이지와 총 페이지 수 추가
        model.addAttribute("list", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "list";
	}
	
	// 게시글 작성 페이지 이동
	@GetMapping("/write")
    public String writePage(HttpSession session) {
		// 세션에서 로그인 상태 확인
        String loggedInUserId = (String) session.getAttribute("ValidMem");
        if (loggedInUserId != null) {
            // 로그인된 사용자라면 게시글 작성 페이지로 이동
            return "write_view";
        } else {
            // 로그인되지 않은 사용자라면 로그인 페이지로 이동
            return "login";
        }
    }
	
	// 게시글 작성
	@PostMapping("/write")
	public String write(HttpSession session, @ModelAttribute("content_view") MemberVO vo, String bName, String bTitle, String bContent,
			RedirectAttributes redirectAttributes) {
		System.out.println("write post 요청");
		String bAuthorId = (String) session.getAttribute("id"); //로그인된 사용자 ID 가져오기
		boardService.write(bName, bTitle, bContent, bAuthorId);
		return "redirect:/list";
	}
	
	// 게시글 상세보기
	@GetMapping("/content_view")
	public String content_viewPage(HttpSession session, Model model, int bId) {
		System.out.println("content_view post 요청");
		BoardVO content = boardService.contentView(bId);
		model.addAttribute("content_view", content);
		
		String loggedInUserId = (String) session.getAttribute("ValidMem");
		System.out.println("로그인 상태: " + loggedInUserId);
		
		//로그인한 사용자인 경우
		if ("yes".equals(loggedInUserId)) {
			String currentUserId = (String) session.getAttribute("id");
			System.out.println("로그인된 사용자 ID: " + currentUserId);
			System.out.println("게시글 작성자 ID: " + content.getbAuthorId());
	        if(currentUserId != null && currentUserId.equals(content.getbAuthorId())) { // 현재 사용자가 게시글 작성자인지 확인
	        	model.addAttribute("isAuthor", true);
	        } else {
	        	model.addAttribute("isAuthor", false);
	        }
        } else {
            //로그인하지 않은 경우
        	model.addAttribute("isAuthor", false);
        }
		return "content_view";
	}
	
	// 게시글 수정
	@PostMapping("/boardModify")
	public String boardModify(int bId, String bTitle, String bContent) {
		System.out.println("boardModify post 요청");
		System.out.println(bId + ", " + bTitle + ", " + bContent);
		boardService.modify(bId, bTitle, bContent);
		return "redirect:/list";
	}
	
	// 게시글 삭제
	@PostMapping("/delete")
	public String delete(int bId) {
		System.out.println("delete post 요청");
		boardService.delete(bId);
		return "redirect:/list";
	}
	
	// 게시글 답장 페이지 이동
	@GetMapping("/reply_view")
	public String reply_view(Model model, int bId) {
		System.out.println("reply_view get 요청");
		if(bId > 0) {
			BoardVO vo = boardService.reply_view(bId);
			model.addAttribute("reply_view", vo);
		}
		return "reply_view";
	}
	
	// 게시글 답장
	@PostMapping("/reply")
	public String reply(HttpSession session, Model model, int bId, String bName, 
			String bTitle, String bContent, int bGroup, int bStep, int bIndent) {
		System.out.println("reply post 요청");
		String authorName = (String) session.getAttribute("name");
		System.out.println(authorName);
		BoardVO vo = boardService.reply_view(bId);
		model.addAttribute("reply_view", vo);
		System.out.println("bGroup: " + bGroup);
        System.out.println("bStep: " + bStep);
        System.out.println("bIndent: " + bIndent);
        
		boardService.reply(bId, authorName, bTitle, bContent, bGroup, bStep, bIndent);
		return "redirect:/list";
	}
}