package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.CommentDAO;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.PagingVO;

@Slf4j
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public PagingVO<CommentVO> selectList(int idx) {
		PagingVO<CommentVO> pagingVO = null;
		try {
			// 1개글에서 총 개수 구하기
			int totalCount = commentDAO.selectCount(idx);
			log.info(totalCount+"총개수");
			pagingVO = new PagingVO<>(totalCount);
			log.info(pagingVO+"pagingVO");
//			 글을 읽어오기
			List<CommentVO> list = commentDAO.selectList(idx);
			log.info(list+"list");
//			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
	
	@Override
	public CommentVO selectByIdx(int idx) {
		CommentVO commentVO = commentDAO.selectByIdx(idx);
		return commentVO;
	}

	@Override
	public void insert(CommentVO commentVO) {
		if(commentVO!=null) {
			commentDAO.insert(commentVO);
			commentDAO.refEqalIdx();
		}
	}
	@Override // 답변 저장
	public void reply(CommentVO commentVO) {
		if(commentVO!=null) {
			// ref가 같으면서 나보다 seq가 큰값 들을 모두 seq값을 1씩 증가시킨다.
			HashMap<String, Integer> map = new HashMap<>();
			map.put("co_ref", commentVO.getCo_ref());
			map.put("co_seq", commentVO.getCo_seq());
			HashMap<String, Integer> map2 = new HashMap<>();
			map2.put("co_ref", commentVO.getCo_ref());
			map2.put("co_seq", commentVO.getCo_seq()+1);
			
			if(commentVO.getCo_seq()!=0) {
				if(!(commentDAO.selectComment(map) < (commentDAO.selectComment(map2)!=null ? commentDAO.selectComment(map2):0))) {
					commentVO.setCo_seq(commentVO.getCo_seq()+1);
					commentDAO.updateSeq(map);
				}else {
					commentVO.setCo_seq(commentVO.getCo_seq()+2);
					commentDAO.updateSeq(map2);
				}
			}else {
				commentVO.setCo_seq(commentDAO.selectMaxSeq(commentVO.getCo_ref())+1);
			}
			// lev는 +1
			commentVO.setCo_lev(commentVO.getCo_lev()+1);
			// 댓글 저장
			commentDAO.reply(commentVO);
		}
	}
	@Override
	public void update(CommentVO commentVO) {
		if(commentVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			CommentVO dbVO = commentDAO.selectByIdx(commentVO.getCo_idx());
			if(dbVO!=null) {
				commentDAO.update(commentVO);
			}
		}
	}

	@Override
	public void delete(CommentVO commentVO) {
		if(commentVO!=null) {		
			CommentVO dbVO = commentDAO.selectByIdx(commentVO.getCo_idx());
			if(dbVO!=null) {
				// 자식이 있으면 삭제표시만하고 자식이 없으면 지운다.
				HashMap<String, Integer> map = new HashMap<>();
				map.put("co_ref", dbVO.getCo_ref());
				map.put("co_seq", dbVO.getCo_seq());
				List<CommentVO> list = commentDAO.selectSeqList(map); // ref가 같으면서 seq가 크거나 같은것들 가져오기
				int childCount = 0; // 자식의 개수를 구한다.
				if(!list.isEmpty()) {
					int lev = list.get(0).getCo_lev(); // 0번의 레벨값
					for(int i=1;i<list.size();i++) {
						if(lev>=list.get(i).getCo_lev()); // 같은 레벨이 나온면 종료
						childCount++; // 자식의 개수 증가
					}
				}
				// 자식이 있으면 삭제표시만하고 자식이 없으면 지운다.
				if(childCount==0) {
					commentDAO.delete2(commentVO.getCo_idx()); // 지우기 === 이 때는 삭제표시가 바뀌지 않은 상태 'N'에서 삭제
				}else {
					commentDAO.updateDel(commentVO.getCo_idx()); // 삭제 표시
				}
//				// 전체글을 반복하면서 del값이 'Y'이면서 자식이 없는 항목은 완전 삭제를 해줘야 한다.
//				List<CommentVO> delList = commentDAO.selectDelList(); // 삭제표시된 목록
//				log.info(delList+"delList%%%%%%%4");
//				if(!delList.isEmpty()) {
//					for(CommentVO vo : delList) { // 반복
//						// 반복하는 요소의 자식의 개수
//						HashMap<String, Integer> map2 = new HashMap<>();
//						map2.put("co_ref", vo.getCo_ref());
//						map2.put("co_seq", vo.getCo_seq());
//						List<CommentVO> list2  = commentDAO.selectSeqList(map2);
//						int count = 0;
//						if(!list2.isEmpty()) {
//							int lev = list2.get(0).getCo_lev();
//							for(int i=1;i<list2.size();i++) {
//								if(lev>=list2.get(i).getCo_lev());
//								count++;
//								log.info(count+"222####4");
//							}
//						}
//						if(count==0) { // 삭제 표시가 있으면서 자식이 없다면 
//							log.info(count+"333####4");
//							commentDAO.delete1(vo.getCo_ref()); // 이미 삭제표시가 'Y'인 상태에서 삭제 쿼리 조건에 del='Y'를 포함
//						} // end if
//					}// end for
//				}// end if
			}//end if
		} // end if
	} // end method

	@Override
	public int selectCoCount(int idx) {
		int coCount = commentDAO.selectCount(idx);
		return coCount;
	}
}

