package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.MemberVO;

public interface MemberDAO {
	// 1. 저장하기
	void insert(MemberVO memberVO);

	// 2. 1개 얻기
	MemberVO selectByIdx(int idx);

	// 3. 유저삭제
	void userDelete(int idx);

	// 4. 모두보기(관리자만 사용)
	List<MemberVO> selectList();

	// 5. 개수세기
	int selectCount();

	// 6. 해당아이디의 개수세기(아이디 중복확인 : 0이면 없는아이디, 1이상이면 존재하는 아이디)
	int selectCountByUserId(String mb_ID);

	// 7. 해당닉네임의 개수세기(닉네입 중복확인 : 0이면 없는닉네임, 1이상이면 존재하는 닉네임)
	int selectCountByUserNick(String mb_nick);

	// 8. 이름과 전화번호로 가져오기(아이디찾기 사용)
	MemberVO selectByUsername(HashMap<String, String> map);

	// 9. ID와과 전화번호로 가져오기(비번찾기 사용)
	MemberVO selectByUserId(HashMap<String, String> map);

	// 10. 인증여부를 변경하는 쿼리
	void updateRole(HashMap<String, String> map);

	// 11. 비밀번호 변경하기
	int updatePassword(HashMap<String, String> map);

	// 12. ID로 가져오기
	MemberVO selectUserId(String mb_ID);

	// 13. 주소 변경하기
	void updateAddress(HashMap<String, String> map);

	// 14. 닉네임 변경하기
	int updateNick(HashMap<String, String> map);

	// 15. 검색하기
	List<MemberVO> selectSearchMember(HashMap<String, Object> map);

	// 16. 검색결과를 카운트
	int selectSearchCount(CommonVO commVO);

	// 17. 소셜 로그인
	MemberVO socialIdChk(String socialId);

}
