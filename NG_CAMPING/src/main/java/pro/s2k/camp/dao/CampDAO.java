package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CampInfoVO;
import pro.s2k.camp.vo.CommonVO;

public interface CampDAO {

	// 전체 캠핑장 리스트
	List<CampInfoVO> selectCampSitel(HashMap<String, Object> map);

	// 전체 캠핑장 개수 구하기
	int selectCountSite();

	// 캠핑장 이름으로 불러오기
	CampInfoVO selectCamplInfo(String facltNm);

	// 검색한 캠핑장 리스트
	List<CampInfoVO> selectSearchList(HashMap<String, Object> map);

	// 검색한 캠핑장 개수 구하기
	int selectSearchCount(CommonVO commonVO);

	// 랜덤으로 캠핑장 구하기
	List<CampInfoVO> selectRandom(HashMap<String, Object> map);

	// 랜덤으로 구한 캠핑장 개수
	int selectCountRandom();

	// 일반 야영장 개수 구하기
	int selectCountCampsitel();

	// 자동차 야영장 개수 구하기
	int selectCountCar();

	// 글램핑, 트레일러 야영장 개수 구하기
	int selectCountGlamping();
}
