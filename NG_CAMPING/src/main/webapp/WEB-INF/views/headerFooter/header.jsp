<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=eae203b516d5693eb4a9560f2bb8505b"></script>
	
<script type="text/javascript">
	function logoutSubmit() {
		var logout = document.logout;
		logout.submit();
	}
	if (navigator.geolocation) {
		// GeoLocation을 이용해서 접속 위치를 얻어옵니다
		navigator.geolocation.getCurrentPosition(function(position) {
			var lat = position.coords.latitude; // 위도 mapy
			var lon = position.coords.longitude; // 경도 mapx
            document.getElementById("lon").value = lon;
            document.getElementById("lat").value = lat;
		});

	} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
		var lat = 33.450701; // 위도 mapy
		var lon = 126.570667; // 경도 mapx
        document.getElementById("lon").value = lon;
        document.getElementById("lat").value = lat;
	}
	function send(){
		document.getElementById("camp").submit();
	}
	
</script>

<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>

</head>
<body class="is-preload landing">


		<!-- Header -->
		<header id="header">
			<h1 id="logo">
				<a href="/main.do"><img
					src="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" style="height: 60px; width: 80px;"
					alt="" /> </a>
			</h1>
			<c:set value="${sessionScope.mvo.gr_role}" var="role" />
			<c:set value="${sessionScope.mvo.mb_nick}" var="nick" />
			<c:set value="${sessionScope.mvo.del}" var="del" />
			<form id="camp" action="/camp/campsite.do">
				<input type="hidden" id="lat" name="lat">
				<input type="hidden" id="lon" name="lon">
			</form>
			<nav id="nav">
				<ul>
					<c:if test="${sessionScope.mvo eq null }">
						<li><a href="/main.do">Home</a></li>
						<li><a href="#">캠핑장</a>
							<ul>
								<li><a style="cursor: pointer;" onclick="send();">캠핑장 찾기</a></li>
							</ul></li>
						<li><a href="#">캠핑톡</a>
							<ul>
								<li><a href="../board/notice.do">공지사항</a></li>
								<li><a href="../board/review.do">캠핑후기</a></li>
								<li><a href="../board/QnA.do">QnA</a></li>
							</ul></li>
						<li><a href="/insert.do">회원가입</a></li>
						<li><a href="/login.do">로그인</a></li>
					</c:if>
					<c:if test="${sessionScope.mvo ne null }">
						<c:choose>
							<c:when test="${role eq 'ROLE_USER'}">
								<li>"${nick }" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
										<li><a style="cursor: pointer;" onclick="send();">캠핑장 찾기</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="../board/notice.do">공지사항</a></li>
										<li><a href="../board/review.do">캠핑후기</a></li>
										<li><a href="../board/QnA.do">QnA</a></li>
									</ul></li>

								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li style="vertical-align: top;">
									<form
										action='${pageContext.request.contextPath } /memberInfo/memberPage.do'
										method="POST" id="rView">
										<sec:csrfInput />
										<a href="#"
											onclick="document.getElementById('rView').submit()">마이페이지</a>
									</form>
								</li>
							</c:when>
							<c:when test="${role eq 'ROLE_USER'}">
								<li>"${nick}" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
										<li><a style="cursor: pointer;" onclick="send();">캠핑장 찾기</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="../board/notice.do">공지사항</a></li>
										<li><a href="../board/review.do">캠핑후기</a></li>
										<li><a href="../board/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li style="vertical-align: top;">
									<form
										action='${pageContext.request.contextPath }/UserInfo/memberpageCorrect.do'
										method="POST" id="rView">
										<sec:csrfInput />


										<a href="#"
											onclick="document.getElementById('rView').submit()">마이페이지</a>
									</form>
								</li>
							</c:when>

							<c:when test="${role eq 'ROLE_ADMIN' }">
								<li>"${nick}" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
									<li><a style="cursor: pointer;" onclick="send();">캠핑장 찾기</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="../board/notice.do">공지사항</a></li>
										<li><a href="../board/review.do">캠핑후기</a></li>
										<li><a href="../board/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li>
                             <form action='${pageContext.request.contextPath }/admin/memberManage.do' method="POST" id="rView">
                                 <sec:csrfInput />
                                 <a href="#" onclick="document.getElementById('rView').submit()">회원관리</a>
                             </form>
                           </li>

							</c:when>
							<c:otherwise>
								<li>"${nick}"님 메일인증을 해주시길바랍니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a href="#">캠핑장</a>
									<ul>
											<li><a style="cursor: pointer;" onclick="send();">캠핑장 찾기</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="../board/notice.do">공지사항</a></li>
										<li><a href="../board/review.do">캠핑후기</a></li>
										<li><a href="../board/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li style="vertical-align: top;">
									<form
										action='${pageContext.request.contextPath }/UserInfo/memberpageCorrect.do'
										method="POST" id="rView">
										<sec:csrfInput />
										<a href="#"
											onclick="document.getElementById('rView').submit()">마이페이지</a>
									</form>
								</li>

							</c:otherwise>
						</c:choose>
					</c:if>
				</ul>
			</nav>
		</header>
		<!-- Scripts -->
		<script
			src="${pageContext.request.contextPath }/resources/assets/js/jquery.min.js"></script>
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