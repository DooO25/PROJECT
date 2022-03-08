package PRO.S2K.CAMP.VO;
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

public class Camp_ReviewVO {
	private int mb_idx;
	private int rv_idx;
	private int ref;
	private int seq;
	private int lev;
	private String mb_nick;
	private String rv_title;
	private String rv_content;
	private Date rv_regDate;
	private Date rv_modiDate;
	private int rv_hit;
}
