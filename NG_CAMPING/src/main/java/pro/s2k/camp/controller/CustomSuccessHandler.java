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

// 로그인 성공시 추가적으로 처리할 내용을 여기에 적는다.
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	NoticeDAO noticeDAO;

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
// 로그인 성공시 처리할 내용을 여기에 적는다.
// 회원 정보를 세션에 저장
		String userid = "";
// 인증정보에서 사용자 아이디를 받아온다.
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			userid = ((UserDetails) principal).getUsername();
		} else {
			userid = principal.toString();
		}
		// DB에서 회원 정보를 얻어온다.
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberVO memberVO = memberDAO.selectUserId(userid);
		if (memberVO.getDel() == 0) {
			// System.out.println(authentication.getPrincipal());
			// System.out.println(userid);
			// System.out.println(memberVO);
			// 얻어온 회원 정보를 세션에 저장하고 홈으로 이동한다.
			HttpSession session = request.getSession();
			session.setAttribute("mvo", memberVO);
			redirectStrategy.sendRedirect(request, response, "/main.do");
		} else {
			out.println("<script language='javascript'>");
			out.println("alert('탈퇴한 회원입니다.');");
			out.println("location.href='/main.do';");
			out.println("</script>");
			out.close();
		}
	}
}
