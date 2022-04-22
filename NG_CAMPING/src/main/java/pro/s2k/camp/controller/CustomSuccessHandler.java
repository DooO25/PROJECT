package pro.s2k.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import pro.s2k.camp.dao.MemberDAO;
import pro.s2k.camp.dao.NoticeDAO;
import pro.s2k.camp.vo.MemberVO;

public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	NoticeDAO noticeDAO;

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		// 로그인 성공시 처리할 로직
		String userid = "";
		// 스프링 시큐리티로 넘어간 인증정보에서 사용자 아이디를 받아온다
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			userid = ((UserDetails) principal).getUsername();
		} else {
			userid = principal.toString();
		}
		// PrintWriter사용시 인코딩 하기위함
		response.setCharacterEncoding("UTF-8"); 
		// 문자코드를 해석하는것이 항상 다르기에 컨텐츠타입까지 적용시켜야함
		response.setContentType("text/html; charset=UTF-8");
		// 컨트롤러에서 자바스크립트를 사용하기위해 PrintWriter 사용
		PrintWriter out = response.getWriter();
		// MemberVO에 아이디를 이용해 정보를 저장
		MemberVO memberVO = memberDAO.selectUserId(userid);
		// 회원정보가 정상이면
		if (memberVO.getDel() == 0) {
			// 얻어온 회원 정보를 세션에 저장하고
			HttpSession session = request.getSession();
			session.setAttribute("mvo", memberVO);
			// 홈으로 보낸다
			redirectStrategy.sendRedirect(request, response, "/main.do");
		} else {
			// 회원정보가 탈퇴된 회원이면
			out.println("<script language='javascript'>");
			out.println("alert('탈퇴한 회원입니다.');");
			out.println("location.href='/main.do';");
			out.println("</script>");
			out.close();
		}
	}
}
