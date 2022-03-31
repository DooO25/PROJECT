<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" />
<title>NG캠핑</title>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">

<script type="text/javascript">
function logoutSubmit() {
	var logout = document.logout;
	logout.submit();
}
function certification() { // 인증안된 회원이 글쓰기 버튼을 눌렀을때
	alert("메일인증이 필요합니다.");
	location.href = "/review.do";
}

function loginSubmit() { // 비회원이 글쓰기를 눌렀을때
	alert("로그인 필요합니다.");
	location.href = "/login.do";
}

function reviewInsert() { //관리자, 회원이 글쓰기를 눌렀을때
	location.href = "/reviewInsert.do";
}


/* alert("${pv.list}"); */
</script>




<style type="text/css">

element.style {
	width: 500px;
	height: 100px;
	margin: 0 auto;
}

.table-hover>tbody>tr:hover>* { -
	-bs-table-accent-bg: var(- -bs-table-hover-bg);
	color: var(- -bs-table-hover-color);
}

.table-hover>tbody>tr:hover>* { -
	-bs-table-accent-bg: var(- -bs-table-hover-bg);
	color: var(- -bs-table-hover-color);
}

.table>:not(caption)>*>* {
	padding: 0.5rem 0.5rem;
	background-color: var(- -bs-table-bg);
	border-bottom-width: 1px;
	box-shadow: inset 0 0 0 9999px var(- -bs-table-accent-bg);
}

.table>:not(caption)>*>* {
	padding: 0.5rem 0.5rem;
	background-color: var(- -bs-table-bg);
	border-bottom-width: 1px;
	box-shadow: inset 0 0 0 9999px var(- -bs-table-accent-bg);
}

table th {
	color: #ffffff;
	font-size: 0.9em;
	font-weight: 300;
	padding: 0 0.75em 0.75em 0.75em;
	text-align: center;
}

table {
	text-align: center;
}

.th-1 {
	width: 40px;
}

.th-2 {
	width: 80px;
}

.th-3 {
	width: 150px;
}

.th-4 {
	width: 90px;
}

.th-5 {
	width: 60px;
}

.th-6 {
	width: 40px;
}

#writebutton {
	padding-bottom: 2%;
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
					<option value="/review.do" selected>캠핑후기</option>
					<option value="/notice.do">공지사항</option>
					<option value="/QnA.do">QnA</option>
				</select>
			</div>
		</div>
		<br> <br>
		<div>
			<p
				style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">캠핑후기</p>
		</div>
		<div style="padding-right: 10%; padding-bottom: 3%;">
			<div class="col-sm-1" style="float: right;">
				<input type="button" value="검색" onclick="search();">
			</div>

			<div class="col-sm-2" style="float: right;">
				<input type="text" />
			</div>

			<div class="col-sm-1.8" style="float: right;">
				<select name="search" id="search" style="float: left;">
					<option value="" selected>전체</option>
					<option value="">제목</option>
					<option value="">내용</option>
				</select>
			</div>
		</div>
		<section
			style="padding-right: 10%; padding-left: 10%; padding-bottom:3%; margin: 0 auto;">
			${pv.pageInfo}
				<br>
				<table class="table table-hover table-dark">
					<tr>
						<th class="th-1" scope="col">글번호</th>
						<th class="th-2" scope="col">제목</th>
						<th class="th-3" scope="col">내용</th>
						<th class="th-4" scope="col">닉네임</th>
						<th class="th-5" scope="col">작성일</th>
						<th class="th-6" scope="col">조회수</th>
					</tr>
					<tr>
						<c:if test="${pv.totalCount==0 }">
							<td colspan="6">등록된 글이 없습니다.</td>
						</c:if>
					</tr>
					<c:if test="${pv.totalCount>0 }">
						<c:if test="${not empty pv.list }">
							<c:set var="no" value="${pv.totalCount - (pv.currentPage-1)*pv.pageSize}"/>
							<c:forEach var="vo" items="${pv.list }" varStatus="vs" >
								<c:if test="${vo.del == 1 }">
								<tr>
									<td>
										${no }
										<c:set var="no" value="${no-1}"/>
									</td>
									
									<td>

										<form action='<c:url value='${pageContext.request.contextPath }/reviewView.do'/>' method="post" id="rView${vs.index }">
				                    	     <sec:csrfInput/>
					                           <input type="hidden" name="p" value="${pv.currentPage }"/>
					                           <input type="hidden" name="s" value="${pv.pageSize }"/>
					                           <input type="hidden" name="b" value="${pv.blockSize }"/>
					                           <input type="hidden" name="rv_idx" value="${vo.rv_idx }"/>
					                           <input type="hidden" name="mode" value="1"/>
					                           <input type="hidden" name="h" value="1"/>
				                        
				                        </form>
				                        <%--  <c:forEach var="vo2" items="${pv2.list }">  --%>
				                        	<a href="#"   onclick="document.getElementById('rView${vs.index}').submit()"><c:out value="${vo.rv_title }"/></a><%-- <c:out value="${vo2.totalCount }"/> --%>
										<%--  </c:forEach>  --%>
									</td>
									<td>
										${vo.rv_content }
									</td>
									<td>
										${vo.mb_nick }
										</td>
									
									<td> 
										<fmt:formatDate value="${vo.rv_regDate }" pattern="yy-MM-dd"/>
									</td>					
									<td>
										${vo.rv_hit }
									</td>
								</tr>
							</c:if>
								</c:forEach>
						</c:if>
						</c:if>
					</table>
							<div style="border: none;text-align: center;">
								${pv.pageList}
							</div>


				<c:set value="${sessionScope.mvo.gr_role}" var="role" />
			<c:choose>
					<c:when test="${role eq 'ROLE_ADMIN' }">
					<button type="button" class="btn btn-outline-secondary"
							style="float: right;" onclick="reviewInsert()">글쓰기</button>
					</c:when>
					<c:when test="${role eq 'ROLE_USER' }">
						<button type="button" class="btn btn-outline-secondary"
							style="float: right;" onclick="reviewInsert()">글쓰기</button>

					</c:when>
					<c:when test="${role eq 'ROLE_GUEST' }">
						<button type="button" class="btn btn-outline-secondary"
							style="float: right;" onclick="certification()">글쓰기</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-outline-secondary"
							style="float: right;" onclick="loginSubmit()">글쓰기</button>
					</c:otherwise>
				</c:choose>
			<br>
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
	<script 
		src="${pageContext.request.contextPath}/resources/assets/js/common.js"></script>



</body>
</html>