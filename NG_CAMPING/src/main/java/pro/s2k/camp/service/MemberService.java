package pro.s2k.camp.service;

import pro.s2k.camp.vo.KakaoVO;
import pro.s2k.camp.vo.MemberVO;
import pro.s2k.camp.vo.RoleVO;

public interface MemberService {
// 1. 로그인
	MemberVO loginOk(MemberVO memberVO);
// 2. 로그아웃
	MemberVO logout (MemberVO memberVO);	
// 3. 회원가입
	void insert(MemberVO memberVO);

	/*
	 * // 4. 회원 탈퇴 void delete(MemberVO memberVO);
	 */
// 5. 비번 변경
	int updatePassword(MemberVO memberVO);
// 6. 인증 완료
	MemberVO updateRole(String mb_ID, String authkey);
// 7. 아이디 중복확인
	int idCheck(String mb_ID);
// 8. 닉네임 중복확인
   int nickCheck(String mb_nick);
// 9. 아이디 찾기
	MemberVO idSearch(MemberVO memberVO);
// 10. 비번 찾기
	MemberVO passwordsearch(MemberVO memberVO);
// 11. 임시비번 만들기
	String makePassword(int length);
// 12. id와 비번이 같은것 가져오기
	MemberVO selectByUserId(MemberVO memberVO);
// 13. 메일로 임시비밀번호 보내기
	void sendPassword(MemberVO memberVO);	
// 14. 닉네임 수정
	int updatenick(MemberVO memberVO);
// 15. 주소 수정
	void updateaddress(MemberVO memberVO);
// 16. 유저가 탈퇴누를때
	int userdelete(String mb_ID);
	 
	/*
	 * // 15. accessToken 얻기 String getAccessToken(String code); // 16. 사용자 정보 가져오기
	 * KakaoVO getUserInfo(String access_Token);
	 */
}
