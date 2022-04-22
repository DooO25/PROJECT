package pro.s2k.camp.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.MemberDAO;
import pro.s2k.camp.service.MemberService;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.MemberVO;
import pro.s2k.camp.vo.PagingVO;

@Slf4j
@Controller
public class MemberController {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// 네이버
	// ----------------------------------------------------------------------------------
	@RequestMapping(value = "/naverCallback.do", produces = "application/json; charset=UTF-8")
	public String naverCallback(HttpSession session, HttpServletRequest request, Model model,
			HttpServletResponse httpServletResponse) throws IOException {
		String clientId = "eT2NCIHgedo2uVebssZm"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "dBx60NTCAZ"; // 애플리케이션 클라이언트 시크릿값"
		String code = request.getParameter("code"); // 뷰에서 정보수집 확인 받고 콜백함수로 넘어온 code
		String state = request.getParameter("state"); // 뷰에서 정보수집 확인 받고 콜백함수로 넘어온 state
		String redirectURI = URLEncoder.encode("http://localhost:8080/naverCallback.do", "UTF-8"); // 콜백주소
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		String access_token = ""; // 회원 정보를 받아오기 위해 필요한 토큰
//		String refresh_token = "";
		String token = "";
		try {
			URL url = new URL(apiURL); // 요청 URL 형식에 맞춰 가공
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); // 리퀘스트 메서드를 세팅하기위해 HttpURLConnection
			con.setRequestMethod("GET"); // GET방식
			int responseCode = con.getResponseCode(); // 상태코드 반환
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출 이면
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) { // 한줄씩 읽는동안 null이 아니면
				res.append(inputLine); // res에 inputLine 추가
			}
			br.close();
			if (responseCode == 200) { // 정상 호출이면
				token = res.toString(); // 가져온 정보 넣어주기
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		JSONParser jsonParser = new JSONParser();
		Object afterToken = null;
		try {
			afterToken = jsonParser.parse(token); // 토큰 제이슨로 파싱
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jsonToken = (JSONObject) afterToken; // 제이슨 객체로 형변환
		access_token = (String) jsonToken.get("access_token"); // 그중 access_token 값만 가져오기
//		refresh_token = (String) jsonToken.get("refresh_token");
		String responseBody = memberService.naverMemberProfile(access_token); // 서비스에 access_token 인수
		Object obj = null;
		try {
			obj = jsonParser.parse(responseBody); // 회원 프로필 정보 담기
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 정보들을 변수에 저장해줌
		JSONObject jsonobj = (JSONObject) obj;
		JSONObject response = (JSONObject) jsonobj.get("response");
		String socialID = (String) response.get("id");
		String socialName = (String) response.get("name");
		String socialEmail = (String) response.get("email");
		String socialTel = (String) response.get("mobile");
		socialTel = socialTel.replace("-", ""); // 네이버 정보에서 전화번호를 "-" 까지 포함하기에 공백으로 변경
		String socialBirth = (String) response.get("birthyear") + "-" + (String) response.get("birthday");
		int socialNumber = 1;
		// 멤버vo에 정보들 담아주고
		MemberVO memberVO = new MemberVO();
		memberVO.setSocialID(socialID);
		memberVO.setMb_name(socialName);
		memberVO.setMb_email(socialEmail);
		memberVO.setMb_tel(socialTel);
		memberVO.setMb_birth(socialBirth);
		memberVO.setSocialNumber(socialNumber);
		// 기존에 소셜로그인으로 회원가입 이력이있는지 판단
		MemberVO naverIdChk = memberService.socialIdChk(socialID);
		// PrintWriter 써주기 위한 인코딩
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("text/html; charset=UTF-8");
		PrintWriter out = httpServletResponse.getWriter();
		// 회원가입 이력이없으면
		if (naverIdChk == null) {
			// 소셜 회원가입으로 보내고,
			model.addAttribute("memberVO", memberVO);
			return "socialInsert";
			// 회원가입시 소셜 로그인 1,2,3 기준으로 같은 소셜인지 판단
		} else if (memberService.socialIdChk(socialID).getSocialNumber() != 1) {
			out.println("<script language='javascript'>");
			out.println("alert('네이버 아이디가 아닙니다.');");
			out.println("location.href='/login.do';");
			out.println("</script>");
			out.close();
		} else {
			// 인증정보에 아이디와 비밀번호를 불러와
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					memberService.socialIdChk(socialID).getMb_ID(),
					memberService.socialIdChk(socialID).getMb_password());
			// 넣어주고
			Object principal = authentication.getPrincipal();
			String userid = "";
			// 스프링 시큐리티로 넘어간 인증정보에서 사용자 아이디를 받아와
			if (principal instanceof UserDetails) {
				userid = ((UserDetails) principal).getUsername();
			} else {
				userid = principal.toString();
			}
			MemberVO mvo = memberDAO.selectUserId(userid);
			// 삭제된 회원이 아니면
			if (mvo.getDel() == 0) {
				// 얻어온 회원 정보를 세션에 저장하고 홈으로 이동한다
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("mvo", mvo);
				redirectStrategy.sendRedirect(request, httpServletResponse, "/main.do");
				// 삭제된 회원이면 alert로 표기
			} else {
				out.println("<script language='javascript'>");
				out.println("alert('탈퇴한 회원입니다.');");
				out.println("location.href='/main.do';");
				out.println("</script>");
				out.close();
			}
		}
		return null;
	}
	// 네이버
	// ----------------------------------------------------------------------------------

	// 카카오
	// ----------------------------------------------------------------------------------
	@RequestMapping(value = "/kakaoCallback.do", produces = "application/json; charset=UTF-8")
	public String kakaoCallback(HttpSession session, HttpServletRequest request, Model model,
			HttpServletResponse httpServletResponse) throws IOException {
		String clientId = "ef83fa2c4e841e935b1971d525cb0e1b"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "CCHf7unzK3JLDx8648Hpbqux4L7N22gl"; // 애플리케이션 클라이언트 시크릿값"
		String code = request.getParameter("code"); // 뷰에서 정보수집 확인 받고 콜백함수로 넘어온 code
		String redirectURI = URLEncoder.encode("http://localhost:8080/kakaoCallback.do", "UTF-8"); // 콜백 주소
		String apiURL;
		apiURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		String access_token = ""; // 회원 정보를 받아오기 위해 필요한 토큰
//		String refresh_token = "";
		String token = "";
		try {
			URL url = new URL(apiURL); // 요청 URL 형식에 맞춰 가공
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); // 리퀘스트 메서드를 세팅하기위해 HttpURLConnection
			con.setRequestMethod("GET"); // GET방식
			int responseCode = con.getResponseCode(); // 상태코드 반환
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출 이면
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) { // 한줄씩 읽는동안 null이 아니면
				res.append(inputLine); // res에 inputLine 추가
			}
			br.close();
			if (responseCode == 200) { // 정상 호출이면
				token = res.toString(); // 가져온 정보 넣어주기
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		JSONParser jsonParser = new JSONParser();
		Object afterToken = null;
		try {
			afterToken = jsonParser.parse(token); // 토큰 제이슨로 파싱
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jsonToken = (JSONObject) afterToken; // 제이슨 객체로 형변환
		access_token = (String) jsonToken.get("access_token"); // 그중 access_token 값만 가져오기
//		refresh_token = (String) jsonToken.get("refresh_token");
		String responseBody = memberService.kakaoMemberProfile(access_token);
		Object obj = null;
		try {
			obj = jsonParser.parse(responseBody);// 회원 프로필 정보 담기
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 변수들에 정보를 저장해줌
		JSONObject response = (JSONObject) obj;
		JSONObject kakao_account = (JSONObject) response.get("kakao_account");
		JSONObject profile = (JSONObject) kakao_account.get("profile");
		String socialID = (String) (response.get("id") + "");
		String socialName = (String) profile.get("nickname");
		String socialEmail = (String) kakao_account.get("email");
		int socialNumber = 2;
		// 멤버vo에 정보들 담아주고
		MemberVO memberVO = new MemberVO();
		memberVO.setSocialID(socialID);
		memberVO.setMb_name(socialName);
		memberVO.setMb_email(socialEmail);
		memberVO.setSocialNumber(socialNumber);
		// 기존에 소셜로그인으로 회원가입 이력이있는지 판단
		MemberVO kakaoIdChk = memberService.socialIdChk(socialID);

		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("text/html; charset=UTF-8");
		PrintWriter out = httpServletResponse.getWriter();
		// 회원가입 이력이 없으면
		if (kakaoIdChk == null) {
			model.addAttribute("memberVO", memberVO);
			// 소셜 회원가입으로 넘기고
			return "socialInsert";
			// 소셜사이트가 다르다면
		} else if (memberService.socialIdChk(socialID).getSocialNumber() != 2) {
			out.println("<script language='javascript'>");
			out.println("alert('카카오 아이디가 아닙니다.');");
			out.println("location.href='/login.do';");
			out.println("</script>");
			out.close();
		} else {

			// 인증정보에 아이디와 비밀번호를 불러와
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					memberService.socialIdChk(socialID).getMb_ID(),
					memberService.socialIdChk(socialID).getMb_password());
			// 넣어주고
			Object principal = authentication.getPrincipal();
			String userid = "";
			// 스프링 시큐리티로 넘어간 인증정보에서 사용자 아이디를 받아와
			if (principal instanceof UserDetails) {
				userid = ((UserDetails) principal).getUsername();
			} else {
				userid = principal.toString();
			}
			MemberVO mvo = memberDAO.selectUserId(userid);
			// 삭제된 회원이 아니면
			if (mvo.getDel() == 0) {
				// 얻어온 회원 정보를 세션에 저장하고 홈으로 이동한다
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("mvo", mvo);
				redirectStrategy.sendRedirect(request, httpServletResponse, "/main.do");
				// 삭제된 회원이면 alert로 표기
			} else {
				out.println("<script language='javascript'>");
				out.println("alert('탈퇴한 회원입니다.');");
				out.println("location.href='/main.do';");
				out.println("</script>");
				out.close();
			}
		}
		return null;
	}

	// 카카오
	// ----------------------------------------------------------------------------------

	// 구글
	// ------------------------------------------------------------------------------------
	@RequestMapping(value = "/googleCallback.do", produces = "application/json; charset=UTF-8")
	public String googleCallback(HttpSession session, HttpServletRequest request, Model model,
			HttpServletResponse httpServletResponse) throws IOException {
		String clientId = "669969361769-cud6nrlaur9thknq75b3hp91tp0i5hcg";// 애플리케이션 클라이언트 아이디값"
		String clientSecret = "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";// 애플리케이션 클라이언트 시크릿값"
		String code = request.getParameter("code"); // 뷰에서 정보수집 확인 받고 콜백함수로 넘어온 code
		String redirectURI = "http://localhost:8080/googleCallback.do"; // 콜백 주소
		String apiURL;
		apiURL = "https://www.googleapis.com/oauth2/v4/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		String access_token = "";
//		String refresh_token = "";
		String token = "";
		try {
			URL url = new URL(apiURL); // 요청 URL 형식에 맞춰 가공
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			BufferedOutputStream bous = new BufferedOutputStream(con.getOutputStream()); // BufferedOutputStream byte단위로
																							// 파일을 기록할때 사용하는 퍼퍼스트림
			bous.write(apiURL.getBytes());
			bous.flush();
			bous.close();
			int responseCode = con.getResponseCode(); // 상태코드 반환
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출 이면
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) { // 한줄씩 읽는동안 null이 아니면
				res.append(inputLine); // res에 inputLine 추가
			}
			br.close();
			if (responseCode == 200) { // 정상 호출이면
				token = res.toString(); // 가져온 정보 넣어주기
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		JSONParser jsonParser = new JSONParser();
		Object afterToken = null;
		try {
			afterToken = jsonParser.parse(token); // 토큰 제이슨로 파싱
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jsonToken = (JSONObject) afterToken; // 제이슨 객체로 형변환
		access_token = (String) jsonToken.get("access_token"); // 그중 access_token 값만 가져오기
//		refresh_token = (String) jsonToken.get("refresh_token");
		String responseBody = memberService.googleMemberProfile(access_token);
		Object obj = null;
		try {
			obj = jsonParser.parse(responseBody);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 변수에 회원 정보 담기
		JSONObject response = (JSONObject) obj;
		String socialID = (String) (response.get("id") + "");
		String socialName = (String) (response.get("name"));
		String socialEmail = (String) (response.get("email"));
		int socialNumber = 3;

		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("text/html; charset=UTF-8");
		PrintWriter out = httpServletResponse.getWriter();
		// 멤버vo에 정보 담기
		MemberVO memberVO = new MemberVO();
		memberVO.setSocialID(socialID);
		// 이름을 미공개한 클라이언트도 있긴 때문에 null point 방지하기위해 if
		if (socialName != null) {
			memberVO.setMb_name(socialName);
		}
		memberVO.setMb_email(socialEmail);
		memberVO.setSocialNumber(socialNumber);

		MemberVO googleIdChk = memberService.socialIdChk(socialID);
		// 회원가입 이력이 없으면
		if (googleIdChk == null) {
			model.addAttribute("memberVO", memberVO);
			// 소셜 회원가입으로 넘기고
			return "socialInsert";
			// 소셜사이트와 번호가 같지 않다면 표시
		} else if (memberService.socialIdChk(socialID).getSocialNumber() != 3) {
			out.println("<script language='javascript'>");
			out.println("alert('구글 아이디가 아닙니다.');");
			out.println("location.href='/login.do';");
			out.println("</script>");
			out.close();
		} else {
			// 인증정보에 아이디와 비밀번호를 불러와
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					memberService.socialIdChk(socialID).getMb_ID(),
					memberService.socialIdChk(socialID).getMb_password());
			// 넣어주고
			Object principal = authentication.getPrincipal();
			String userid = "";
			// 스프링 시큐리티로 넘어간 인증정보에서 사용자 아이디를 받아와
			if (principal instanceof UserDetails) {
				userid = ((UserDetails) principal).getUsername();
			} else {
				userid = principal.toString();
			}
			MemberVO mvo = memberDAO.selectUserId(userid);
			// 삭제된 회원이 아니면
			if (mvo.getDel() == 0) {
				// 얻어온 회원 정보를 세션에 저장하고 홈으로 이동한다
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("mvo", mvo);
				redirectStrategy.sendRedirect(request, httpServletResponse, "/main.do");
				// 삭제된 회원이면 alert로 표기
			} else {
				out.println("<script language='javascript'>");
				out.println("alert('탈퇴한 회원입니다.');");
				out.println("location.href='/main.do';");
				out.println("</script>");
				out.close();
			}
		}
		return null;
	}
	// 구글
	// ------------------------------------------------------------------------------------

	// 회원가입 페이지 이동 (get방식)
	@RequestMapping(value = "/insert.do", method = RequestMethod.GET)
	public String insert() {
		return "insert";
	}

	// 회원 가입 완료 페이지 이동(Get방식) = 403페이지(오류)로 이동
	@RequestMapping(value = "/insertOk.do", method = RequestMethod.GET)
	public String insertOk() {
		return "403";
	}

	// 회원 가입 완료 페이지 이동(post방식) = 회원가입 완료 페이지로 이동
	@RequestMapping(value = "/insertOk.do", method = RequestMethod.POST)
	public String insertOk(MemberVO memberVO, Model model) {
		memberVO.setAuthkey(UUID.randomUUID().toString()); // 인증키를 랜덤으로 지정해준다.
		memberVO.setMb_password(bCryptPasswordEncoder.encode(memberVO.getMb_password())); // 입력했던 비밀번호를 암호화 시킨다.
		memberService.insert(memberVO);
		model.addAttribute("memberVO", memberVO);
		return "insertOk";
	}

	// 회원가입시 아이디 중복여부 체크
	@RequestMapping(value = "/idCheck.do", produces = "text/plain;charset=UTF-8") // UTF-8로 글자 하나 넣겠다.
	@ResponseBody
	public String idCheck(@RequestParam(required = false) String userid) {
		String userids[] = "admin,root,master,webmaster,administrator, ".split(","); // 금지 아이디 목록을 지정
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

	// 회원가입시 닉네임 중복여부 체크
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

	// 이메일 인증시 아이디 사용 가능 여부 판단
	@RequestMapping(value = "/confirm.do")
	public String confirm(@RequestParam String mb_ID, @RequestParam String authkey, Model model) {
		MemberVO memberVO = memberService.updateRole(mb_ID, authkey); // mb_use(아이디 인증여부)를 1로 변경
		model.addAttribute("memberVO", memberVO);
		return "confirm";
	}

	// 아이디찾기 페이지에서 아이디 찾기를 실패할 경우
	@RequestMapping(value = "/userInfo/findUserId.do")
	public String findUserId(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보의 아이디는 존재하지 않습니다.");
		}
		return "/userInfo/findUserId";
	}

	// 아이디를 찾기 위한 정보를 get으로 받을 경우 다이 아이디 찾기 페이지로 이동
	@RequestMapping(value = "/userInfo/findUserIdOk.do", method = RequestMethod.GET)
	public String findUserIdOk() {
		return "/userInfo/findUserId";
	}

	// 아이디를 찾기 위한 정보를 post로 받을 경우
	@RequestMapping(value = "/userInfo/findUserIdOk.do", method = RequestMethod.POST)
	public String findUserIdOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.idSearch(memberVO);
		if (vo == null) {
			// 찾아야 하는 정보가 없는 경우
			return "redirect:/userInfo/findUserId.do?error";
		} else {
			// 찾아야하는 정보가 있는 경우
			model.addAttribute("memberVO", vo);
			return "/userInfo/findUserIdOk";
		}
	}

	// 비밀번호 찾기 페이지에서 비밀번호 찾기를 실패한 경우
	@RequestMapping(value = "/userInfo/findPassword.do")
	public String findPassword(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보는 존재하지 않습니다.");
		}
		return "/userInfo/findPassword";
	}

	// 비밀번호 찾기 위한 정보를 get으로 받을 경우 다시 비밀번호 찾기 페이지로 이동
	@RequestMapping(value = "/userInfo/findPasswordOk.do", method = RequestMethod.GET)
	public String findPasswordOk() {
		return "/userInfo/findPasswordOk";
	}

	// 비밀번호 찾기 위한 정보를 post로 받을 경우
	@RequestMapping(value = "/userInfo/findPasswordOk.do", method = RequestMethod.POST)
	public String findPasswordOkPOST(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.passwordsearch(memberVO);
		if (vo == null) {
			// 찾아야 하는 정보가 없는 경우
			return "redirect:/userInfo/findPassword.do?error";
		} else {
			// 찾아야하는 정보가 있는 경우
			// 임시비밀번호를 만들어서 DB에 저장
			String newPassword = memberService.makePassword(15);
			memberVO.setMb_password(newPassword);
			memberService.updatePassword(memberVO);
			memberService.sendPassword(memberVO);
			// 만들어진 임시 비밀번호 메일로 보낸다.
			return "/userInfo/findPasswordOk";
		}
	}

	// 비밀번호 초기화를 위한 정보를 Post로 받을 경우
	@RequestMapping(value = "/resetPasswordOk.do", method = RequestMethod.POST)
	public String resetPasswordOk(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.passwordsearch(memberVO);
		if (vo == null) {
			// 찾아야 하는 정보가 없는 경우
			return "redirect:/admin/memberManage.do?error";
		} else {
			// 찾아야하는 정보가 있는 경우
			// 임시비밀번호를 만들어서 DB에 저장
			String newPassword = memberService.makePassword(15);
			memberVO.setMb_password(newPassword);
			memberService.updatePassword(memberVO);
			memberService.sendPassword(memberVO);
			// 만들어진 임시 비밀번호 메일로 보낸다.
			return "/admin/resetPasswordOk";
		}
	}

	// 회원탈퇴 페이지로 이동
	@RequestMapping(value = "/memberInfo/memberDelete.do", method = RequestMethod.GET)
	public String memberdelete() {
		return "/memberInfo/memberDelete";
	}

	// 회원 탈퇴 정보가 에러인 경우
	@RequestMapping(value = "/memberInfo/memberDelete.do")
	public String memberdelete(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "입력하신 정보가 일치하지 않습니다.");
		}
		return "/memberInfo/memberDelete";
	}

	// 회원 탈퇴 페이지에서 탈퇴가 완료되는 경우
	@RequestMapping(value = "/memberDeleteOk.do")
	public String userDeleteOk(@ModelAttribute MemberVO memberVO, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=euc-kr"); // 입력한 내용이 한글로 인코딩이 되게끔 설정
		PrintWriter out = response.getWriter(); // 문자열을 출력하기 위한 설정
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

	// 비밀번호 수정 완료
	@RequestMapping(value = "/memberInfo/passwordCorrectOk.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String pwdcorrect(@ModelAttribute MemberVO memberVO, @RequestParam String newPassword, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		memberVO.setMb_password(newPassword);
		result += memberService.updatePassword(memberVO);
		session.removeAttribute("mvo");
		System.out.println(result);
		return result + "";
	}

	// 비밀번호 수정 페이지로 이동
	@RequestMapping(value = "/memberInfo/passwordCorrect.do", method = RequestMethod.GET)
	public String passwordcorrect() {
		return "/memberInfo/passwordCorrect";
	}

	// 마이페이지로 이동
	@RequestMapping(value = "/memberInfo/memberPage.do", method = RequestMethod.POST)
	public String memberPage() {
		return "/memberInfo/memberPage";
	}

	// 마이페이지 닉네임 수정 완료시
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

	// 관리자-> 회원관리 페이지 get방식으로 이동시 403오류 발생
	@RequestMapping(value = "/admin/memberManage.do", method = RequestMethod.GET)
	public String memberManage() {
		return "403";
	}

	// 관리자페이지->회원관리 페이지로 이동
	@RequestMapping(value = "/admin/memberManage.do", method = RequestMethod.POST)
	public String memberManage(Model model, MemberVO memberVO) {
		PagingVO<MemberVO> pv = memberService.selectList();
		model.addAttribute("pv", pv);
		return "/admin/memberManage";
	}

	// 회원관리페이지에 가입된 회원정보를 표시 기능
	@RequestMapping(value = "/admin/memberManageInfo.do")
	public String memberManageInfo(@RequestParam int mb_idx, Model model, MemberVO memberVO) {
		PagingVO<MemberVO> pv = memberService.selectList();
		model.addAttribute("pv", pv);
		memberVO = memberService.selectByIdx(mb_idx);
		model.addAttribute("mv", memberVO);
		return "/admin/memberManage";
	}

	// 회원관리 페이지에 있는 검색기능을 통한 기능
	@RequestMapping(value = "/selectSearchMember.do")
	public String selectSearchNotice(HttpServletRequest request, @ModelAttribute CommonVO commVO, Model model) {
		PagingVO<MemberVO> pv = memberService.selectSearchMember(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
		return "/admin/memberManage";

	}

}
