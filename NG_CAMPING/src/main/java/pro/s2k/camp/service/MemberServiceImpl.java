package pro.s2k.camp.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.MemberDAO;
import pro.s2k.camp.dao.RoleDAO;
import pro.s2k.camp.vo.MemberVO;
import pro.s2k.camp.vo.PagingVO;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;



@Slf4j
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	/*
	 * @Autowired private KakaoDAO kakaoDAO;
	 */
	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public MemberVO loginOk(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO logout(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public PagingVO<MemberVO> selectList() {
		PagingVO<MemberVO> vo = null;
		try {
			int totalCount = memberDAO.selectCount();
			vo = new PagingVO<>(totalCount);
			List<MemberVO> list = memberDAO.selectList();
			if(list!=null && list.size()>0) {
				vo.setList(list);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	public MemberVO selectByIdx(int idx) {
		MemberVO memberVO = memberDAO.selectByIdx(idx);
		return memberVO;
	}

	@Override
	public void insert(MemberVO memberVO) {
		// DB에 저장한다.
		if (memberVO != null) {
			memberDAO.insert(memberVO);
			roleDAO.insert(memberVO.getMb_ID());
			sendEmail(memberVO);
		}
	}

	@Override
	public MemberVO update(MemberVO memberVO) {
		if (memberVO != null) {
			MemberVO dbVO = memberDAO.selectByIdx(memberVO.getMb_idx());
			if (dbVO != null) {
				String dbPassword = dbVO.getMb_password();
				if (bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
					memberDAO.update(memberVO);
					dbVO = memberDAO.selectByIdx(memberVO.getMb_idx());
					return dbVO;
				}
			}
		}
		return null;
	}

	@Override
	public void delete(MemberVO memberVO) {
		if (memberVO != null) {
			MemberVO vo = memberDAO.selectUserId(memberVO.getMb_ID());
			if (vo != null) {
				String dbPassword = vo.getMb_password();
				if (bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
					memberDAO.delete(vo.getMb_idx());
				}
			}
		}
	}
	
	@Override
	public void userDelete(MemberVO memberVO) {
		if (memberVO != null) {
			MemberVO vo = memberDAO.selectUserId(memberVO.getMb_ID());
			if (vo != null) {
				String dbPassword = vo.getMb_password();
				if (bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
					memberDAO.userDelete(vo.getMb_idx());
				}
			}
		}
	}

	@Override
	public int updatePassword(MemberVO memberVO) {
  		int result = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		String encryptPassword =bCryptPasswordEncoder.encode(memberVO.getMb_password());
		map.put("mb_ID", memberVO.getMb_ID());			 
		map.put("mb_password", encryptPassword);
		result += memberDAO.updatePassword(map);
		
		return result;
		}	      


	@Override
	public MemberVO updateRole(String mb_ID, String authkey) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mb_ID", mb_ID);
		map.put("authkey", authkey);
		memberDAO.updateRole(map);
		return memberDAO.selectUserId(mb_ID);
	}

	@Override
	public int idCheck(String mb_ID) {
		return memberDAO.selectCountByUserId(mb_ID);
	}

	@Override
	public int nickCheck(String mb_nick) {
		return memberDAO.selectCountByUserNick(mb_nick);
	}

	@Override // 아이디 찾기 :이름과 전화번호를 넘겨서 DB에서 가져온다.
	public MemberVO idSearch(MemberVO memberVO) {
		MemberVO vo = null;
		if (memberVO != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mb_name", memberVO.getMb_name());
			map.put("mb_tel", memberVO.getMb_tel());
			vo = memberDAO.selectByUsername(map);
		}
		return vo;
	}

	@Override // 비밀번호 찾기 :아이디와 이메일을 넘겨서 DB에서 가져온다.
	public MemberVO passwordsearch(MemberVO memberVO) {
		MemberVO vo = null;
		if (memberVO != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mb_ID", memberVO.getMb_ID());
			map.put("mb_email", memberVO.getMb_email());
			vo = memberDAO.selectByUserId(map);

		}

		return vo;

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

	// 비밀번호 찾기 임시비밀번호 메일 보내는 메서드


	// 임시 비번
	@Override
	public String makePassword(int length) {
		Random random = new Random();
		String password = "";
		String str = "~@!#$%^&*+-*";
		for (int i = 0; i < length; i++) {
			// case의 개수가 많을수록 나타날 확율이 높아진다.
			switch (random.nextInt(20)) { // 0(숫자), 1(영어소문자), 2(영어 대문자), 3(특수문자)
			case 0:
			case 18:
			case 19:
				password += (char) ('0' + random.nextInt(10));
				break;
			case 1:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				password += (char) ('a' + random.nextInt(26));
				break;
			case 2:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
				password += (char) ('A' + random.nextInt(26));
				break;
			case 3:
				password += str.charAt(random.nextInt(str.length()));
				break;
			}
		}
		return password;
	}
	
	@Override
	public void sendPassword(MemberVO memberVO) {
		String subject = "NG_CAMP 임시 비밀번호 입니다.";
		String to = memberVO.getMb_email();
		String content = "반갑습니다. " + memberVO.getMb_ID() + "님!!!<br>" + "NG_CAMP에서 요청하신 비밀번호 찾기 메일로,<br> "
				+ "회원님의 임시 비밀번호는 " + memberVO.getMb_password() + " 입니다.";

		MimeMessagePreparator preparator = getMessagePreparator(to, subject, content);
		try {
			mailSender.send(preparator);
			System.out.println("메일 보내기 성공 ***************************************************");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public MemberVO selectByUserId(MemberVO memberVO) {
		MemberVO vo = null;
		if (memberVO != null) {
			vo = memberDAO.selectUserId(memberVO.getMb_ID());
			if (vo != null) {
				String dbPassword = vo.getMb_password();
				if (!bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
					return null;
				}
			}
		}
		return vo;
	}
	/*
	 * // 카카오 엑세스토큰 받아오는 메서드 public String getAccessToken (String authorize_code) {
	 * String access_Token = ""; String refresh_Token = ""; String reqURL =
	 * "https://kauth.kakao.com/oauth/token";
	 * 
	 * try { URL url = new URL(reqURL);
	 * 
	 * HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // POST
	 * 요청을 위해 기본값이 false인 setDoOutput을 true로
	 * 
	 * conn.setRequestMethod("POST"); conn.setDoOutput(true); // POST 요청에 필요로 요구하는
	 * 파라미터 스트림을 통해 전송
	 * 
	 * BufferedWriter bw = new BufferedWriter(new
	 * OutputStreamWriter(conn.getOutputStream())); StringBuilder sb = new
	 * StringBuilder(); sb.append("grant_type=authorization_code");
	 * 
	 * sb.append("&client_id=8e9a2c0fe5fd14072d6be59bf53313d6"); //본인이 발급받은 key
	 * sb.append("&redirect_uri=http://localhost:8080/kakaoLogin.do"); // 본인이 설정한 주소
	 * 
	 * sb.append("&code=" + authorize_code); bw.write(sb.toString()); bw.flush();
	 * 
	 * // 결과 코드가 200이라면 성공 int responseCode = conn.getResponseCode();
	 * System.out.println("responseCode : " + responseCode);
	 * 
	 * // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기 BufferedReader br = new
	 * BufferedReader(new InputStreamReader(conn.getInputStream())); String line =
	 * ""; String result = "";
	 * 
	 * while ((line = br.readLine()) != null) { result += line; }
	 * System.out.println("response body : " + result);
	 * 
	 * // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성 JsonParser parser = new JsonParser();
	 * JsonElement element = parser.parse(result);
	 * 
	 * access_Token = element.getAsJsonObject().get("access_token").getAsString();
	 * refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
	 * 
	 * System.out.println("access_token : " + access_Token);
	 * System.out.println("refresh_token : " + refresh_Token);
	 * 
	 * br.close(); bw.close(); } catch (IOException e) { e.printStackTrace(); }
	 * return access_Token; }
	 * 
	 * // 카캌오 회원정보 가져오는 메서드
	 * 
	 * @Override public KakaoVO getUserInfo(String access_Token) {
	 * 
	 * // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언 HashMap<String, Object>
	 * userInfo = new HashMap<String, Object>(); String reqURL =
	 * "https://kapi.kakao.com/v2/user/me"; try { URL url = new URL(reqURL);
	 * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 * conn.setRequestMethod("GET");
	 * 
	 * // 요청에 필요한 Header에 포함될 내용 conn.setRequestProperty("Authorization", "Bearer "
	 * + access_Token);
	 * 
	 * int responseCode = conn.getResponseCode();
	 * System.out.println("responseCode : " + responseCode);
	 * 
	 * BufferedReader br = new BufferedReader(new
	 * InputStreamReader(conn.getInputStream()));
	 * 
	 * String line = ""; String result = "";
	 * 
	 * while ((line = br.readLine()) != null) { result += line; }
	 * System.out.println("response body : " + result);
	 * 
	 * JsonParser parser = new JsonParser(); JsonElement element =
	 * parser.parse(result);
	 * 
	 * JsonObject properties =
	 * element.getAsJsonObject().get("properties").getAsJsonObject(); JsonObject
	 * kakao_account =
	 * element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	 * 
	 * String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	 * String email = kakao_account.getAsJsonObject().get("email").getAsString();
	 * userInfo.put("nickname", nickname); userInfo.put("email", email);
	 * log.info(nickname+"#######################################"); } catch
	 * (IOException e) { e.printStackTrace(); } KakaoVO result =
	 * kakaoDAO.findKakao(userInfo); // 위 코드는 먼저 정보가 저장되있는지 확인하는 코드.
	 * System.out.println("S:" + result); if(result==null) { // result가 null이면 정보가
	 * 저장이 안되있는거므로 정보를 저장. kakaoDAO.kakaoInsert(userInfo); // 위 코드가 정보를 저장하기 위해
	 * Repository로 보내는 코드임. return kakaoDAO.findKakao(userInfo); // 위 코드는 정보 저장 후
	 * 컨트롤러에 정보를 보내는 코드임. // result를 리턴으로 보내면 null이 리턴되므로 위 코드를 사용. } else { return
	 * result; // 정보가 이미 있기 때문에 result를 리턴함. } }
	 */
	//닉네임 변경
	@Override
	public int updateNick(MemberVO memberVO) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mb_ID", memberVO.getMb_ID());
		map.put("mb_nick", memberVO.getMb_nick());
		return memberDAO.updateNick(map);
	}

// 네이버-------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public String naverMemberProfile() {
		String token = "AAAAN-bBh9O7n5gBgKblzl_zWmzisuBSZvEsmCVNBbnSXJZ_H9VesvCuD8Z4U1_SfTCqPV3JEvBEZJgKZnv2O90YB0o"; // 네이버 로그인 접근 토큰;
        String header = "Bearer " + token; // Bearer 다음에 공백 추가

        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);
        String split[] = responseBody.split(",");
        
        JsonElement json = JsonParser.parseString(responseBody);
        log.info(Arrays.toString(split)+"#####");
        log.info(json+"@@@@@@");
        System.out.println(responseBody);
        
        
        return responseBody;
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
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
	
	private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
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
}
// 네이버-------------------------------------------------------------------------------------------------------------------------------------------------------