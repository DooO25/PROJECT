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
	private int idx; // 고유넘버링
	private String firstImageUrl; // 썸네일
	private String zipcode; // 우편번호
	private String addr1; // 주소1 
	private String addr2; // 주소2
	private String facltNm; // 캠핑장이름
	private String lineIntro; // 한줄 설명
	private String intro; // 설명
	private String featureNm; // 특징
	private String lctCl; //
	private String doNm; // 도 이름
	private String sigunguNm; // 시군구
	private int allar; // 
	private int gnrlSiteCo; 
	private int autoSiteCo;
	private int glampSiteCo;
	private int caravSiteCo;
	private int indvdlCaravSiteCo;
	private String glampInnerFclty;
	private String caravInnerFclty;
	private String trlerAcmpnyAt;
	private String caravAcmpnyAt;
	private String eqpmnLendCl;
	private String sbrsCl;
	private String sbrsEtc;
	private String posblFcltyCl;
	private String posblFcltyEtc;
	private String themaEnvrnCl;
	private String animalCmgCl;
	private int toiletCo;
	private int swrmCo;
	private int wtrplCo;
	private String brazierCl;
	private int siteBottomCl1;
	private int siteBottomCl2;
	private int siteBottomCl3;
	private int siteBottomCl4;
	private int siteBottomCl5;
	private String homepage;
	private String resveUrl;
	private String tel;
	private String induty;
	private double mapX;
	private double mapY;
}
