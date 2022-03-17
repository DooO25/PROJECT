package PRO.S2K.CAMP.VO;
/*
 * CREATE TABLE `camp_notice` (
  `mb_nick` varchar(30) NOT NULL,
  nt_idx int not null auto_increment ,
  `mb_idx` int(11) NOT null,
  `nt_title` varchar(100) not NULL,
  `nt_content` text not NULL,
  `nt_regDate` timestamp default now(),
  `nt_modiDate` timestamp default now(),
  `nt_hit` int(11) default 0,
  PRIMARY KEY (`nt_idx`),
  CONSTRAINT `camp_notice_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE
);
 */


import java.sql.Date;

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
}
