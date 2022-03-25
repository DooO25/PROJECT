package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.CommentDAO;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;

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
			pagingVO = new PagingVO<>(totalCount);
//			 글을 읽어오기
			List<CommentVO> list = commentDAO.selectList(idx);
//			// 해당글들의 첨부파일 정보를 넣어준다.
//			if(list!=null && list.size()>0) {
//				for(ReviewVO vo : list) {
//					// 해당글의 첨부파일 목록을 가져온다.
//					List<FileUploadVO> fileList =  fileUploadDAO.selectList(vo.getRv_idx());
//					// vo에 넣는다.
//					vo.setFileList(fileList);
//				}
//			}
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
			// ref가 같으면서 나보다 seq가 큰값들을 모두 seq 값을 1씩 증가한다.
			HashMap<String, Integer> map = new HashMap<>();
			map.put("ref", commentVO.getRv_idx());
			map.put("seq", commentVO.getCo_seq());
			commentDAO.updateSeq(map);
			// ref는 그대로
			// seq는 +1
			commentVO.setCo_seq(commentVO.getCo_seq()+1);
			// lev는 +1
			commentVO.setCo_lev(commentVO.getCo_lev()+1);
			// 댓글 저장
			commentDAO.insert(commentVO);
		}
	}

	@Override
	public void update(CommentVO commentVO, String[] delFiles, String realPath) {
		if(commentVO!=null) {
			// DB에서 해당 글번호의 글 읽어온다.
			CommentVO dbVO = commentDAO.selectByIdx(commentVO.getRv_idx());
			if(dbVO!=null) {
				commentDAO.update(commentVO);
			}
		}
	}

	@Override
	public void delete(CommentVO commentVO, String uploadPath) {
		if(commentVO!=null) {		
			CommentVO dbVO = commentDAO.selectByIdx(commentVO.getRv_idx());
			if(dbVO!=null) {
				commentDAO.delete2(dbVO.getCo_idx());
			}
		}
	}





}
