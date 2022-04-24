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
	public PagingVO<CommentVO> selectList(int idx) {		// 댓글 목록 리스트
		PagingVO<CommentVO> pagingVO = null;
		try {
			// 1개글에서 총 개수 구하기
			int totalCount = commentDAO.selectCount(idx);	// commentMapper selectCount 쿼리 실행
			pagingVO = new PagingVO<>(totalCount);	// 리스트를 담기위한 객체 선언
			//글을 읽어오기
			List<CommentVO> list = commentDAO.selectList(idx);	//commentMapper SelectList 쿼리 실행
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
	
	@Override
	public CommentVO selectByIdx(int idx) {		// 특정 댓글
		CommentVO commentVO = commentDAO.selectByIdx(idx);	// commentMapper selectByIdx 쿼리 실행
		return commentVO;
	}

	@Override
	public void insert(CommentVO commentVO) { // 댓글 작성
		if(commentVO!=null) {
			commentDAO.insert(commentVO);	// commentMapper insert 쿼리 실행
			commentDAO.refEqalIdx();		// commentMapper refEqalIdx 쿼리 실행
		}
	}
	@Override // 대댓글 저장
	public void reply(CommentVO commentVO) {
		if(commentVO!=null) {
			HashMap<String, Integer> map = new HashMap<>();
			map.put("co_ref", commentVO.getCo_ref());
			map.put("co_seq", commentVO.getCo_seq());
			HashMap<String, Integer> map2 = new HashMap<>();
			map2.put("co_ref", commentVO.getCo_ref());
			map2.put("co_seq", commentVO.getCo_seq()+1);
			// 댓글이 아니면 (대댓글 이면)
			if(commentVO.getCo_seq()!=0) {
				// ref가 같은것중         seq와        seq+1이 null이 아닐때 seq+1의 레벨이 좌항보다 높지 않다면,        
				if(!(commentDAO.selectComment(map) < (commentDAO.selectComment(map2)!=null ? commentDAO.selectComment(map2):0))) {
					// vo에 seq +1 해주고,
					commentVO.setCo_seq(commentVO.getCo_seq()+1);
					// ref가 같으면서 나보다 seq가 큰값 들을 모두 seq값을 1씩 증가시킨다.
					commentDAO.updateSeq(map);
				}else {
					// vo에 seq +2 해주고,
					commentVO.setCo_seq(commentVO.getCo_seq()+2);
					// ref가 같으면서 나보다 seq+2보다 큰값 들을 모두 seq값을 1씩 증가시킨다.
					commentDAO.updateSeq(map2);
				}
			}else {
				// 댓글이면 같은 ref중 가장 큰 seq +1 해준다
				commentVO.setCo_seq(commentDAO.selectMaxSeq(commentVO.getCo_ref())+1);
			}
			// lev는 +1
			commentVO.setCo_lev(commentVO.getCo_lev()+1);
			// 대댓글 저장
			commentDAO.reply(commentVO);
		}
	}
	@Override
	public void update(CommentVO commentVO) { // 댓글(대댓글) 수정
		if(commentVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			CommentVO dbVO = commentDAO.selectByIdx(commentVO.getCo_idx()); // commentMapper selectByIdx 쿼리 실행
			if(dbVO!=null) {
				commentDAO.update(commentVO);
			}
		}
	}

	@Override
	public void delete(CommentVO commentVO) {
		if(commentVO!=null) {		
			CommentVO dbVO = commentDAO.selectByIdx(commentVO.getCo_idx()); // commentMapper selectByIdx 쿼리 실행
			if(dbVO!=null) {
				// 자식이 있으면 삭제표시만하고 자식이 없으면 지운다.
				HashMap<String, Integer> map = new HashMap<>();
				map.put("co_ref", dbVO.getCo_ref());
				map.put("co_seq", dbVO.getCo_seq());
				List<CommentVO> list = commentDAO.selectSeqList(map); // ref가 같으면서 seq가 크거나 같은것들 가져오기
				log.info("#####"+list);
				int childCount = 0; // 자식의 개수를 구한다.
				if(!list.isEmpty()) {
					int lev = list.get(0).getCo_lev(); // 0번의 레벨값
					for(int i=1;i<list.size();i++) {
						if(lev>=list.get(i).getCo_lev()); // 같은 레벨이 나온면 종료
						childCount++; // 자식의 개수 증가
					}
				}
				log.info("#####"+childCount);
				// 자식이 있으면 삭제표시만하고 자식이 없으면 지운다.
				if(childCount==0) {
					commentDAO.delete2(commentVO.getCo_idx()); // 지우기 === 이 때는 삭제표시가 바뀌지 않은 상태 '0'에서 삭제
				}else {
					commentDAO.updateDel(commentVO.getCo_idx()); // commentMapper updateDel 쿼리 실행(del = 1)
				}
			}//end if
		} // end if
	} // end method
}

