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
	function logoutSubmit() {
		var logout = document.logout;
		logout.submit();
	}
</script>
<style>

.swiper-container {
	width: 90%;
	height: 200px;
	margin: 20px auto;
}

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

	<!-- header -->
	<%@ include file="../headerFooter/header.jsp"%>

	<!-- Banner -->
		<div class="col-sm-8">
			<img src="">
		</div>
		<div style="padding-top: 70px; padding-left: 10%">
			<div class="col-sm-2">
				<select name="chart" id="chart" style="float: left;" onchange="window.open(value,'_self');">
					<option value="" selected>캠핑톡 관리</option>
					<option value="/Admin/membermanage.do">회원관리</option>
					<option value="">로그 관리</option>
				</select>

			</div>
			<div class="col-sm-2" style="float: left;">
				<select name="list" id="list" onchange="window.open(value,'_self');">
					<option value="/Admin/noticemanage" selected>공지사항</option>
					<option value="/review.do">캠핑후기</option>
					<option value="/QnA.do">QnA</option>
				</select>
			</div>
		</div>
		<br> <br>
		<div>
			<p
				style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">공지사항</p>
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
			style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
			<form action="result.do" method="post">
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
						<td>1</td>
						<td>제목이지렁</td>
						<td>이것은 테스트입니다.</td>
						<td>일이삼사오육실팔구십</td>
						<td>2022/11/08 05:22:00</td>
						<td>1</td>
					</tr>
				</table>
				<c:if test="${role eq 'ROLE_ADMIN' }">
					<button type="button" class="btn btn-outline-secondary"
						style="float: right;" onclick="noticeWriteSubmit()">글쓰기</button>
				</c:if>
			</form>
		</section>
	
	
	

<!-- Footer -->
	<%@ include file="../headerFooter/footer.jsp"%>

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