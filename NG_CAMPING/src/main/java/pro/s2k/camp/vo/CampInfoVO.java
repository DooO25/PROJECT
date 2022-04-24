package pro.s2k.camp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* CREATE TABLE `camp_info` (
`idx` int(11) NOT NULL AUTO_INCREMENT,
`firstImageUrl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`zipcode` char(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`addr1` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`addr2` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`facltNm` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`lineIntro` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`intro` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`featureNm` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`lctCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`doNm` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`sigunguNm` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`allar` int(11) DEFAULT NULL,
`gnrlSiteCo` int(11) DEFAULT NULL,
`autoSiteCo` int(11) DEFAULT NULL,
`glampSiteCo` int(11) DEFAULT NULL,
`caravSiteCo` int(11) DEFAULT NULL,
`indvdlCaravSiteCo` int(11) DEFAULT NULL,
`glampInnerFclty` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`caravInnerFclty` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`trlerAcmpnyAt` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`caravAcmpnyAt` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`eqpmnLendCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`sbrsCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`sbrsEtc` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`posblFcltyCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`posblFcltyEtc` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`themaEnvrnCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`animalCmgCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`toiletCo` int(11) DEFAULT NULL,
`swrmCo` int(11) DEFAULT NULL,
`wtrplCo` int(11) DEFAULT NULL,
`brazierCl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`siteBottomCl1` int(11) DEFAULT NULL,
`siteBottomCl2` int(11) DEFAULT NULL,
`siteBottomCl3` int(11) DEFAULT NULL,
`siteBottomCl4` int(11) DEFAULT NULL,
`siteBottomCl5` int(11) DEFAULT NULL,
`homepage` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`resveUrl` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`tel` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`induty` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`mapX` double DEFAULT NULL,
`mapY` double DEFAULT NULL,
PRIMARY KEY (`idx`)
) */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampInfoVO {
	private int idx; // 정보 고유 번호
	private String firstImageUrl; // 썸네일
	private String zipcode; // 우편번호
	private String addr1; // 주소1 
	private String addr2; // 주소2
	private String facltNm; // 캠핑장이름
	private String lineIntro; // 한줄 설명
	private String intro; // 소개
	private String featureNm; // 특징
	private String lctCl; // 입지 구분
	private String doNm; // 도 이름
	private String sigunguNm; // 시군구
	private int allar; // 전체 면적
	private int gnrlSiteCo; // 야영장 개수
	private int autoSiteCo;	// 주요 시설 자동 야영장
	private int glampSiteCo; // 글램핑 개수
	private int caravSiteCo; // 카라반 개수
	private int indvdlCaravSiteCo; // 개인 카라반
	private String glampInnerFclty; // 글램핑 내부 시설 개수
	private String caravInnerFclty;	// 카라반 내부 시설 개수
	private String trlerAcmpnyAt;	// 트레일러 동반 여부
	private String caravAcmpnyAt;	// 개인 카라반 동반 여부
	private String eqpmnLendCl;		// 캠핑 장비 대여
	private String sbrsCl;			// 부대시설
	private String sbrsEtc;			// 부대시설기타
	private String posblFcltyCl;	// 주변 이용 가능 시설
	private String posblFcltyEtc;	// 주빈 이용 가능 시설 기타
	private String themaEnvrnCl;	// 테마 환경
	private String animalCmgCl;		// 애완동물 동반 여부
	private int toiletCo;			// 화장실 개수
	private int swrmCo;				// 샤워실 개수
	private int wtrplCo;			// 개수대 개수
	private String brazierCl;		// 화로대 개수
	private int siteBottomCl1;		// 잔디
	private int siteBottomCl2;		// 파쇄석
	private int siteBottomCl3;		// 테크
	private int siteBottomCl4;		// 자갈
	private int siteBottomCl5;		// 맨흙
	private String homepage;		// 홈페이지
	private String resveUrl;		// 예약 주소
	private String tel;				// 전화번호
	private String induty;			// 업종
	private double mapX;			// 경도
	private double mapY;			// 위도
}
