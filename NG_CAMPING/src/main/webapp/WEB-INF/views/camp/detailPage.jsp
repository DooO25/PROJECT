<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>NG캠핑</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" />
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<script type="text/javascript">
	
</script>



<style type="text/css">
</style>

<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>

</head>
<body>
	<br>
	<div>
		<div class="area"
			style="text-align: right; font-size: 15px; padding-right: 10px; font-weight: bold; padding-bottom: 3px;">
			${result.doNm } > ${result.sigunguNm }</div>
		<div class="name">
			<h1 style="color: blue; font-weight: bold; padding-left: 5px;">${result.facltNm }</h1>
			<hr
				style="border: solid 1px skyblue; margin-bottom: 4px; margin-top: 2px;">
		</div>
		<div class="row" style="padding-left: 5px;">
			<div class="col-md-8">
				<button class="btn btn-info"
					style="width: 100px; height: 35px; font-size: 14px; text-align: center;"
					onclick="location.href='/board/review.do'">캠핑후기</button>
				<button class="btn btn-info"
					style="width: 100px; height: 35px; font-size: 14px; text-align: center;"
					onclick="location.href='/board/notice.do'">공지사항</button>
				<button class="btn btn-info"
					style="width: 100px; height: 35px; font-size: 14px; text-align: center;"
					onclick="location.href='/board/QnA.do'">QnA</button>
			</div>
		</div>
	</div>
	<div style="font-size: 20px; padding-top: 10px; font-weight: bold;">
	</div>
	<div class="row">
		<div class="col-md-8">
			<input type='button' disabled='disabled' value='위도' /> ${result.mapY }
			<input type='button' disabled='disabled' value='경도' /> ${result.mapX }
		</div>
	</div>
	<br>
	<div class="pic" style="text-align: center;">
		<img src="${result.firstImageUrl }"
			style="width: 400px; height: 200px;" onerror="this.style.display='none'">
	</div>
	<div class="col-md-8"
		style="padding-top: 30px; padding-left: 3px; margin: 3px; float: left; font-weight: bold;">
		<div
			style="float: left; width: 150px; height: 40px; font-size: 15px; background-color: #828282; color: white; text-align: center; padding-top: 10px;">
			주변환경</div>
		<div
			style="float: left; width: 250px; height: 40px; font-size: 15px; background-color: #A6A6A6; color: white; text-align: center; padding-top: 10px;">
			${result.lctCl }</div>
		<div
			style="float: left; width: 150px; height: 40px; font-size: 15px; background-color: #828282; color: white; text-align: center; padding-top: 10px;">
			캠핌유형</div>

		<div
			style="float: left; width: 250px; height: 40px; font-size: 15px; background-color: #A6A6A6; color: white; text-align: center; padding-top: 10px;">
			${result.induty }</div>
	</div>
	<div style="padding-top: 71px; padding-left: 8px;">
		<div style="background-color: #CACACA; height: 200px; width: 1800px;">
			<div style="padding-top: 25px; padding-left: 10px; font-size: 15px; font-weight: bold;">
			소개 :  ${result.lineIntro } <br>
			주소 :  ${result.addr1 } <br>
			부대시설 :  ${result.sbrsCl } <br>
			부대시설 기타 :  ${result.sbrsEtc } <br>
			주변이용가능시설 :  ${result.posblFcltyCl } <br>
			테마환경 :  ${result.themaEnvrnCl } <br>
			애완견 동반 여부 :  ${result.animalCmgCl }
			    
				
			</div>
		</div>
		<div
			style="background-color: #DCDCDC; height: 50px; width: 1600px; padding-top: 10px; text-align:center; font-size: 20px;">
			<a href="${result.homepage }">해당 홈페이지로 이동</a>
		</div>
	</div>
	<div style="text-align: center; padding-top: 20px;">
		<button class="btn btn-primary" onclick="window.close();"
			style="width: 150px; height: 50px; font-weight: bold;">닫기</button>
	</div>

</body>
</html>