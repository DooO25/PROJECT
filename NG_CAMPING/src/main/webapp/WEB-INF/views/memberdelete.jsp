<%@page import="pro.s2k.camp.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" />
<title>캠핑은 NG캠핑!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<meta charset="utf-8" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<script type="text/javascript">
	
function test(){
	if (!$('#flexCheckDefault').is(":checked")) {
		alert("약관에 동의해주세요.");
		return false;
	}





</script>
<style>

.form-check {
    display: block;
    min-height: 1.5rem;
    padding-left: 5.5em;
    margin-bottom: 0.125rem;
}


#banner1 {
	padding: 100px 0;
	top: 20px;
}

#content1 {
	text-align: left;
}

textarea {
	height: 15em;
	border: none;
	resize: none;
	background-color: white;
	color: black;
}

#member_position {
	padding-left: 33%;
}
</style>

<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>
</head>
<body class="is-preload landing">


		
	<!-- header -->
	<%@ include file="header_footer/header.jsp"%>
		

	<!-- Banner -->

		<section id="banner1">
			<div id="content1">
				<div id="member_position">
					<h1 style="font-size: 30px">회원탈퇴</h1>
					<p />
					<div style="width: 50%;">
						<label for="exampleFormControlTextarea1"> NG캠핑 회원탈퇴</label>
						<textarea class="form-control" id="exampleFormControlTextarea1"
							readonly="readonly">
NG캠핑 회원 탈퇴 시 이용할 수 없는 정보입니다.

1. 캠핑톡>캠핑후기>글 작성
2. 캠핑톡>캠핑후기>댓글 및 대댓글 기능
3. 캠핑톡>캠핑후기>QnA 글 작성
4. 기타 NG캠핑에서 제공하는 회원전용 서비스

이 외에도 회원 탈퇴시 모든 개인정보는 파기되며
회원전용 서비스를 재이용하려면 가입해주시기 바랍니다.

기타 궁금한 사항은 하단에 있는 이메일 버튼으로
문의하시면 친절하게 답해드립니다.

</textarea>
					</div>
					<br>
					<div class="form-check" id="check">
						위 내용을 숙지 하였습니다. &nbsp;&nbsp;&nbsp;&nbsp; <input
							class="form-check-input" type="checkbox" value=""
							id="flexCheckDefault"> <label class="form-check-label"
							for="flexCheckDefault"> 확인. </label>
					</div>
					<br>
					<div class="mb-4">
			<form action="/memberdeleteOk.do" method="post" onsubmit="return test();">
						<input type="hidden" name="mb_ID" value="${mvo.mb_ID }">
							<div style="width: 50%;">
										<div class="mb-4 row">
									<label for="password" class="col-sm-4 col-form-label">비밀번호 입력</label>
									<div class="col-sm-6">
										<input type="password" class="form-control" id="password"
											name="mb_password" placeholder="비밀번호를 입력해주세요." required
											maxlength="15">
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
								<div style="text-align: right;">
								<input type="submit" value="회원탈퇴" >
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>

	<!-- Footer -->
	<%@ include file="header_footer/footer.jsp"%>


	<!-- Scripts -->
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/jquery.scrolly.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/jquery.dropotron.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/jquery.scrollex.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/browser.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/breakpoints.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/util.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/main.js"></script>
	<div style="position: fixed; bottom: 10px; right: 10px; z-index: 100;">
		<a href="#banner" style="text-decoration: underline;"><img
			src="${pageContext.request.contextPath }/resources/assets/css/images/top.png"></a>
	</div>
</body>
</html>