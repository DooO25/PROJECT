package PRO.S2K.CAMP.DAO;

import java.util.HashMap;

import PRO.S2K.CAMP.VO.KakaoVO;

public interface KakaoDAO {
	// 정보 찾기
	KakaoVO findKakao(HashMap<String, Object> userInfo);
	
	// 정보 저장
	void kakaoInsert(HashMap<String, Object> userInfo);
	
	
	
}
