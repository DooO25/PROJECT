package pro.s2k.camp.vo;
/*
 *CREATE TABLE `camp_review` ( 
  `mb_idx` int(11) NOT NULL,
  rv_idx int not null auto_increment,
  ref int default 0,
  seq int default 0,
  lev int default 0,
  `mb_nick` varchar(30) NOT NULL,
  `rv_title` varchar(100) not NULL,
  `rv_content` text not NULL,
  `rv_regDate` timestamp default now(),
  `rv_modiDate` timestamp default now(),
  `rv_hit` int(11) default 0,
  del int default 1,
  PRIMARY KEY (`rv_idx`),
  CONSTRAINT `camp_review_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE
);
 */


import java.sql.Date;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVO {						// 캠핑 후기 객체
	private int mb_idx;						// 유저 고유 번호
	private int rv_idx;						// 후기 고유 번호
	private String rv_title;				// 후기 제목
	private String rv_content;				// 후기 내용
	private Date rv_regDate;				// 후기 작성일
	private Date rv_modiDate;				// 후기 수정일
	private String rv_ip;					// 후기 작성자 ip
	private int rv_hit;						// 후기 조회수
	private int del;						// 후기 삭제 여부 (1이면 삭제 default 0)
	private String mb_nick;					// 유저 닉네임
	private int coCount;                    // 댓댓글 개수 세기
}
