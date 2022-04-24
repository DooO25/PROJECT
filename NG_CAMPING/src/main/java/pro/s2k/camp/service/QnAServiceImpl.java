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
public class QnAServiceImpl implements QnAService {

	@Autowired
	private QnADAO qnaDAO;

	@Override
	public PagingVO<QnAVO> selectList(CommonVO commVO) { // Q&A 게시물 리스트
		PagingVO<QnAVO> pagingVO = null; // 페이징
		try {
			int totalCount = qnaDAO.selectCount(); // 전체 개수 카운트 qnaMapper selectCount 쿼리 실행
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo()); // 시작 글번호
			map.put("pageSize", pagingVO.getPageSize());// 끝 글번호
			List<QnAVO> list = qnaDAO.selectList(map); // qnaMapper selectList 쿼리 실행
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public QnAVO selectByIdx(int idx) { // 질문글 조회
		QnAVO qnaVO = qnaDAO.selectByIdx(idx); // qnaMapper selectByIdx 쿼리 실행
		return qnaVO;
	}

	@Override
	public QnAVO selectByIdxAnswer(int idx) { // 답변글 가져오기
		QnAVO qnaVO = qnaDAO.selectByIdxAnswer(idx); // qnaMapper selectByIdxAnswer 쿼리 실행
		return qnaVO;
	}

	@Override
	public void insert(QnAVO qnaVO) { // 질문글 저장
		if (qnaVO != null) {
			qnaDAO.insert(qnaVO); // qnaMapper insert 쿼리 실행
		}
	}

	@Override
	public void answer(QnAVO qnaVO) { // 답변 저장
		if (qnaVO != null) {
			qnaDAO.answer(qnaVO); // qnaMapper udpate 쿼리 실행
			// 질문글 조회 여부(미조회, 조회, 답변)
			qnaDAO.updateRead(qnaVO.getQna_idx()); // qnaMapper updateRead 쿼리 실행
		}
	}

	@Override
	public void update(QnAVO qnaVO) {	// 질문글 수정
		if(qnaVO!=null) {
			qnaDAO.update(qnaVO);		// qnaMapper update 쿼리 실행
		}
	}

	@Override
	public void delete(QnAVO qnaVO) {	// 질문글 삭제
		qnaDAO.delete(qnaVO.getQna_idx());	// qnaMapper delete 쿼리 실행
	}

	@Override
	public void updateRead(int idx) {	// 질문글 조회 여부(미조회, 조회, 답변)
		qnaDAO.updateRead(idx);			// qnaMapper updateRead 쿼리 실행
	}

	@Override
	public int selectMb_idx(int idx) {	// 질문글 작성자 여부 체크(삭제시)
		int mbIdx = qnaDAO.selectMb_idx(idx);	// qnaMapper selectMb_idx 쿼리 실행
		return mbIdx;
	}

	@Override
	public void updateAnswer(QnAVO qnaVO) {	// 답변 수정
		if(qnaVO!=null) {
			qnaDAO.updateAnswer(qnaVO);		// qnaMapper updateAnswer 실행
		}
	}
	
	@Override
	public PagingVO<QnAVO> selectSearchList(CommonVO commonVO) {	// qna 검색
		PagingVO<QnAVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = qnaDAO.selectSearchCount(commonVO);
			// 페이지 계산
			pagingVO = new PagingVO<>(commonVO.getCurrentPage(), commonVO.getPageSize(), commonVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			map.put("searchText",commonVO.getSearchText());
			map.put("searchType",commonVO.getSearchType());
			List<QnAVO> list = qnaDAO.selectSearchList(map);	// qnaMapper selectSearchList 쿼리 실행

			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
}