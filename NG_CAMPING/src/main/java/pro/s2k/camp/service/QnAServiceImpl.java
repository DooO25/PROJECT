package pro.s2k.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.QnADAO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;
import pro.s2k.camp.vo.ReviewVO;

@Slf4j
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
			log.info(pagingVO.getStartNo() + "#################################");
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
	public void insert(QnAVO qnaVO) {
		if(qnaVO!=null) {
			qnaDAO.insert(qnaVO);
		}
		
	}

	@Override
	public void reply(QnAVO qnaVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(QnAVO qnaVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(QnAVO qnaVO) {
		// TODO Auto-generated method stub
		
	}

}
