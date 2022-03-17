package PRO.S2K.CAMP.CONTROLLER;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import PRO.S2K.CAMP.SERVICE.MemberService;
import PRO.S2K.CAMP.VO.MemberVO;

@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	
	
	@RequestMapping(value = "/insert.do", method = RequestMethod.GET)
	public String insert() {
		return "insert";
	}

	@RequestMapping(value = "/insertOk.do", method = RequestMethod.GET)
	public String insertOk() {
		return "redirect:/";
	}
	
	@RequestMapping(value = "/insertOk.do", method = RequestMethod.POST)
	public String insertOk(MemberVO memberVO, Model model) {
		memberVO.setAuthkey(UUID.randomUUID().toString());
		memberService.insert(memberVO);
		model.addAttribute("memberVO",memberVO);
		return "insertOk";
	}
	
	@RequestMapping(value = "/idCheck.do", produces = "text/plain;charset=UTF-8" ) // UTF-8로 글자 하나 넣겠다.
	@ResponseBody
	public String idCheck(@RequestParam(required = false) String userid) {
	   String userids[] = "admin,root,master,webmaster,administrator, ".split(","); // 금지 아이디 목록
	      int count = 0;
	      for(String id : userids) { 
	         if(userid.contains(id)) {
	            count=1;
	            break;
	         }
	      }
	      if(count==0) {
	         // 서비스를 호출하여 동일한 아이디의 개수를 얻어소 count변수에 넣는다.
	         count = memberService.idCheck(userid);
	      }
	      return count + "";
   }
	
	@RequestMapping(value = "/nickCheck.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String nickCheck(@RequestParam(required = false) String nick) {
		String nicks[] = "admin,root,master,webmaster,administrator, ".split(","); // 금지 아이디 목록
		int count = 0;
		for(String ni : nicks) { 
			if(nick.contains(ni)) {
				count=1;
				break;
			}
		}
		if(count==0) {
			// 서비스를 호출하여 동일한 닉네임 갯수를 얻어서 count 변수에 넣는다.
			logger.info("=============================1");
			count = memberService.nickCheck(nick);
			logger.info("=============================2");
		}
		return count + "";
	}
	
	
	@RequestMapping(value = "/login.do")
	public String login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		Model model
		) {
		if(error!=null) {
			model.addAttribute("error", "아이디가 없거나 비번이 일치하지 않습니다.");
		}
		if(logout!=null) {
			model.addAttribute("msg", "성공적으로 로그아웃을 했습니다.");
		}
		return "login";
	}
	
	@RequestMapping(value = "/confirm.do")
	   public String confirm(@RequestParam String mb_ID, @RequestParam String authkey, Model model) {

	      MemberVO memberVO = memberService.updateGrade(mb_ID, authkey); // grade값을 1로 변경
	      model.addAttribute("memberVO", memberVO);
	      return "confirm";
	   }
 
	
	@RequestMapping(value = "/callback.do", method = RequestMethod.GET)
	public String callback() {
		return "callback";
	}
	
	 @RequestMapping(value = "/findUserId.do")
	public String findUserId(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model
			) {
			 if(error!=null) {
					model.addAttribute("error", "입력하신 정보의 아이디는 존재하지 않습니다.");
			}
		return "findUserId"; 
	}
	
	@RequestMapping(value = "/findUserIdOk.do",method = RequestMethod.GET)
	public String findUserIdOk() {
		return "findUserId";
	}
	@RequestMapping(value = "/findUserIdOk.do",method = RequestMethod.POST)
	public String findUserIdOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.idSearch(memberVO);
		if(vo==null) {
			// 일치하는 정보가 없다.
			return "redirect:findUserId.do?error";
		}else {
			// 일치하는 정보가 있다.
			model.addAttribute("memberVO", vo);  
			return "findUserIdOk";
		}
	}
	
}
 