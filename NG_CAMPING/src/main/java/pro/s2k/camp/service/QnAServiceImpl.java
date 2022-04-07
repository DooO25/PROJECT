package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.s2k.camp.dao.QnADAO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;
import pro.s2k.camp.vo.ReviewVO;

@Service("QnAService")
public class QnAServiceImpl implements QnAService{

	@Autowired
	private QnADAO qnaDAO;
	
	@Override
	public PagingVO<QnAVO> selectList(CommonVO commVO) {
		PagingVO<QnAVO> pagingVO=null;
		try {
			int totalCount = qnaDAO.selectCount();
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			List<QnAVO> list = qnaDAO.selectList(map);
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public QnAVO selectByIdx(int idx) {
		QnAVO qnaVO = qnaDAO.selectByIdx(idx);
		return qnaVO;
	}
	@Override
	public QnAVO selectByIdxAnswer(int idx) {
		QnAVO qnaVO = qnaDAO.selectByIdxAnswer(idx);
		return qnaVO;
	}

	@Override
	public void insert(QnAVO qnaVO) {
		if(qnaVO!=null) {
			qnaDAO.insert(qnaVO);
		}
		
	}

	@Override
	public void answer(QnAVO qnaVO) {
		if(qnaVO!=null) {
			qnaDAO.answer(qnaVO);
			qnaDAO.updateRead(qnaVO.getQna_idx());
			}
	}

	@Override
	public void update(QnAVO qnaVO) {
		if(qnaVO!=null) {
			qnaDAO.update(qnaVO);
		}
	}

	@Override
	public void delete(QnAVO qnaVO) {
		qnaDAO.delete(qnaVO.getQna_idx());
		
	}

	@Override
	public void updateRead(int idx) {
		qnaDAO.updateRead(idx);
		
	}

	@Override
	public int selectMb_idx(int idx) {
		int mbIdx = qnaDAO.selectMb_idx(idx);
		return mbIdx;
	}

	@Override
	public void updateAnswer(QnAVO qnaVO) {
		if(qnaVO!=null) {
			qnaDAO.updateAnswer(qnaVO);
		}
		
	}

	@Override
	public PagingVO<QnAVO> selectSearchList(CommonVO commVO) {
		PagingVO<QnAVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = qnaDAO.selectSearchCount(commVO);
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			map.put("searchText",commVO.getSearchText());
			map.put("searchType",commVO.getSearchType());
			List<QnAVO> list = qnaDAO.selectSearchList(map);

			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}


}
