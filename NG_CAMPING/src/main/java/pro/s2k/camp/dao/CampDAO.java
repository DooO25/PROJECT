package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.s2k.camp.vo.CampInfoVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.NoticeVO;



public interface CampDAO {
	
	// 일반야영장 불러오기
	List<CampInfoVO> selectCampSitel(HashMap<String, Object> map);
	int selectCountSite();
	
	//idx로 불러오기
	CampInfoVO selectCamplInfo(String facltNm); 

	// 검색기능
	List<CampInfoVO> selectSearchList(HashMap<String, Object> map);
	int selectSearchCount(CommonVO commonVO);
	// 메인 랜덤
	List<CampInfoVO> selectRandom(HashMap<String, Object> map);
	int selectCountRandom();

	// 메인에 개수찍기
	int selectCountTotal();
	int selectCountCampsitel();
	int selectCountCar();
	int selectCountGlamping();
}
