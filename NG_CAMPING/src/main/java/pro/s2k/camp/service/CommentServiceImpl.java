package pro.s2k.camp.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.CommentDAO;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.ReviewVO;

@Slf4j
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public CommentVO selectList(CommonVO commonVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CommentVO selectByIdx(int idx) {
		CommentVO commentVO = commentDAO.selectByIdx(idx);
		return null;
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
