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
	location.href = "/board/review.do";
}

function loginSubmit() { // 비회원이 글쓰기를 눌렀을때
	alert("로그인 필요합니다.");
	location.href = "/login.do";
}

function reviewInsert() { //관리자, 회원이 글쓰기를 눌렀을때
	location.href = "/board/reviewInsert.do";
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
					<option value="/board/review.do" selected>캠핑후기</option>
					<option value="/board/notice.do">공지사항</option>
					<option value="/board/QnA.do">QnA</option>
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
									<td style="vertical-align: middle;">
										${no }
										<c:set var="no" value="${no-1}"/>
									</td>
									
									<td style="padding-bottom: 60px;">

										<form action='<c:url value='${pageContext.request.contextPath }/board/reviewView.do'/>' method="post" id="rView${vs.index }">
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
									<td style="padding-top: 50px;">
										${vo.rv_content }
									</td>
									<td style="vertical-align: middle;">
										${vo.mb_nick }
										</td>
									
									<td style="vertical-align: middle;"> 
										<fmt:formatDate value="${vo.rv_regDate }" pattern="yy-MM-dd"/>
									</td>					
									<td style="vertical-align: middle;">
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
	<%@ include file="../headerFooter/footer.jsp"%>


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