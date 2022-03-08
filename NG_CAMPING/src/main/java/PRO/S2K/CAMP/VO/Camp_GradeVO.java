package PRO.S2K.CAMP.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * CREATE TABLE `camp_grade` (
  `gr_grade` int(11) NOT NULL,
  `gr_name` varchar(15) not NULL,
  `gr_role` varchar(15) not NULL,
  PRIMARY KEY (`gr_grade`)
);
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Camp_GradeVO {
	private int gr_grade;
	private String gr_name;
	private String gr_role;
}
