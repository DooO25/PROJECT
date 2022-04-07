package pro.s2k.camp.dao;

import java.util.HashMap;

import pro.s2k.camp.vo.KakaoVO;

public interface KakaoDAO {
	// 정보 찾기
	KakaoVO findKakao(HashMap<String, Object> userInfo);
	
	// 정보 저장
	void kakaoInsert(HashMap<String, Object> userInfo);
	
	
	
}
