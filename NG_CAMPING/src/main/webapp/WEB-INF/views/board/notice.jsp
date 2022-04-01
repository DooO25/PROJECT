<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	rel="stylesheet" type="text/css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

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

	function certification() { // 인증안된 회원이 글쓰기 버튼을 눌렀을때
		alert("메일인증이 필요합니다.");
		location.href = "/board/notice.do";
	}

	function loginSubmit() { // 비회원이 글쓰기를 눌렀을때
		alert("로그인 필요합니다.");
		location.href = "/board/login.do";
	}

	function noticeInsert() {
		location.href = "/board/noticeInsert.do";
	}
	
	

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
table td {
	margin: 0 auto;
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
</style>

<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>




</head>
<body class="is-preload landing">
	<!-- Footer -->
	<%@ include file="../headerFooter/header.jsp"%>

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
					<option value="/board/notice.do" selected>공지사항</option>
					<option value="/board/review.do">캠핑후기</option>
					<option value="/board/QnA.do">QnA</option>
				</select>
			</div>
		</div>
		<br> <br>
		<div>
			<p style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">공지사항</p>
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
						<c:forEach var="vo" items="${pv.list }" varStatus="num">
							<tr>
								<td style="vertical-align: middle;">
									${no }
									<c:set var="no" value="${no-1}"/>
								</td>
								<td style="padding-bottom: 60px;"> 
								<form action='<c:url value='${pageContext.request.contextPath }/board/noticeView.do'/>' method="post" id="nView${num.index }">
									<sec:csrfInput/>
									<input type="hidden" name="p" value="${pv.currentPage }"/>
									<input type="hidden" name="s" value="${pv.pageSize }"/>
									<input type="hidden" name="b" value="${pv.blockSize }"/>
									<input type="hidden" name="nt_idx" value="${vo.nt_idx }"/>
									<%--오늘 저장한 글이면 new  --%>
								</form>
								<jsp:useBean id="today" scope="request" class="java.util.Date"></jsp:useBean>				
								<fmt:formatDate value="${today }" pattern="yyyyMMdd" var="day"/> 
								<fmt:formatDate value="${vo.nt_regDate }" pattern="yyyyMMdd" var="reg"/> 
								<c:if test="${day==reg }">
									  <span style="color:red;">New</span>
								</c:if>
								<a style="cursor: pointer;" onclick="document.getElementById('nView${num.index }').submit()"><c:out value="${vo.nt_title }"></c:out></a>
							
								</td>
								<td style="padding-top: 50px;">
									${vo.nt_content }
								</td>
								<td style="vertical-align: middle;">
									${vo.mb_nick }
								</td>
								<td style="vertical-align: middle;"> 
									<fmt:formatDate value="${vo.nt_regDate }" pattern="yy-MM-dd"/>
								</td>					
								<td style="vertical-align: middle;">
									${vo.nt_hit }
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</c:if>
			</table>
			<div style="border: none;text-align: center;">
						${pv.pageList}
			</div>
	
			<c:set value="${sessionScope.mvo.gr_role}" var="role" />
         <c:if test="${role eq 'ROLE_ADMIN' }">
            <button type="button" class="btn btn-outline-secondary"
                  style="float: right;" onclick="noticeInsert()">글쓰기</button>
         </c:if>
			<br>
		</section>
		<br>

<!-- Footer -->
	<%@ include file="../headerFooter/footer.jsp"%>


	<!-- Scripts -->
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/resources/assets/js/commons.js"></script>
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