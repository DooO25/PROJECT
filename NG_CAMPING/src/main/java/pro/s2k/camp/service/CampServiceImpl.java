package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.CampDAO;
import pro.s2k.camp.vo.CampInfoVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;

@Service("campService")
public class CampServiceImpl implements CampService {

	@Autowired
	private CampDAO campDAO;

	@Override
	public PagingVO<CampInfoVO> selectCampSitel(CommonVO commonVO) {	// 캠핑장 리스트
		PagingVO<CampInfoVO> pagingVO = null;
		try {
			int totalCount = campDAO.selectCountSite();		// campMapper selectCountSite 쿼리 실행
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commonVO.getCurrentPage(), commonVO.getPageSize(), commonVO.getBlockSize(), totalCount);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());		// 시작 글 번호
			map.put("pageSize", pagingVO.getPageSize());	// 페이지당 글의 수
			map.put("lat", commonVO.getLat());				// 위도
			map.put("lon", commonVO.getLon());				// 경도
			List<CampInfoVO> list = campDAO.selectCampSitel(map);	// campMapper selectCampSitel 쿼리 실행
			
			pagingVO.setList(list);	// 페이징 객체에 list 담기
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public CampInfoVO selectCamplInfo(String facltNm) {	// 캠핑장 조회
		return campDAO.selectCamplInfo(facltNm);		// campMapper selectCampInfo 쿼리 실행
	}
	
	@Override
	public PagingVO<CampInfoVO> selectSearchList(CommonVO commVO) {	// 캠핑장 검색
		PagingVO<CampInfoVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = campDAO.selectSearchCount(commVO);	// campMapper selectSearchCount 쿼리 실행
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());		// 시작 번호
			map.put("pageSize", pagingVO.getPageSize());	// 페이지당 캠핑장 수
			map.put("searchText",commVO.getSearchText());	// 캠핑장 이름 검색
			map.put("searchType",commVO.getSearchType());	// 도 이름 선택
			map.put("searchType2",commVO.getSearchType2());	// 시군구 선택
			map.put("animalCheck",commVO.getAnimalCheck());	// 애완동물 동반여부 체크
			map.put("lat", commVO.getLat());				// 위도
			map.put("lon", commVO.getLon());				// 경도
			List<CampInfoVO> list = campDAO.selectSearchList(map);	// campMapper selectSearchList 쿼리 실행
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
	
	@Override
	public PagingVO<CampInfoVO> selectRandom(CommonVO commonVO) {	// 메인에 표기하는 캠핑장 리스트
		PagingVO<CampInfoVO> pagingVO = null;
		try {
			int totalCount = campDAO.selectCountRandom();	// campMapper selectCountRandom 쿼리 실행
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commonVO.getCurrentPage(), commonVO.getPageSize(), commonVO.getBlockSize(), totalCount);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());		// 시작 번호
			map.put("pageSize", pagingVO.getPageSize());	// 표기할 캠핑장 수
			List<CampInfoVO> list = campDAO.selectRandom(map);	// campMapper selectRandom 쿼리 실행
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
}



