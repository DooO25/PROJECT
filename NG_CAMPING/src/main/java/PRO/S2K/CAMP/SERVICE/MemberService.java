package PRO.S2K.CAMP.SERVICE;

import PRO.S2K.CAMP.VO.MemberVO;

public interface MemberService {
// 1. 로그인
	MemberVO loginOk(MemberVO memberVO);
// 2. 로그아웃
	MemberVO logout (MemberVO memberVO);	
	
// 3. 회원가입
	void insert(MemberVO memberVO);
// 4. 회원정보수정
	MemberVO update(MemberVO memberVO);
// 5. 회원 탈퇴
	void delete(MemberVO memberVO);
// 6. 비번 변경
	void updatePassword(MemberVO memberVO);
// 7. 인증 완료
	MemberVO updateGrade(String mb_ID, String authkey);
// 8. 아이디 중복확인
	int idCheck(String mb_ID);
// 9. 닉네임 중복확인
   int nickCheck(String mb_nick);
// 10. 아이디 찾기
	MemberVO idSearch(MemberVO memberVO);
// 11. 비번 찾기
	MemberVO passwordsearch(MemberVO memberVO);
// 12. 임시비번 만들기
	String makePassword(int length);
// 13. id와 비번이 같은것 가져오기
	MemberVO selectByUserId(MemberVO memberVO);

	
}
