package pro.s2k.camp.vo;


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
	private String gr_idx;
	private int del;
}