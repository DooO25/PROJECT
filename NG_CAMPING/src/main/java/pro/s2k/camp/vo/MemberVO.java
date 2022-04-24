package pro.s2k.camp.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {					// 유저정보 객체
	private int mb_idx;					// 유저 고유 번호
	private String mb_ID;				// 유저 ID
	private String mb_password; 		// 유저 비밀번호
	private String mb_name;				// 유저 이름
	private String mb_nick;				// 유저 닉네임
	private String mb_email;			// 유저 이메일
	private String mb_tel;				// 유저 전화번호
	private String mb_birth;			// 유저 생일
	private String mb_zipcode;			// 유저 우편번호
	private String mb_address1;			// 유저 주소
	private String mb_address2;			// 유저 상세주소
	private int mb_use;					// 사용 가능 여부
	private String authkey;				// 이메일 인증에 발급 될 인증키
	private String gr_role;				// 유저 권한
	private int del;					// 유저 탈퇴 여부
	private String socialID;			// 소셜로그인으로 가입하여 각 사에서 제공된 고유 ID
	private int socialNumber;			// 어떤 소셜 로그인을 시도 하였는지 검증하기 위한 고유 번호 (1 = 네이버, 2 = 카카오, 3 = 구글)
}