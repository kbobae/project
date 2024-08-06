package com.mycompany.goldenTime.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.goldenTime.model.MemberVO;
import com.mycompany.goldenTime.service.IMemberService;

@Controller
public class MemberController {
	
	//private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private IMemberService memberService;
	
	// 메인 페이지로 이동
	@GetMapping("/index")
	public String mainPage() {
		return "index";
	}
	
	// 로그인 페이지로 이동
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	} 
	
	// 로그인 실행
	@PostMapping("/loginOk")
	public String loginSuccess(HttpServletRequest request, HttpSession session, Model model) {
		System.out.println("loginOk post 호출");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		int checkNum = memberService.userCheck(id, pw);
		if(checkNum == -1) {
			// 아이디가 존재하지 않을 때
			model.addAttribute("errorMessage", "아이디가 존재하지 않습니다.");
			return "loginOk";
		} else if(checkNum == 0) {
			// 비밀번호가 다를 때
			model.addAttribute("errorMessage", "비밀번호를 재확인하세요");
			return "loginOk";
		} else if(checkNum == 1) {
			// 존재하지 않는 회원일 때
			MemberVO vo = memberService.getMember(id);
			if(vo == null) {
				model.addAttribute("errorMessage", "존재하지 않는 회원입니다.");
				return "loginOk";
			} else {
				// 로그인 성공 시
				System.out.println("로그인 성공");
				String name = vo.getName();
				session.setAttribute("id", id);
				session.setAttribute("name", name);
				session.setAttribute("ValidMem", "yes");
				return "redirect:/index";				
			}
		}
		return "error";
	}
	
	// 회원가입 페이지로 이동
	@GetMapping("/joinOk")
	public String insertMember(Model model) {
		model.addAttribute("member", new MemberVO());
		return "join";
	}
	
	// 회원가입 실행
	@PostMapping("/joinOk")
	public String insertMember(HttpServletRequest request, MemberVO vo, Model model) {
		System.out.println("joinOk post 요청");
		String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        vo.setId(id);
        vo.setPw(pw);
        vo.setName(name);
        vo.setEmail(email);
        vo.setPhone(phone);
        vo.setAddress(address);
        vo.setrDate(new Timestamp(System.currentTimeMillis()));
        
        int ri = memberService.insertMember(vo);
		try {
			if(ri == -1) {
			model.addAttribute("joinMessage", "이미 존재하는 아이디입니다.");	
			} else {
				if(ri == 1) {
					model.addAttribute("joinMessage", "회원 가입이 성공적으로 완료되었습니다.");
				} else {
					model.addAttribute("joinMessage", "회원가입에 실패했습니다.");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("joinMessage", "회원가입 중 오류가 발생했습니다.");
		}
		return "joinOk";
	}
	
	// 회원 정보 수정 페이지로 이동
	@GetMapping("/modify")
	public String updateMember(String id, Model model) {
	    MemberVO vo = memberService.getMember(id);
	    if (vo == null) {
	        return "redirect:/errorPage"; // 사용자 정의 에러 페이지로 리다이렉트 할 수 있습니다.
	    }
	    model.addAttribute("modify", vo);
	    return "modify";
	}
	
	// 
	@GetMapping("/modifyOk")
	public String updateSuccess() {
		return "modifyOk";
	} 
	
	// 회원 정보 수정
	@PostMapping("/modifyOk")
	public String updateMember(@ModelAttribute("modify") MemberVO vo, RedirectAttributes redirectAttributes) {
		System.out.println("modifyOk post 요청");
		memberService.updateMember(vo);
		redirectAttributes.addFlashAttribute("modifyMessage", "회원정보가 성공적으로 업데이트 되었습니다.");
		return "redirect:/modifyOk";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logoutSuccess(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	
	
	//예외 처리하는 메소드
/*		@ExceptionHandler({RuntimeException.class})
		public ModelAndView runtimeException(HttpServletRequest request, Exception ex) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("url", request.getRequestURI());
			mav.addObject("exception", ex);
			mav.setViewName("error/runtime"); 
			return mav; 
		}*/
	
}
