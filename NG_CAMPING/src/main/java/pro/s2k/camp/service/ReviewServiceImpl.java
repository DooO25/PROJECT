package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.s2k.camp.dao.CommentDAO;
import pro.s2k.camp.dao.ReviewDAO;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;


@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDAO reviewDAO;
	@Autowired
	private CommentDAO commentDAO;


	@Override
	public PagingVO<ReviewVO> selectList(CommonVO commVO) {
		PagingVO<ReviewVO> pagingVO = null;
	
		try {
			// 전체 개수 구하기
			int totalCount = reviewDAO.selectCount();
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("pageSize", pagingVO.getPageSize());
			List<ReviewVO> list = reviewDAO.selectList(map);

//			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
	

	@Override
	public ReviewVO selectByIdx(int idx) {
		ReviewVO reviewVO = reviewDAO.selectByIdx(idx); // 글 1개를 가져온다.
		reviewVO.setRv_hit(reviewVO.getRv_hit()+1);
		reviewDAO.updateHit(idx);
		return reviewVO;
	}
	
	@Override
	public void insert(ReviewVO reviewVO) {
		// 컨트롤러에 있는 파일을 저장하는 부분을 유틸리티 클래스로 빼주면 컨트롤러가 간단해 진다.
		if(reviewVO!=null) {
			// 1. 글을 저장한다.
			reviewDAO.insert(reviewVO);
		}
	}

	@Override
	public void update(ReviewVO reviewVO) {
		if(reviewVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			ReviewVO dbVO = reviewDAO.selectByIdx(reviewVO.getRv_idx());
			// 글이 있고, 비번이 같으면 수정한다.
			if(dbVO!=null) {
				// 1. 글수정
				reviewDAO.update(reviewVO);
				// 2. 새롭게 첨부된 첨부 파일의 정보도 저장해 주어야 한다.

			}
		}
	}

	@Override
	public int selectMb_idx(int idx) {
		int mbIdx = reviewDAO.selectMb_idx(idx);
		return mbIdx;
	}

	@Override
	public void delete(ReviewVO reviewVO) {
		if(reviewVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			List<CommentVO> coVO = commentDAO.selectList(reviewVO.getRv_idx());
			// 글이 없으면 삭제한다.
			if(coVO==null) {
				reviewDAO.delete1(reviewVO.getRv_idx());

			}else {
				reviewDAO.updateDel(reviewVO.getRv_idx()); // 삭제 표시
			}
		}
	} // end if
} // end method





