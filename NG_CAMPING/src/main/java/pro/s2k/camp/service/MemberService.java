package pro.s2k.camp.service;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.MemberVO;
import pro.s2k.camp.vo.PagingVO;

public interface MemberService {

	// 1. 회원가입
	void insert(MemberVO memberVO);

	// 2 회원 탈퇴
	void userDelete(MemberVO memberVO);

	// 3. 비번 변경
	int updatePassword(MemberVO memberVO);

	// 4. 인증 완료
	MemberVO updateRole(String mb_ID, String authkey);

	// 5. 아이디 중복확인
	int idCheck(String mb_ID);

	// 6. 닉네임 중복확인
	int nickCheck(String mb_nick);

	// 7. 아이디 찾기
	MemberVO idSearch(MemberVO memberVO);

	// 8. 비번 찾기
	MemberVO passwordsearch(MemberVO memberVO);

	// 9. 임시비번 만들기
	String makePassword(int length);

	// 10. id와 비번이 같은것 가져오기
	MemberVO selectByUserId(MemberVO memberVO);

	// 11. 메일로 임시비밀번호 보내기
	void sendPassword(MemberVO memberVO);

	// 12. 닉네임 수정
	int updateNick(MemberVO memberVO);

	// 13. 아이디로 리스트 찾기
	PagingVO<MemberVO> selectList();

	// 14. 1개 얻기
	MemberVO selectByIdx(int mb_idx);

	// 15. 검색으로 정보 찾기	
	PagingVO<MemberVO> selectSearchMember(CommonVO commVO);

	// 16. 소셜로그인 (네이버,카카오,구글) 회원가입
	String naverMemberProfile(String access_token);

	String kakaoMemberProfile(String access_token);

	String googleMemberProfile(String accessToken);

	// 17. 소셜로그인시 회원가입 여부 판단
	MemberVO socialIdChk(String socialID);

}
