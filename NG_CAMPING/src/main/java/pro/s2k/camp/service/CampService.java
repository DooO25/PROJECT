package pro.s2k.camp.service;

import pro.s2k.camp.vo.CampInfoVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;

public interface CampService {

	// 캠핑장 리스트
	PagingVO<CampInfoVO> selectCampSitel(CommonVO commonVO);

	// 정보 조회
	CampInfoVO selectCamplInfo(String facltNm);

	// 캠핑장 검색
	PagingVO<CampInfoVO> selectSearchList(CommonVO commVO);

	// 메인페이지 표기할 캠핑장
	PagingVO<CampInfoVO> selectRandom(CommonVO commonVO);

}
