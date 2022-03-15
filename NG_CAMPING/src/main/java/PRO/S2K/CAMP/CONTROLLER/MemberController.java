package PRO.S2K.CAMP.CONTROLLER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	MemberService memberService;
	
	
	@RequestMapping(value = "/insert.do", method = RequestMethod.GET)
	public String insert() {
		return "insert";
	}

	@RequestMapping(value = "/insertOk.do", method = RequestMethod.POST)
	public String insertOk(MemberVO memberVO) {
		memberService.insert(memberVO);
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
	
}
 