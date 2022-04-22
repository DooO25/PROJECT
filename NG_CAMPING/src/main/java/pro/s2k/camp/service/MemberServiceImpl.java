package pro.s2k.camp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pro.s2k.camp.dao.MemberDAO;
import pro.s2k.camp.dao.RoleDAO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.MemberVO;
import pro.s2k.camp.vo.PagingVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// 1. 회원가입
	@Override
	public void insert(MemberVO memberVO) { // 회원정보 입력
		// DB에 저장한다.
		if (memberVO != null) {
			memberDAO.insert(memberVO); // 회원정보 입력
			roleDAO.insert(memberVO.getMb_ID()); // memberVO에 Mb_ID값을 가져와 입력한다.
			sendEmail(memberVO); // 메일로 정보를 보낸다.
		}
	}

	// 2 회원 탈퇴
	@Override
	public void userDelete(MemberVO memberVO) {
		if (memberVO != null) { // memberVO가 null이 아닌경우
			MemberVO vo = memberDAO.selectUserId(memberVO.getMb_ID()); // memberVO에 Mb_ID값을 가져온다.
			if (vo != null) { // 가져온 값이 null값이 아니면
				String dbPassword = vo.getMb_password(); // 입력한 비밀번호를 가져온다.
				if (bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) { // 입력된 비밀번호와 저장된 비밀번호를
																							// 암호화하였을때 같으면
					memberDAO.userDelete(vo.getMb_idx()); // userdelete를 실행
				}
			}
		}
	}

	// 3. 비번 변경
	@Override
	public int updatePassword(MemberVO memberVO) {
		int result = 0; // result를 0으로 설정
		HashMap<String, String> map = new HashMap<String, String>(); // HashMap<String, String>을 map에 담는다.
		String encryptPassword = bCryptPasswordEncoder.encode(memberVO.getMb_password()); // 새로입력한 비밀번호를 암호화하여
																							// encryptPassword에 담는다.
		map.put("mb_ID", memberVO.getMb_ID()); // mb_ID에 memberVO에 있는 아이디를 가져온다.
		map.put("mb_password", encryptPassword); // encryptPassword 가져와서 넣는다.
		result += memberDAO.updatePassword(map); // 정보를 담은 map을 updatePassword로 통해 실행 시키며 result에 1(+=)을 추가한다.

		return result;
	}

	// 4. 인증 완료
	@Override
	public MemberVO updateRole(String mb_ID, String authkey) {
		HashMap<String, String> map = new HashMap<String, String>(); // HashMap<String, String>을 map에 담는다.
		map.put("gr_role", "ROLE_USER"); // gr_role 값을 ROLE_USER로 바꿔서 추가한다.
		map.put("mb_ID", mb_ID); // mb_ID에 ID를 추가한다.
		map.put("authkey", authkey); // authkey에 인증키를 추가한다.
		memberDAO.updateRole(map); // 정보를 담은 map을 memberDAO.updateRole로 통해 실행
		return memberDAO.selectUserId(mb_ID); // 바뀐 정보의 mb_id를 memberDAO.selectUserId로 리턴
	}

	// 5. 아이디 중복확인
	@Override
	public int idCheck(String mb_ID) {
		return memberDAO.selectCountByUserId(mb_ID); // 아이디 중복체크를 확인하기 위해 memberDAO.selectCountByUserId(mb_ID)를 리턴한다.
	}

	// 6. 닉네임 중복확인
	@Override
	public int nickCheck(String mb_nick) {
		return memberDAO.selectCountByUserNick(mb_nick); // 아이디 중복체크를 확인하기 위해 memberDAO.selectCountByUserId(mb_ID)를
															// 가져온다.
	}

	// 7. 아이디 찾기 : 이름과 전화번호를 넘겨 DB에서 가져온다.
	@Override
	public MemberVO idSearch(MemberVO memberVO) {
		MemberVO vo = null; // vo의 값을 null로 만든다.
		if (memberVO != null) { // memberVO의 값이 null이 아닌경우
			HashMap<String, String> map = new HashMap<String, String>(); // HashMap<String, String>을 map에 담는다.
			map.put("mb_name", memberVO.getMb_name()); // mb_name에 memberVO에 있는 이름을 가져와서 map에 넣는다.
			map.put("mb_tel", memberVO.getMb_tel()); // mb_tel에 memberVO에 있는 전화번호를 가져와서 map에 넣는다.
			vo = memberDAO.selectByUsername(map); // 정보를 담은 map을 가지고 memberDAO.selectByUsername를 실행시켜 그값을 vo에 담는다.
		}
		return vo; // vo를 리턴
	}

	// 8. 비밀번호 찾기 :아이디와 이메일을 넘겨 DB에서 가져온다.
	@Override
	public MemberVO passwordsearch(MemberVO memberVO) {
		MemberVO vo = null; // vo의 값을 null로 만든다.
		if (memberVO != null) { // memberVO의 값이 null이 아닌경우
			HashMap<String, String> map = new HashMap<String, String>(); // HashMap<String, String>을 map에 담는다.
			map.put("mb_ID", memberVO.getMb_ID()); // mb_ID에 memberVO에 있는 아이디를 가져와서 map에 넣는다.
			map.put("mb_email", memberVO.getMb_email()); // mb_email에 memberVO에 있는 이메일을 가져와서 map에 넣는다.
			vo = memberDAO.selectByUserId(map); // 정보를 담은 map을 가지고 selectByUserId을 실행시켜 그값을 vo에 담는다.
		}
		return vo; // vo를 리턴
	}

	// 9. 임시비번 만들기
	@Override
	public String makePassword(int length) {
		Random random = new Random(); // random이라는 변수 하나 생성
		String password = ""; // 빈값의 passwword 변수 생성
		String str = "~@!#$%^&*+-*"; // str이라는 변수에 ~@!#$%^&*+-*를 추가
		for (int i = 0; i < length; i++) {
			// case의 개수가 많을수록 나타날 확률이 높아진다.
			switch (random.nextInt(20)) { // 0(숫자), 1(영어소문자), 2(영어 대문자), 3(특수문자) //랜덤으로 줄 값을 20개의 확률로 지정
			case 0:
			case 18:
			case 19:
				password += (char) ('0' + random.nextInt(10)); // 3/20 확률로 숫자 0~9까지 임의로 부여해 password에 추가
				break;
			case 1:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				password += (char) ('a' + random.nextInt(26)); // 8/20 확률로 영어 소문자 a~z까지 임의로 부여해 password에 추가
				break;
			case 2:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
				password += (char) ('A' + random.nextInt(26)); // 8/20 확률로 영어 대문자 A~Z까지 임의로 부여해 password에 추가
				break;
			case 3:
				password += str.charAt(random.nextInt(str.length())); // 1/20 확률로 ~@!#$%^&*+-*를 임의로 부여해 password에 추가
				break;
			}
		}
		return password; // password를 리턴
	}

	// 10. id와 비번이 같은것 가져오기
	@Override
	public MemberVO selectByUserId(MemberVO memberVO) {
		MemberVO vo = null; // vo의 값을 null로 만든다.
		if (memberVO != null) { // memberVO가 null이 아닌경우
			vo = memberDAO.selectUserId(memberVO.getMb_ID()); // vo에 memberVO에 있는 아이디를 가져와서에 넣는다.
			if (vo != null) { // vo가 null값이 아닌 경우
				String dbPassword = vo.getMb_password(); // 현재 정보가 있는 vo에 비밀번호를 가져와 dbPassword에 넣는다.
				if (!bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) { // 입력된 비밀번호와 저장된 비밀번호를
																								// 암호화하였을때 같이 않으면
					return null; // null값을 리턴
				}
			}
		}
		return vo; // vo를 리턴
	}

	// 11. 메일로 임시비밀번호 보내기
	@Override
	public void sendPassword(MemberVO memberVO) {
		String subject = "NG_CAMP 임시 비밀번호 입니다.";
		String to = memberVO.getMb_email(); // to라는 변수에 memberVO에 있는 email를 가져온다.
		String content = "반갑습니다. " + memberVO.getMb_ID() + "님!!!<br>" + "NG_CAMP에서 요청하신 비밀번호 찾기 메일로,<br> "
				+ "회원님의 임시 비밀번호는 " + memberVO.getMb_password() + " 입니다.";

		MimeMessagePreparator preparator = getMessagePreparator(to, subject, content); // 메일전송을 위해 preparator에
																						// getMessagePreparator(to,
																						// subject, content) 삽입
		try {
			mailSender.send(preparator); // 정보가 담긴 preparator를 가지고 메일을 전송
			System.out.println("메일 보내기 성공 ***************************************************");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	// 12. 닉네임 변경
	@Override
	public int updateNick(MemberVO memberVO) {
		HashMap<String, String> map = new HashMap<String, String>(); // HashMap<String, String>을 map에 담는다.
		map.put("mb_ID", memberVO.getMb_ID()); // mb_ID에 memberVO에 있는 아이디를 가져와서 map에 넣는다.
		map.put("mb_nick", memberVO.getMb_nick()); // mb_nick에 memberVO에 있는 닉네임을 가져와서 map에 넣는다.
		return memberDAO.updateNick(map); // 정보를 담은 map을 가지고 updateNick을 실행시키고 리턴한다.
	}

	// 13. 아이디로 리스트 찾기
	public PagingVO<MemberVO> selectList() {
		PagingVO<MemberVO> vo = null;
		try {
			int totalCount = memberDAO.selectCount(); // memberDAO.selectCount()의 값을 totalCount 변수에 넣는다.
			vo = new PagingVO<>(totalCount); // vo에 new PagingVO<>(totalCount)를 넣고
			List<MemberVO> list = memberDAO.selectList(); // memberDAO.selectList()를 list에 담는다.
			if (list != null && list.size() > 0) { // list가 null값이 아니거나 list의 길이가 0 이상인 경우
				vo.setList(list); // vo.setList(list)를 가져온다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo; // vo를 리턴
	}

	// 14. 회원정보 상세 얻기
	public MemberVO selectByIdx(int idx) {
		MemberVO memberVO = memberDAO.selectByIdx(idx); // memberVO에 selectByIdx(idx)를 넣는다.
		return memberVO; // memberVO를 리턴
	}

	// 15. 검색으로 정보 찾기
	@Override
	public PagingVO<MemberVO> selectSearchMember(CommonVO commVO) {
		PagingVO<MemberVO> pagingVO = null;
		try {
			// 전체갯수 구하기
			int totalCount = memberDAO.selectSearchCount(commVO);
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			map.put("searchText", commVO.getSearchText());
			map.put("searchType", commVO.getSearchType());
			List<MemberVO> list = memberDAO.selectSearchMember(map);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	// 회원 가입시 인증 메일 보내는 메서드
	public void sendEmail(MemberVO memberVO) {
		String subject = "회원 가입을 축하드립니다.";
		String to = memberVO.getMb_email();
		String content = "반갑습니다. " + memberVO.getMb_nick() + "님!!!<br>" + "회원 가입을 축하드립니다.<br> "
				+ "회원 가입을 완료하려면 다음의 링크를 클릭해서 인증하시기 바랍니다.<br>" + "<a href='http://localhost:8080/confirm.do?mb_ID="
				+ memberVO.getMb_ID() + "&authkey=" + memberVO.getAuthkey() + "'>인증</a><br>";

		MimeMessagePreparator preparator = getMessagePreparator(to, subject, content);
		try {
			mailSender.send(preparator);
			System.out.println("메일 보내기 성공 ***************************************************");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	// 메일 내용 완성
	private MimeMessagePreparator getMessagePreparator(String to, String subject, String content) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("ngcamppp@gmail.com");
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content, true);
			}
		};
		return preparator;
	}
	
	// 소셜 로그인
	// -------------------------------------------------------------------------------------------------------------------------------------------------------


	// 16. 소셜로그인(네이버) 회원정보 가져오기
	@Override
	public String naverMemberProfile(String access_token) {
		String token = access_token; // 네이버 로그인 접근 토큰
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		String apiURL = "https://openapi.naver.com/v1/nid/me";

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", header);
		String responseBody = get(apiURL, requestHeaders); // 요청 URL에 필요 정보 넣어서 리턴
		return responseBody;
	}

	// 16. 소셜로그인(카카오) 회원정보 가져오기
	@Override
	public String kakaoMemberProfile(String accessToken) {
		String token = accessToken; // 카카오 로그인 접근 토큰
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		String apiURL = "https://kapi.kakao.com/v2/user/me";

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", header);
		String responseBody = get(apiURL, requestHeaders); // 요청 URL에 필요 정보 넣어서 리턴
		return responseBody;
	}

	// 16. 소셜로그인(구글) 회원정보 가져오기
	@Override
	public String googleMemberProfile(String accessToken) {
		String token = accessToken; // 구글 로그인 접근 토큰
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		String apiURL = "https://www.googleapis.com/oauth2/v2/userinfo";

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", header);
		String responseBody = get(apiURL, requestHeaders); // 요청 URL에 필요 정보 넣어서 리턴
		return responseBody;
	}

	// 회원 프로필 메서드에서 필요로 하는 메서드
	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) { //entrySet()의 경우 키와 값을 모두 출력
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}
	//get 메서드에서 필요로 하는 메서드
	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}
	//get 메서드에서 필요로 하는 메서드
	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);
		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();
			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}

	// 17. 소셜로그인시 회원가입 여부 판단
	@Override
	public MemberVO socialIdChk(String socialID) {
		return memberDAO.socialIdChk(socialID);
	}

	// 소셜 로그인
	// -------------------------------------------------------------------------------------------------------------------------------------------------------

}
