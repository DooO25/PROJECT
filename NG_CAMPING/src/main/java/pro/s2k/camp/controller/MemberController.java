package pro.s2k.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.service.MemberService;
import pro.s2k.camp.vo.MemberVO;


@Slf4j
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value = "/naverCallback.do", produces = "application/json; charset=UTF8")
	public String naverCallback(Model model) {
		String naverInfo = memberService.naverMemberProfile();
		model.addAttribute("ni", naverInfo);
		return "naverCallback";
	}
	
	@RequestMapping(value = "/naverInsert.do", produces = "application/json; charset=UTF8")
	public String naverInsert(Model model) {
		String naverInfo = memberService.naverMemberProfile();
		model.addAttribute("ni", naverInfo);
		return "naverInsert";
	}
	
	
	
	

	@RequestMapping(value = "/insert.do", method = RequestMethod.GET)
	public String insert() {
		return "insert";
	}

	@RequestMapping(value = "/insertOk.do", method = RequestMethod.GET)
	public String insertOk() {
		return "403";
	}

	@RequestMapping(value = "/insertOk.do", method = RequestMethod.POST)
	public String insertOk(MemberVO memberVO, Model model) {
		memberVO.setAuthkey(UUID.randomUUID().toString());
		memberVO.setMb_password(bCryptPasswordEncoder.encode(memberVO.getMb_password()));
		memberService.insert(memberVO);
		model.addAttribute("memberVO", memberVO);

		return "redirect:/main.do";
	}

	// 아이디 체크
	@RequestMapping(value = "/idCheck.do", produces = "text/plain;charset=UTF-8") // UTF-8로 글자 하나 넣겠다.
	@ResponseBody
	public String idCheck(@RequestParam(required = false) String userid) {
		String userids[] = "admin,root,master,webmaster,administrator, ".split(","); // 금지 아이디 목록
		int count = 0;
		for (String id : userids) {
			if (userid.contains(id)) {
				count = 1;
				break;
			}
		}
		if (count == 0) {
			// 서비스를 호출하여 동일한 아이디의 개수를 얻어소 count변수에 넣는다.
			count = memberService.idCheck(userid);
		}
		return count + "";
	}

	// 닉네임 체크
	@RequestMapping(value = "/nickCheck.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String nickCheck(@RequestParam(required = false) String nick) {
		String nicks[] = "admin,root,master,webmaster,administrator, ".split(","); // 금지 아이디 목록
		int count = 0;
		for (String ni : nicks) {
			if (nick.contains(ni)) {
				count = 1;
				break;
			}
		}
		if (count == 0) {
			// 서비스를 호출하여 동일한 닉네임 갯수를 얻어서 count 변수에 넣는다.
			count = memberService.nickCheck(nick);
		}
		return count + "";
	}

	// 로그인 접속 실패시 (성공시는 CustomSuccessHandler)
	@RequestMapping(value = "/login.do")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "아이디가 없거나 비번이 일치하지 않습니다.");
		}
		return "login";
	}

	// 이메일 인증시 등급 올리
	@RequestMapping(value = "/confirm.do")
	public String confirm(@RequestParam String mb_ID, @RequestParam String authkey, Model model) {

		MemberVO memberVO = memberService.updateRole(mb_ID, authkey); // grade값을 1로 변경
		model.addAttribute("memberVO", memberVO);
		return "confirm";
	}

	// 아이디찾기 실패시
	@RequestMapping(value = "/userInfo/findUserId.do")
	public String findUserId(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보의 아이디는 존재하지 않습니다.");
		}
		return "/userInfo/findUserId";
	}

	// 아이디 찾기 를 GET으로 받았을때
	@RequestMapping(value = "/userInfo/findUserIdOk.do", method = RequestMethod.GET)
	public String findUserIdOk() {
		return "/userInfo/findUserId";
	}

	// 아이디 찾기 를 POST로 받았을때
	@RequestMapping(value = "/userInfo/findUserIdOk.do", method = RequestMethod.POST)
	public String findUserIdOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.idSearch(memberVO);
		if (vo == null) {
			// 일치하는 정보가 없다.
			return "redirect:/userInfo/findUserId.do?error";
		} else {
			// 일치하는 정보가 있다.
			model.addAttribute("memberVO", vo);
			return "/userInfo/findUserIdOk";
		}
	}

	// 비밀번호 찾기 실패시
	@RequestMapping(value = "/userInfo/findPassword.do")
	public String findPassword(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보는 존재하지 않습니다.");
		}
		return "/userInfo/findPassword";
	}

	// 비밀번호 찾기 GET으로 받았을때
	@RequestMapping(value = "/userInfo/findPasswordOk.do", method = RequestMethod.GET)
	public String findPasswordOk() {
		return "/userInfo/findPasswordOk";
	}

	@RequestMapping(value = "/userInfo/findPasswordOk.do", method = RequestMethod.POST)
	public String findPasswordOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.passwordsearch(memberVO);
		if (vo == null) {
			// 일치하는 정보가 없다.
			return "redirect:/userInfo/findPassword.do?error";
		} else {
			// 일치하는 정보가 있다.
			// 임시비밀번호를 만들어서 DB에 저장
			String newPassword = memberService.makePassword(15);
			memberVO.setMb_password(newPassword);
			memberService.updatePassword(memberVO);
			memberService.sendPassword(memberVO);
			// 만들어진 임시 비밀번호 메일로 보낸다.
			return "/userInfo/findPasswordOk";
		}
	}

	@RequestMapping(value = "/memberInfo/memberDelete.do", method = RequestMethod.GET)
	public String memberdelete() {
		return "/memberInfo/memberDelete";
	}

	@RequestMapping(value = "/admin/memberManage.do", method = RequestMethod.GET)
	public String membermanage() {
		return "/admin/memberManage";
	}

	// 회원 탈퇴 폼
	@RequestMapping(value = "/memberInfo/memberDelete.do")
	public String memberdelete(@RequestParam(value = "error", required = false) String error,Model model) {
		if(error!=null) {
			model.addAttribute("error", "입력하신 정보가 일치하지 않습니다.");
		}
		return "/memberInfo/memberDelete";
	}
	
	
	@RequestMapping(value = "/memberDeleteOk.do")
	public String userDeleteOk(@ModelAttribute MemberVO memberVO, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=euc-kr");
		PrintWriter out = response.getWriter();
		MemberVO vo = memberService.selectByUserId(memberVO);
		if (vo == null) {
			out.println("<script>alert('비밀번호를 다시 입력해주세요.'); </script>");
			out.flush();
			return "/memberInfo/memberDelete";
		} else {
			memberService.userDelete(memberVO);
			session.removeAttribute("mvo");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
			out.println("<script>alert('그동안 NG캠핑을 이용해주셔서 감사합니다.'); </script>");
			out.println("<script>location.href='/main.do';</script>");
			out.flush();
			return "/";
		}
	}

	@RequestMapping(value = "/memberInfo/passwordCorrectOk.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String pwdcorrect(@ModelAttribute MemberVO memberVO, @RequestParam String newPassword, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		log.info(newPassword + "######################################");
		memberVO.setMb_password(newPassword);
		result += memberService.updatePassword(memberVO);
		session.removeAttribute("mvo");
		System.out.println(result);
		return result + "";
	}

	@RequestMapping(value = "/memberInfo/passwordCorrect.do", method = RequestMethod.GET)
	public String passwordcorrect() {
		return "/memberInfo/passwordCorrect";
	}

	@RequestMapping(value = "/memberInfo/memberPage.do", method = RequestMethod.POST)
	public String memberPage() {
		return "/memberInfo/memberPage";
	}

	@RequestMapping(value = "/memberInfo/memberpageOk.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String memberPageOk(@ModelAttribute MemberVO memberVO, @RequestParam String mb_nick, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		memberVO.setMb_nick(mb_nick);
		result += memberService.updateNick(memberVO);
		session.removeAttribute("mvo");
		System.out.println(result);
		return result + "";
	}
}
