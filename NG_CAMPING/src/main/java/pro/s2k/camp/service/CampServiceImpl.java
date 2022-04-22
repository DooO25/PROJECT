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

@Slf4j
@Service("campService")
public class CampServiceImpl implements CampService {

	@Autowired
	private CampDAO campDAO;

	@Override
	public PagingVO<CampInfoVO> selectCampSitel(CommonVO commonVO) {
		PagingVO<CampInfoVO> pagingVO = null;
		try {
			int totalCount = campDAO.selectCountSite();
			
			pagingVO = new PagingVO<>(commonVO.getCurrentPage(), commonVO.getPageSize(), commonVO.getBlockSize(), totalCount);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			map.put("lat", commonVO.getLat());
			map.put("lon", commonVO.getLon());
			List<CampInfoVO> list = campDAO.selectCampSitel(map);
			
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
	
	@Override
	public PagingVO<CampInfoVO> selectSearchList(CommonVO commVO) {
		PagingVO<CampInfoVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = campDAO.selectSearchCount(commVO);
			log.info("$$$"+totalCount);
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			map.put("searchText",commVO.getSearchText());
			map.put("searchType",commVO.getSearchType());
			map.put("searchType2",commVO.getSearchType2());
			map.put("animalCheck",commVO.getAnimalCheck());
			map.put("lat", commVO.getLat());
			map.put("lon", commVO.getLon());
			List<CampInfoVO> list = campDAO.selectSearchList(map);
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public CampInfoVO selectCamplInfo(String facltNm) {
		return campDAO.selectCamplInfo(facltNm);
	}
	
	@Override
	public PagingVO<CampInfoVO> selectRandom(CommonVO commonVO) {
		PagingVO<CampInfoVO> pagingVO = null;
		try {
			int totalCount = campDAO.selectCountRandom();
			pagingVO = new PagingVO<>(commonVO.getCurrentPage(), commonVO.getPageSize(), commonVO.getBlockSize(), totalCount);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			List<CampInfoVO> list = campDAO.selectRandom(map);
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
}


