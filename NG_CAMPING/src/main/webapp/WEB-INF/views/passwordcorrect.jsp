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
<%-- 다음 우편번호 API --%>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">

<script type="text/javascript">

function ajaxTOsend(){
	$.ajax({
		type : "GET", 
		url : "passwordcorrectOk.do", 
		data :$('#memberVOForm').serialize(), 
		dataType : "text",
		success : function(result) {
			console.log(result)
			if (result==1) {
				alert('비밀번호 변경에 성공했습니다. 다시 로그인해주세요.');
				location.href ='/main.do'; 
			}else if(result == 0){
				alert('오류가 발생했습니다. 다시 비밀번호를 수정해주세요.');
				$("#password").val ='';
				$("#password1").val ='';	
				
				
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { // java에서 에러가 나는 경우 탐.
			alert("오류발생") 
		}
		
});
}


$(function() {
	$("#password1").keyup(function() {
		var pw = $('#password').val();
		var pw2 = $('#password1').val();

		if (pw != '' || pw2 != '') {
			if (pw == pw2) {
				$('#pwcheckval').html('Matching').css('color', 'white');
			} else {
				$('#pwcheckval').html('Not Matching').css('color', 'yellow');
			}

		}

	});

	$("#password1").focus(function() {
		if ($("#password").val() == '') {
			alert('비밀번호 입력칸을 먼저 입력해주세요.');
			$("#password").focus();
		}
		 
	});
});

function logoutSubmit() {
	var logout = document.logout;
	logout.submit();
}

function formCheck() {
	var regPass = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/;
	var value = $("#password").val();
	if (!regPass.test(value)) {
		alert('영문, 숫자, 특수기호 조합으로 8-15자리로 입력해주세요.');
		$("#password").focus();
		return false;
	}
}


</script>
<style>
#banner1 {
	padding: 100px 0;
	top: 20px;
}

#content1 {
	text-align: left;
}

#member_position {
	padding-left: 25%;
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
				<div class="mb-4">
					<form action="/passwordcorrectOk.do" method="get" name="memberVOForm" id="memberVOForm">
						<input type="hidden" name="mb_ID" value="${mvo.mb_ID }">
						<div style="width: 50%;">
							<div class="title">비밀번호 수정</div>
							<br>
							<div class="mb-4 row">
								<label for="password" class="col-sm-3 col-form-label">비밀번호</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="password"
										name="newPassword" placeholder="영어,숫자,특수문자 8~15글자" required
										maxlength="15">
								</div>
								<div class="col-sm-3 col-form-label" id="pwcheckval"></div>
							</div>

							<div class="mb-4 row">
								<label for="password" class="col-sm-3 col-form-label"
									id="pwcheck">비밀번호확인 </label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="password1"
										placeholder="비밀번호확인" required maxlength="15">
								</div>
							</div>

							<div style="text-align: right;">
									<input type="button" value="수정하기" onclick="ajaxTOsend();">
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