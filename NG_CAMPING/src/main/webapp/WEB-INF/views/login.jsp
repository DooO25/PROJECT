<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta id="_csrf" name="_csrf" th:content="${_csrf.token}" />
<meta id="_csrf_header" name="_csrf_header"
	th:content="${_csrf.headerName}" />
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
<%-- 네이버 로그인 js--%>
	var naver_id_login = new naver_id_login("eT2NCIHgedo2uVebssZm", "localhost:8080/callback");
  	var state = naver_id_login.getUniqState();
  	naver_id_login.setButton("white", 2,40);
  	naver_id_login.setDomain("localhost:8080");
  	naver_id_login.setState(state);
  	naver_id_login.setPopup();
  	naver_id_login.init_naver_id_login();
</script>

<style type="text/css">

#banner1 {
	padding:200px;

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
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">
			<h1 id="logo">
				<a href="index.jsp"><img
					src="${pageContext.request.contextPath }/resources/assets/css/images/logo.png"
					alt="" /> </a>
			</h1>
			<nav id="nav">
				<ul>
					<li><a href="index.jsp">Home</a></li>
					<li><a href="#">캠핑장</a>
						<ul>
							<li><a href="#">일반 야영장</a></li>
							<li><a href="#">자동차 야영장</a></li>
							<li><a href="#">카라반</a></li>
							<li><a href="#">글램핑</a></li>
						</ul></li>

					<li><a href="#">캠핑톡</a>
						<ul>
							<li><a href="#">공지사항</a></li>
							<li><a href="#">캠핑후기</a></li>
							<li><a href="#">QnA</a></li>
						</ul></li>
					<li><a href="/insert.do">회원가입</a></li>
					<li><a href="/login.do">로그인</a></li>
				</ul>
			</nav>
		</header>
		<!-- Banner -->
		<section id="banner1">
			<div class="row">
				<div style="text-align: left; padding-left: 30%;">아이디/비번 로그인</div>
					<div class="col-md-8" style="padding-left: 30% ">
						<input type="text" class="form-control" id="ID" name="mb_ID"placeholder="아이디입력" required>
						<input type="text" class="form-control" id="ID" name="mb_ID"placeholder="비밀번호입력" required>
					</div>
				<div class="col-md-4">
					<input type="submit" value="로그인" style="height: 95px; float: left" />
				</div>
				<div style="font:white;  font-size: 10px; text-align:left; padding-left: 65%;"><a href="">아이디찾기</a>&nbsp; | &nbsp;<a href="">비밀번호 찾기 </a> &nbsp; | &nbsp;<a href="/insert.do">회원가입 </a></div>
			</div>
		<div class="row" style="width: 300px; height: 300px; margin: 0 auto;" >
		<div class="col-md-4">
  		<a href="/naverLogin.do"><span id="naver"><img style="width: 70%; height: 14%;"
			src="${pageContext.request.contextPath }/resources/images/naverLogin.png"
			alt="" /></span></a>
		</div>
		<div class="col-md-4">
		<a href="/kakaoLogin.do"><span class="col-md-4" id="kakao"><img style="width: 70%; height: 14%;"
			src="${pageContext.request.contextPath }/resources/images/kakaoLogin.png"
			alt="" /></span></a>
		</div>
		<div class="col-md-4">
		<a href="googleLogin.do"><span class="col-md-4" id="google"><img style="width: 70%; height: 14%;"
			src="${pageContext.request.contextPath }/resources/images/googleLogin.png"
			alt="" /></span></a>
			</div>
		</div> 
		</section>
		
		
		
		
	
		

		<!-- Footer -->
		<footer id="footer">
			<ul class="icons">
				<li><a href="https://twitter.com/?lang=ko"
					class="icon brands alt fa-twitter" target="_blank"><span
						class="label">Twitter</span></a></li>
				<li><a href="https://www.facebook.com/"
					class="icon brands alt fa-facebook-f" target="_blank"><span
						class="label">Facebook</span></a></li>
				<li><a href="https://www.instagram.com/"
					class="icon brands alt fa-instagram" target="_blank"><span
						class="label">Instagram</span></a></li>
				<li><a href="mailto:Email@email.com"
					class="icon solid alt fa-envelope"><span class="label">Email</span></a></li>
			</ul>
			<ul class="copyright">
				<li>&copy; COPYRIGHT(C) 2022. Green Academy in Sungnam.</li>
				<li>Made by 김가람, 강두오, 서해성</li>
			</ul>
		</footer>
	</div>


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