package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.CommentDAO;
import pro.s2k.camp.dao.ReviewDAO;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;

@Slf4j
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDAO reviewDAO;
	@Autowired
	private CommentDAO commentDAO;

	@Override
	public PagingVO<ReviewVO> selectList(CommonVO commVO) { // 후기 게시글 리스트
		PagingVO<ReviewVO> pagingVO = null;	// 페이징 작업 변수
		try {
			// 전체 개수 구하기
			int totalCount = reviewDAO.selectCount();
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo());	// 시작 글번호
			map.put("pageSize", pagingVO.getPageSize());// 끝 글번호
			List<ReviewVO> list = reviewDAO.selectList(map); // reviewMapper selectList 쿼리 실행

			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public ReviewVO selectByIdx(Map<String, Integer> map) { // 후기 조회
		ReviewVO reviewVO = reviewDAO.selectByIdx(map.get("rv_idx")); // reviewMapper selectByIdx 쿼리 실행
		// 인수로 받아온 mode가 1이면
		if(map.get("mode")==1) {
			reviewDAO.updateHit(map.get("rv_idx")); // 조회수 증가
			reviewVO.setRv_hit(reviewVO.getRv_hit() + 1); // reviewMapper updateHit 쿼리 실행
		}
		return reviewVO;
	}

	@Override
	public void insert(ReviewVO reviewVO) { // 후기 작성
		if(reviewVO!=null) {
			// 글 저장
			reviewDAO.insert(reviewVO);	// reviewMapper insert 쿼리 실행
		}
	}

	@Override
	public void update(ReviewVO reviewVO) { // 후기 수정
		if (reviewVO != null) {
			// DB에서 해당 글번호의 글 읽어온다.
			ReviewVO dbVO = reviewDAO.selectByIdx(reviewVO.getRv_idx()); // reviewMapper selectByIdx 쿼리 실행(파라미터로 vo에 객체로 담긴 후기 고유번호)
			// 글이 있고, 비번이 같으면 수정한다.
			if (dbVO != null) {
				// 글수정
				reviewDAO.update(reviewVO);
			}
		}
	}

	@Override
	public int selectMb_idx(int idx) {	// 작성자인지 점검하기 위한 유저 고유번호 가져오기
		int mbIdx = reviewDAO.selectMb_idx(idx);	// reviewMapper selectMb_idx 쿼리 실행
		return mbIdx;
	}

	@Override
	public void delete(ReviewVO reviewVO) {		// 후기 삭제
		if(reviewVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			List<CommentVO> commentVO = commentDAO.selectList(reviewVO.getRv_idx()); // 댓글이 존재하면 삭제하지 않기 위한 commentMapper selectList 쿼리 실행
			// 글이 없으면 삭제한다.
			if(commentVO.isEmpty()) { // 댓글이 없다면
				reviewDAO.delete2(reviewVO.getRv_idx()); // 후기 삭제
			}else {
				reviewDAO.updateDel(reviewVO.getRv_idx()); // 삭제 표시 (댓글이 존재할 경우 댓글을 작성한 작성자들을 위해 게시글을 안보이게 설정함) reviewMapper updateDel 쿼리 실행 -> del = 1
			}
		}
	}
	
	@Override
	public PagingVO<ReviewVO> selectSearchList(CommonVO commVO) {		// 검색 기능
		PagingVO<ReviewVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = reviewDAO.selectSearchCount(commVO);
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			map.put("searchText", commVO.getSearchText());
			map.put("searchType", commVO.getSearchType());
			List<ReviewVO> list = reviewDAO.selectSearchList(map);	// reviewMapper selectSearchList 쿼리 실행

			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
}
