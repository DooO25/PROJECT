<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	rel="stylesheet">

<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<!-- 언어 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/lang/summernote-ko-KR.min.js"></script>
<script type="text/javascript">
	
</script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<script type="text/javascript">
	function logoutSubmit() {
		var logout = document.logout;
		logout.submit();
	}
	$(function() {
		$('#summernote').summernote(
				{
					lang : 'ko-KR', // default: 'en-US'
					height : 300, // set editor height
					minHeight : null, // set minimum height of editor
					maxHeight : null, // set maximum height of editor
					fontNames : [ '맑은고딕', 'Arial', 'Arial Black',
							'Comic Sans MS', 'Courier New', ],
					fontNamesIgnoreCheck : [ '맑은고딕' ],
					focus : true,
					// 이미지가 1MB를 넘을경우 수동으로 업로드를 처리하고 실행될 코드를 지정해준다.
					callbacks : {
						onImageUpload : function(files, editor, welEditable) {
							for (var i = files.length - 1; i >= 0; i--) {
								sendFile(files[i], this);
							}
						}
					}
				});
	});

	// 실제 업로드되는 서버의 파일을 Ajax로 호출해 줘야한다.
	function sendFile(file, el) {
		// 폼 작성
		var form_data = new FormData();
		form_data.append('file', file);
		// Ajax 호출
		$.ajax({
			data : form_data,
			type : "POST",
			url : 'imageUpload',
			cache : false,
			contentType : false,
			enctype : 'multipart/form-data',
			processData : false,
			success : function(img_name) { // 성공
				$(el).summernote('editor.insertImage', img_name);
			}
		});
	}
</script>

<style type="text/css">
element.style {
	width: 500px;
	height: 100px;
	margin: 0 auto;
}

#banner1 {
	padding: 200px;
	padding-top: 300px;
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
				<a href="/main.do"><img
					src="${pageContext.request.contextPath }/resources/assets/css/images/logo.png"
					alt="" /> </a>
			</h1>
			<c:set value="${sessionScope.mvo.gr_role}" var="role" />
			<c:set value="${sessionScope.mvo.mb_name}" var="name" />
			<nav id="nav">
				<ul>
					<c:if test="${sessionScope.mvo eq null }">
						<li><a href="/main.do">Home</a></li>
						<li><a href="#">캠핑장</a>
							<ul>
								<li><a href="#">일반 야영장</a></li>
								<li><a href="#">자동차 야영장</a></li>
								<li><a href="#">카라반</a></li>
								<li><a href="#">글램핑</a></li>
							</ul></li>
						<li><a href="#">캠핑톡</a>
							<ul>
								<li><a href="/notice.do">공지사항</a></li>
								<li><a href="/review.do">캠핑후기</a></li>
								<li><a href="/QnA.do">QnA</a></li>
							</ul></li>
						<li><a href="/insert.do">회원가입</a></li>
						<li><a href="/login.do">로그인</a></li>


					</c:if>
					<c:if test="${sessionScope.mvo ne null }">
						<c:choose>
							<c:when test="${role eq 'ROLE_USER' }">
								<li>"${name}" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
										<li><a href="#">일반 야영장</a></li>
										<li><a href="#">자동차 야영장</a></li>
										<li><a href="#">카라반</a></li>
										<li><a href="#">글램핑</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="/notice.do">공지사항</a></li>
										<li><a href="/review.do">캠핑후기</a></li>
										<li><a href="/QnA.do">QnA</a></li>
									</ul></li>

								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a href="/login.do">마이페이지</a></li>
							</c:when>
							<c:when test="${role eq 'ROLE_ADMIN' }">
								<li>"${name}" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
										<li><a href="#">일반 야영장</a></li>
										<li><a href="#">자동차 야영장</a></li>
										<li><a href="#">카라반</a></li>
										<li><a href="#">글램핑</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="/notice.do">공지사항</a></li>
										<li><a href="/review.do">캠핑후기</a></li>
										<li><a href="/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a href="#">관리자페이지</a></li>
							</c:when>
							<c:otherwise>
								<li>"${name}"님 메일인증을 해주시길바랍니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
										<li><a href="#">일반 야영장</a></li>
										<li><a href="#">자동차 야영장</a></li>
										<li><a href="#">카라반</a></li>
										<li><a href="#">글램핑</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="/notice.do">공지사항</a></li>
										<li><a href="/review.do">캠핑후기</a></li>
										<li><a href="/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a href="#">마이페이지</a></li>

							</c:otherwise>
						</c:choose>
					</c:if>
				</ul>
			</nav>
		</header>

		<!-- Banner -->
		<div class="col-sm-8">
			<img src="">
		</div>
		<div style="padding-top: 70px; padding-left: 10%">
			<div class="col-sm-2">
				<select name="chart" id="chart" style="float: left;">
					<option value="">캠핑장</option>
					<option value="" selected>캠핑톡</option>
				</select>

			</div>
			<div class="col-sm-2" style="float: left;">
				<select name="list" id="list" onchange="window.open(value,'_self');">
				    <option value="/notice.do" selected>공지사항</option>
					<option value="/QnA.do" >QnA</option>
					<option value="/review.do">캠핑후기</option>
				</select>
			</div>
		</div>
		<br /> <br /> <br /> <br />
		<div>
			<p style="font-size: 50px; padding-left: 12%;">공지사항</p>
		</div>

		<div style="padding-right: 10%; padding-bottom: 5%;">
			<div class="col-sm-1" style="float: right;">
				<input type="button" value="검색" onclick="search();">
			</div>


			<div class="col-sm-1.8" style="float: right;">
				<select name="search" id="search" style="float: left;">
					<option value="" selected>전체</option>
					<option value="">제목</option>
					<option value="">내용</option>
				</select>
			</div>
		</div>
		<br />
		<section
			style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
			<form action="result.do" method="post">
				<input type="text" size="60" name="subject"><br>
				<textarea id="summernote" name="note"></textarea>
				<c:set value="${sessionScope.mvo.gr_role}" var="role" />
				<c:if test="${role eq 'ROLE_ADMIN' }">
					<input type="submit" value="글쓰기"
						class="btn btn-outline-success btn-sm" style="float: right;">
				</c:if>
			</form>
		</section>
		<br />



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



</body>
</html>