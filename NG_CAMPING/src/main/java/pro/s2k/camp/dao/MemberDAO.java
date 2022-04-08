package pro.s2k.camp.dao;


import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.MemberVO;

public interface MemberDAO {
	// 1. 저장하기
		void insert(MemberVO memberVO);
		// 2. 1개 얻기
		MemberVO selectByIdx(int idx);
		// 3. 수정하기
		void update(MemberVO memberVO);
		// 4. 삭제하기
		void delete(int idx);
		// 4-1. 유저삭제
		void userDelete(int idx);
		// 5. 모두보기(관리자만 사용) 
		List<MemberVO> selectList();
		// 6. 개수세기
		int selectCount();
		// 7. 해당아이디의 개수세기(아이디 중복확인 : 0이면 없는아이디, 1이상이면 존재하는 아이디)
		int selectCountByUserId(String mb_ID);
		// 8. 해당닉네임의 개수세기(닉네입 중복확인 : 0이면 없는닉네임, 1이상이면 존재하는 닉네임)
		int selectCountByUserNick(String mb_nick);
		// 9. 이름과 전화번호로 가져오기(아이디찾기 사용)
		MemberVO selectByUsername(HashMap<String, String> map);
		// 10. ID와과 전화번호로 가져오기(비번찾기 사용)
		MemberVO selectByUserId(HashMap<String, String> map);
		// 11. 인증여부를 변경하는 쿼리
		void updateRole (HashMap<String, String> map);
		// 12. 비밀번호 변경하기
		int updatePassword(HashMap<String, String> map);
		// 13. ID로 가져오기
		MemberVO selectUserId(String mb_ID);
		// 14. 주소 변경하기
		void updateAddress(HashMap<String, String> map);
		// 15. 닉네임 변경하기
		int updateNick(HashMap<String, String> map);
		
		String selectSocialID(String socialID);
		
	
}
