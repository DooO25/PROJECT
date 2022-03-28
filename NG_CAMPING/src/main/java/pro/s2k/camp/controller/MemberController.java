package pro.s2k.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.interfaces.RSAKey;
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

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/memberdelete.do", method = RequestMethod.GET)
	public String memberdelete() {
		return "memberdelete";
	}

	@RequestMapping(value = "/Admin/membermanage.do", method = RequestMethod.GET)
	public String membermanage() {
		return "/Admin/membermanage";
	}

	// 회원 탈퇴 폼
	@RequestMapping(value = "/memberdelete.do")
	public String memberdelete(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보가 일치하지 않습니다.");
		}
		return "memberdelete";
	}

	@RequestMapping(value = "/memberdeleteOK.do", method = RequestMethod.GET)
	public String memberdeleteOK() {
		return "memberdelete";
	}

	@RequestMapping(value = "/memberdeleteOk.do", method = RequestMethod.POST)
	public String deleteOkPOST(@ModelAttribute MemberVO memberVO, HttpSession session, String mb_ID,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberVO vo = memberService.selectByUserId(memberVO);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(mb_ID, memberVO.getMb_password())) {
			session.setAttribute("mb_password", memberVO.getMb_password());
			out.println("<script>alert('회원탈퇴 완료.'); location.href=\"redirect:/\";</script>");
			out.flush();
			session.removeAttribute("mvo");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			// 인증정보가 있다면
			if (authentication != null) {
				// 로그아웃을 시킨다.
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
		} else if (!encoder.matches(mb_ID, memberVO.getMb_password())) {
			out.println("<script>alert('오류.');");
		}
		return "redirect:memberdelete?error";
	}

	
	
	@RequestMapping(value = "/passwordcorrectOk.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
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

	@RequestMapping(value = "/passwordcorrect.do", method = RequestMethod.GET)
	public String passwordcorrect() {
		return "passwordcorrect";
	}

	@RequestMapping(value = "/memberpage.do", method = RequestMethod.POST)
	public String memberpage() {
		return "memberpage";
	}

	@RequestMapping(value = "/memberpageOk.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String memberpageOk(@ModelAttribute MemberVO memberVO, @RequestParam String mb_nick, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		log.info(mb_nick + "######################################");
		memberVO.setMb_nick(mb_nick);
		result += memberService.updatenick(memberVO);
		session.removeAttribute("mvo");
		System.out.println(result);
		return result + "";
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
		return "insertOk";
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
	@RequestMapping(value = "/findUserId.do")
	public String findUserId(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보의 아이디는 존재하지 않습니다.");
		}
		return "findUserId";
	}

	// 아이디 찾기 를 GET으로 받았을때
	@RequestMapping(value = "/findUserIdOk.do", method = RequestMethod.GET)
	public String findUserIdOk() {
		return "findUserId";
	}

	// 아이디 찾기 를 POST로 받았을때
	@RequestMapping(value = "/findUserIdOk.do", method = RequestMethod.POST)
	public String findUserIdOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.idSearch(memberVO);
		if (vo == null) {
			// 일치하는 정보가 없다.
			return "redirect:findUserId.do?error";
		} else {
			// 일치하는 정보가 있다.
			model.addAttribute("memberVO", vo);
			return "findUserIdOk";
		}
	}

	// 비밀번호 찾기 실패시
	@RequestMapping(value = "/findPassword.do")
	public String findPassword(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보는 존재하지 않습니다.");
		}
		return "findPassword";
	}

	// 비밀번호 찾기 GET으로 받았을때
	@RequestMapping(value = "/findPasswordOk.do", method = RequestMethod.GET)
	public String findPasswordOk() {
		return "findPasswordOk";
	}

	@RequestMapping(value = "/findPasswordOk.do", method = RequestMethod.POST)
	public String findPasswordOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.passwordsearch(memberVO);
		if (vo == null) {
			// 일치하는 정보가 없다.
			return "redirect:findPassword.do?error";
		} else {
			// 일치하는 정보가 있다.
			// 임시비밀번호를 만들어서 DB에 저장
			String newPassword = memberService.makePassword(15);
			memberVO.setMb_password(newPassword);
			memberService.updatePassword(memberVO);
			memberService.sendPassword(memberVO);
			// 만들어진 임시 비밀번호 메일로 보낸다.
			return "findPasswordOk";
		}
	}
}
