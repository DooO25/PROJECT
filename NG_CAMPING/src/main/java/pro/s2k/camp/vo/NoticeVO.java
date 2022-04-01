package pro.s2k.camp.vo;


import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NoticeVO {
	private String mb_nick;
	private int nt_idx;
	private int mb_idx;
	private String nt_title;
	private String nt_content;
	private Date nt_regDate;
	private Date nt_modiDate;
	private int nt_hit;
	private String nt_ip;
	private List<FileUploadVO> fileList;
}
