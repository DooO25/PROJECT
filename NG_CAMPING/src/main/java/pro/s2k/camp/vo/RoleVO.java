package pro.s2k.camp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
CREATE TABLE `camp_role` (
  `gr_idx` int(11) NOT null AUTO_INCREMENT primary key,
   `mb_ID` varchar(12) not NULL,
  `gr_role` varchar(15) not NULL
);
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleVO {
	private int gr_idx;
	private String mb_ID;
	private String gr_role;
}
