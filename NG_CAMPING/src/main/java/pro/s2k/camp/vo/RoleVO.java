package pro.s2k.camp.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleVO { 		// 권한 객체
	private int gr_idx;		// 권한 부여 대상 고유 번호
	private String mb_ID;	// 권한 부여 대상 ID
	private String gr_role;	// 부여된 권한
}
