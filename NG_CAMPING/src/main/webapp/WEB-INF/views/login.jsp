<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" />
<title>NG캠핑 로그인</title>
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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<script type="text/javascript">
function login(){
	var ID = $("#ID").val();
	var pw = $("#password").val();
	alert(ID);
	alert(pw);
}

</script>

<style type="text/css">


element.style {
    width: 500px;
    height: 100px;
    margin: 0 auto;
}

#banner1 {
	padding:200px;
	padding-top:300px;
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
	padding-left: 27%;
}

#login {
	padding-left: 27%;
	width: 1000px;
	height: 1000px;
	
}

</style>

<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>




</head>
<body class="is-preload landing">
<%@ include file="headerFooter/header.jsp"%>
		
		<!-- Banner -->
		<section id="banner1">
			<form action="${pageContext.request.contextPath}/login" method="post">
				<%-- 로그인 실패시 에러메세지 출력 --%>
				<c:if test="${not empty error }">
					<div style="color: red;">${error }</div>
				</c:if>
				<%-- 로그아웃시 메세지 출력 --%>
				<c:if test="${not empty msg }">
					<div style="color: green;">${msg }</div>
				</c:if>
			<div class="row">
				<div style="text-align: left; padding-left: 30%;">아이디/비번 로그인</div>
					<div class="col-md-8" style="padding-left: 30% ">
						<input type="text" class="form-control" id="ID" name="mb_ID"placeholder="아이디입력" required>
						<input type="password" class="form-control" id="password" name="mb_password"placeholder="비밀번호입력" required>
					</div>
					
					<!-- 시큐리트에서 사용자가 지정한 폼을 사용하려면 반드시 아래의 코드를 첨부해줘야 한다.-->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				 
					<div class="col-md-4">
						<input type="submit" value="로그인" style="height: 95px; float: left;"/>
					</div>
				<!-- 
					<div class="col-md-4">
						<input type="button" value="로그인" style="height: 95px; float: left;" onclick="login()"/>
					</div>
				 -->
				
				<div style="font:white;  font-size: 10px; text-align:left; padding-left: 65%;">
					<a href="/userInfo/findUserId.do">아이디찾기</a>
					&nbsp; | &nbsp;
					<a href="/userInfo/findPassword.do">비밀번호 찾기 </a>
					&nbsp; | &nbsp;
					<a href="/insert.do">회원가입 </a></div>
	
	
	
		<div class="row" style="width: 300px; height: 300px; margin: 0 auto; padding-right:3;" >
		<div class="col-md-4">
  		<a href="/naverLogin.do"><span id="naver"><img style="width: 50px; height: 50px;"
			src="${pageContext.request.contextPath }/resources/images/naverLogin.png"
			alt="" /></span></a>
		</div>
		<div class="col-md-4">
		<a href="/kakaoLogin.do"><span class="col-md-4" id="kakao"><img style="width: 50px; height: 50px;"
			src="${pageContext.request.contextPath }/resources/images/kakaoLogin.png"
			alt="" /></span></a>
		</div>
		<div class="col-md-4">
		<a href="googleLogin.do"><span class="col-md-4" id="google"><img style="width: 50px; height: 50px;"
			src="${pageContext.request.contextPath }/resources/images/googleLogin.png"
			alt="" /></span></a>
			</div>
			</div>
		</div> 
		</form>
		</section>
		

		<!-- Footer -->
		<%@ include file="headerFooter/footer.jsp"%>


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
		
	<!-- 네이버 로그인API  -->
	<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</body>
</html>