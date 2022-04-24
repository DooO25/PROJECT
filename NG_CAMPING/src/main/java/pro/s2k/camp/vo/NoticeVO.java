package pro.s2k.camp.vo;


import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {						// 공지 객체
	private String mb_nick;					// 유저 닉네임
	private int mb_idx;						// 유저 고유 번호
	private int nt_idx;						// 공지 고유 번호
	private String nt_title;				// 공지 제목
	private String nt_content;				// 공지 내용
	private Date nt_regDate;				// 공지 작성일
	private Date nt_modiDate;				// 공지 수정일
	private int nt_hit;						// 공지 조회수
	private String nt_ip;					// 작성자 ip
	private List<FileUploadVO> fileList;	// 파일 첨부 리스트
}
