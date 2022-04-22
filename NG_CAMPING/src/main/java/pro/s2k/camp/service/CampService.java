package pro.s2k.camp.service;


import pro.s2k.camp.vo.CampInfoVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;

public interface CampService {
		
		PagingVO<CampInfoVO> selectCampSitel(CommonVO commonVO);
		
		// 정보 조회
		CampInfoVO selectCamplInfo(String facltNm);
		
		PagingVO<CampInfoVO> selectSearchList(CommonVO commVO);
		
		PagingVO<CampInfoVO> selectRandom(CommonVO commonVO);
	
}
