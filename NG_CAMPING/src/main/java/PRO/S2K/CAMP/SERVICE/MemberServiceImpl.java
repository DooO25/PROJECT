package PRO.S2K.CAMP.SERVICE;

import java.util.HashMap;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import PRO.S2K.CAMP.DAO.GradeDAO;
import PRO.S2K.CAMP.DAO.MemberDAO;
import PRO.S2K.CAMP.VO.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private GradeDAO gradeDAO;
	
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

	@Override
	public void insert(MemberVO memberVO) {
		// DB에 저장한다.
		log.info("insert 호출 : " + memberVO);
		if(memberVO!=null) {
			memberDAO.insert(memberVO);
			/* gradeDAO.insert(memberVO.getMb_ID()); 이거 사용 X*/   
			/* sendEmail(memberVO);  나중에 다시 활성화 ON */
			sendEmail(memberVO);
		}
	}

	@Override
	public MemberVO update(MemberVO memberVO) {
		if(memberVO!=null) {
			MemberVO dbVO = memberDAO.selectByIdx(memberVO.getMb_idx());
			if(dbVO!=null) {
				String dbPassword = dbVO.getMb_password();
				if(bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
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
		if(memberVO!=null) {
			MemberVO vo = memberDAO.selectUserId(memberVO.getMb_ID());
			if(vo!=null) {
				String dbPassword = vo.getMb_password();
				if(bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
					memberDAO.delete(vo.getMb_idx());
				}
			}
		}
	}

	@Override
	public void updatePassword(MemberVO memberVO) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mb_Id", memberVO.getMb_ID());
		String encryptPassword = bCryptPasswordEncoder.encode(memberVO.getMb_password());
		map.put("mb_Password", encryptPassword);
		memberDAO.updatePassword(map);
	}

	@Override
	public MemberVO updateUse(String mb_ID, String authkey) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("use", 1);
		map.put("mb_ID", mb_ID);
		map.put("authkey", authkey);
		memberDAO.updateUse(map);
		return memberDAO.selectUserId(mb_ID);
	}

	@Override
	public int idCheck(String mb_ID) {
		return memberDAO.selectCountByUserId(mb_ID);		
	}
	
	@Override
	public int nickCheck(String mb_nick) {
		return memberDAO.selectCountByUsernick(mb_nick);
	}

	@Override
	public MemberVO idSearch(MemberVO memberVO) {
		MemberVO vo = null;
		if(memberVO!=null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mb_Name", memberVO.getMb_name());
			map.put("mb_Tel", memberVO.getMb_tel());
			vo = memberDAO.selectByUsername(map);
		}
		return vo;
	}

	@Override
	public MemberVO passwordsearch(MemberVO memberVO) {
		MemberVO vo = null;
		if(memberVO!=null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mb_Name", memberVO.getMb_name());
			map.put("mb_Tel", memberVO.getMb_tel());
			vo = memberDAO.selectByUsername(map);
		}
		
		return vo;
		
	}
	// 회원 가입시 인증 메일 보내는 메서드 
	public void sendEmail(MemberVO memberVO) {
		String subject = "회원 가입을 축하드립니다.";
		String to = memberVO.getMb_email();
		String content = "반갑습니다. " + memberVO.getMb_nick() + "님!!!<br>"
                + "회원 가입을 축하드립니다.<br> "
        		+ "회원 가입을 완료하려면 다음의 링크를 클릭해서 인증하시기 바랍니다.<br>"
                + "<a href='http://localhost:8080/confirm?getMb_ID="+memberVO.getMb_ID()+"&Authkey="+memberVO.getAuthkey()+"'>인증</a><br>";
		
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
            	helper.setFrom("S2KCCamp@gmail.com");
            	helper.setTo(to);
            	helper.setSubject(subject);
            	helper.setText(content, true);
            }
        };
        return preparator;
    }
	
	// 임시 비번
	@Override
	public String makePassword(int length) {
		Random random = new Random();
		String password="";
		String str="~@!#$%^&*+-*";
		for(int i=0;i<length;i++) {
			// case의 개수가 많을수록 나타날 확율이 높아진다.
			switch (random.nextInt(20)) { // 0(숫자), 1(영어소문자), 2(영어 대문자), 3(특수문자)
			case 0: case 18: case 19:
				password += (char)('0' + random.nextInt(10));
				break;
			case 1: case 4: case 5: case 6: case 7: case 8: case 9: case 10:
				password += (char)('a' + random.nextInt(26));
				break;
			case 2: case 11: case 12: case 13: case 14: case 15: case 16: case 17:
				password += (char)('A' + random.nextInt(26));
				break;
			case 3:
				password += str.charAt(random.nextInt(str.length()));
				break;
			}
		}
		return password;
	}
	
	@Override
	public MemberVO selectByUserId(MemberVO memberVO) {
		MemberVO vo = null;
		if(memberVO!=null) {
			vo = memberDAO.selectUserId(memberVO.getMb_ID());
			if(vo!=null) {
				String dbPassword = vo.getMb_password();
				if(!bCryptPasswordEncoder.matches(memberVO.getMb_password(), dbPassword)) {
					return null; 
				}
			}
		}
		return vo; 
	}
}
