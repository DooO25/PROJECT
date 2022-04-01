package pro.s2k.camp.service;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.NoticeVO;
import pro.s2k.camp.vo.PagingVO;

public interface NoticeService {
	PagingVO<NoticeVO> selectList(CommonVO commonVO);
	
	NoticeVO selectByIdx(int up_idx);
	
	void insert(NoticeVO noticeVO);

	void update(NoticeVO noticeVO, String path, String[] deleteFile);
	
	void delete(NoticeVO noticeVO, String path);
}
