package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.CommentDAO;
import pro.s2k.camp.dao.FileUploadDAO;
import pro.s2k.camp.dao.ReviewDAO;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.FileUploadVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;


@Slf4j
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDAO reviewDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private FileUploadDAO fileUploadDAO;


	@Override
	public PagingVO<ReviewVO> selectList(CommonVO commVO) {
		log.info("{}의 selectList 호출 : {}", this.getClass().getName(), commVO);
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
		log.info("{}의 selectByIdx 호출 : {}", this.getClass().getName(), idx);
		ReviewVO reviewVO = reviewDAO.selectByIdx(idx); // 글 1개를 가져온다.
		reviewVO.setRv_hit(reviewVO.getRv_hit()+1);
		reviewDAO.updateHit(idx);
		return reviewVO;
	}
	
	@Override
	public void insert(ReviewVO reviewVO) {
		log.info("{}의 insert 호출 : {}", this.getClass().getName(), reviewVO);
		// 컨트롤러에 있는 파일을 저장하는 부분을 유틸리티 클래스로 빼주면 컨트롤러가 간단해 진다.
		if(reviewVO!=null) {
			// 1. 글을 저장한다.
			log.info(reviewVO.getRv_title()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			reviewDAO.insert(reviewVO);
			// VO에 닉네임 저장하기
			// 저장을 했으면 저장된 idx값을 얻어온다
			int ref = reviewDAO.selectSeq();
			// 2. 첨부 파일의 정보도 저장해 주어야 한다.
			List<FileUploadVO> list = reviewVO.getFileList();
			if(list!=null && list.size()>0) {
				for(FileUploadVO vo : list) {
					vo.setRef(ref); // 원본글번호
					fileUploadDAO.insert(vo);
				}
			}
		}
	}

//	@Override
//	public void reply(ReviewVO reviewVO) {
//		if(reviewVO!=null) {
//			// ref가 같으면서 나보다 seq가 큰값들을 모두 seq 값을 1씩 증가한다.
//			HashMap<String, Integer> map = new HashMap<>();
//			map.put("ref", reviewVO.getRef());
//			map.put("seq", reviewVO.getSeq());
//			reviewDAO.updateSeq(map);
//			// ref는 그대로
//			// seq는 +1
//			reviewVO.setSeq(reviewVO.getSeq()+1);
//			// lev는 +1
//			reviewVO.setLev(reviewVO.getLev()+1);
//			// 댓글 저장
//			reviewDAO.reply(reviewVO);
//		}
//	}

	@Override
	public void update(ReviewVO reviewVO) {
		if(reviewVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			ReviewVO dbVO = reviewDAO.selectByIdx(reviewVO.getRv_idx());
			// 글이 있고, 비번이 같으면 수정한다.
			if(dbVO!=null) {
				// 1. 글수정
				reviewDAO.update(reviewVO);

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
			ReviewVO dbVO = reviewDAO.selectByIdx(reviewVO.getRv_idx()); 
			List<CommentVO> coVO = commentDAO.selectList(reviewVO.getRv_idx());
			// 글이 없으면 삭제한다.
			if(coVO==null) {
				reviewDAO.delete1(reviewVO.getRv_idx());
//			}else {
//				
//				// 자식이 있으면 삭제표시만하고 자식이 없으면 지운다.
//				HashMap<String, Integer> map = new HashMap<>();
//				map.put("co_ref", coVO.getCo_ref());
//				map.put("co_seq", coVO.getCo_seq());
//				List<CommentVO> list = commentDAO.selectSeqList(map); // ref가 같으면서 seq가 크거나 같은것들 가져오기
//				
//				int childCount = 0; // 자식의 개수를 구한다.
//				if(list!=null) {
//					int lev = list.get(0).getCo_lev(); // 0번의 레벨값
//					for(int i=1;i<list.size();i++) {
//						if(lev>list.get(i).getCo_lev()); // 같은 레벨이 나온면 종료
//						childCount++; // 자식의 개수 증가
//					
//					}
//				}
//				// 자식이 있으면 삭제표시만하고 자식이 없으면 지운다.
//				if(childCount==0) {
//					reviewDAO.delete2(reviewVO.getRv_idx()); // 지우기 === 이 때는 삭제표시가 바뀌지 않은 상태 '1'에서 삭제
//					}
				}else {
					reviewDAO.updateDel(reviewVO.getRv_idx()); // 삭제 표시
				}
			}
				
//				// 전체글을 반복하면서 del값이 '1'이면서 자식이 없는 항목은 완전 삭제를 해줘야 한다.
//				List<ReviewVO> delList = commentDAO.selectDelList(); // 삭제표시된 목록
//				if(delList!=null) {
//					for(ReviewVO vo : delList) { // 반복
//						// 반복하는 요소의 자식의 개수
//						HashMap<String, Integer> map2 = new HashMap<>();
//						map2.put("ref", vo.getRef());
//						map2.put("seq", vo.getSeq());
//						List<ReviewVO> list2  = commentDAO.selectSeqList(map2);
//						int count = 0;
//						if(list2!=null) {
//							int lev = list2.get(0).getLev();
//							for(int i=1;i<list2.size();i++) {
//								if(lev>=list2.get(i).getLev()) break;
//								count++;
//							}
//						}
//						if(count==0) { // 삭제 표시가 있으면서 자식이 없다면 
//							reviewDAO.delete1(vo.getRv_idx()); // 이미 삭제표시가 '0'인 상태에서 삭제 쿼리 조건에 del='0'를 포함
//						} // end if
//					}// end for
//				}// end if
//			}//end if
		} // end if
	} // end method





