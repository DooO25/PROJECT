package PRO.S2K.CAMP.VO;
/*

CREATE TABLE `camp_member` (
  `mb_idx` int(11) NOT NULL AUTO_INCREMENT,
  `mb_ID` varchar(12) not NULL,
  `mb_password` varchar(41) not NULL,
  `mb_name` varchar(15) not NULL,
  `mb_nick` varchar(30) NOT NULL,
  `mb_email` varchar(30) not NULL,
  `mb_tel` varchar(13) not NULL,
  `mb_birth` date not null,
  `mb_address1` varchar(200) not NULL,
  `mb_address2` varchar(300) not NULL,
  `gr_grade` int(11) NOT NULL,
  `mb_use` int(11) default 1,
  PRIMARY KEY (`mb_idx`),
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MemberVO {
	private int mb_idx;
	private String mb_ID;
	private String mb_password; 
	private String mb_name;
	private String mb_nick;
	private String mb_email;
	private String mb_tel;
	private String mb_birth;
	private String mb_zipcode;
	private String mb_address1;
	private String mb_address2;
	private int mb_use;
	private String authkey;
	private String gr_role;
	private String sns_email;

}