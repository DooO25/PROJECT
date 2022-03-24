<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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

	// 폼의 값 유효성 검사하기 스크립트
	function formCheck(){
		var value = $("#replyContent").val();
		if(!value || value.trim().length==0){
			alert('답변을 반드시 입력해야 합니다.');
			$("#replyContent").val("");
			$("#replyContent").focus();
			return false; 
		}
	}
	const url = new URL(window.location.href);
	   const urlParams = url.searchParams;
	   
	   alert(urlParams.get('idx'));
	   
	   
	   alert("#rv_idx").val();
	   document.getElementById("rv_idx").value = urlParams.get('idx');
	  
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
					<option value="/notice.do">공지사항</option>
					<option value="/review.do" selected>캠핑후기</option>
					<option value="/QnA.do">QnA</option>
				</select>
			</div>
		</div>
		<br> <br>
		<div>
			<p
				style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">캠핑후기</p>
		</div>


	</div>
	<section style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
			<br>
			<table class="table" style="border: 1px solid white;">
				<thead class="thead-dark">
					<tr>
						<th>
							<div
								style="text-align: left; font-size: 20px; font-weight: bold; padding-bottom: 1%; padding-left: 2%;padding-right: 2%;">
								 ${rv.rv_title }</div>
							<div class="row" style="padding-left: 2%">
								<div class="col-4" style="text-align: left; font-size: 15px;">닉네임 : ${rv.mb_nick }</div>
								<div class="col-3" style="text-align: left; font-size: 15px;">등록일 : 
								<fmt:formatDate value="${rv.rv_modiDate }" pattern="yyyy년 MM월 dd일 HH:mm:ss"/>
									</div>
								<div class="col-4" style="text-align: right; font-size: 15px; ">조회수 : ${rv.rv_hit }
									</div>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="color: white; padding-left: 3%;">${rv.rv_content }</td>
					</tr>
				</tbody>
			</table>
			<c:choose>
			<c:when test="${role eq 'ROLE_ADMIN' }">
			<div style="text-align: center;">
				<button type="button" class="btn btn-outline-secondary btn-sm"
					style="margin: auto; margin-right: 1%;" onclick="/review.do">목록으로</button>
				<button type="button" class="btn btn-outline-secondary btn-sm"
					style="margin: auto; " onclick="/reviewUpdate.do">수정하기</button>
			</div>
			</c:when>
			<c:otherwise>
				<div style="text-align: center;">
				<button type="button" class="btn btn-outline-secondary btn-sm"
					style="margin: auto;" onclick="/review.do">목록으로</button>
				</div>
			</c:otherwise>
			</c:choose>
	</section>
	<br />
	

	<!-- 글 목록이 표시되어야 한다. -->
	<section style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
		<c:if test="${empty cm.list }">
			<div style="border: 1px solid gray; text-align: center;">등록된 댓글이 없습니다.</div>
		</c:if>
	</section>
		<c:if test="${not empty cm.list }">
			<c:forEach var="vo" items="${cm.list }" varStatus="vs">
				<div style="paddingleft:${cm.lev}%;">
				<!-- 삭제표시가 되어 있으면 삭제표시된 글이라고 표시한다. -->
				<c:if test="${cm.del==0 }">
					<div class="title" onclick="return false;" style="background-color: gray;color: red;">삭제된 글입니다.</div>
				</c:if>
				<!-- 삭제표시가 되어 있지 않으면 보여준다. -->
				<c:if test="${cm.del==1 }">
					<!-- 단계별로 타이틀 색상 변경 -->
					<div class="title" style="background-color: ${cm.lev==0 ? 'skyblue':cm.lev==1 ? 'orange':cm.lev==2 ? 'pink':'silver' }">
						<c:out value="${cm.mb_nick }"/>님이 ${vo.rv_ip }에서 
						<fmt:formatDate value="${cm.rv_regDate }" pattern="yyyy년 MM월 dd일(E) HH:mm:ss"/>에서 남긴글
						
						<!-- 삭제표시를 달아보자 -->
						<button class="delForm btn btn-outline-success btn-sm">삭제</button>
						<span style="display:none;" onclick="return false;" >
							<input type="button" value="삭제확인" onclick="deleteOk(${cm.rv_idx})" class="btn btn-outline-danger btn-sm"/>		
							<input type="button" value="삭제취소" onclick="deleteCancel(this)" class="btn btn-outline-danger btn-sm"/>		
						</span>
						<!-- 아이콘에 접기/펼치기 구현 -->
						<i class="axi axi-ion-chevron-down" style="font-size:10pt;">펼치기</i>
						
					</div>
					<div class="content">
						<div>
						<!-- 여기에 글의 내용을 출력한다. -->
						<c:set var="content" value="${cm.content }"/>
						<!-- 태그 무시 -->
						<c:set var="content" value="${fn:replace(content,'<','&lt;') }"/>
						<!-- \n을 <br>로 변경 -->
						<c:set var="content" value="${fn:replace(content, newLine, br ) }"/>
						${cm_content }
						</div>
					
						<div style="text-align: right;">
							<button class="btn btn-outline-danger btn-sm updateForm">수정하기</button>
							<button class="btn btn-outline-danger btn-sm replyForm">답변달기</button>
						</div>
						<div class="reply">
							<form action="reply" method="post">
									<input type="hidden" name="ref" value="${cm.ref }"/>
									<input type="hidden" name="seq" value="${cm.seq }"/>
									<input type="hidden" name="lev" value="${cm.lev }"/>
									<input type="text" name="name" id="name" required="required"
										placeholder="이름입력"> 
									<input type="password" name="password"
										id="password" required="required" placeholder="비번입력"> <br />
									<textarea style="margin-top: 10px;" rows="3" cols="40"
										name="content" id="content" required="required" placeholder="내용입력"></textarea>
									<br /> 
									<input type="submit" value="답변쓰기"
										class="btn btn-outline-success btn-sm" /> 
									<input type="reset"
										value="다시쓰기" class="btn btn-outline-success btn-sm" /> 
									<input type="button" class="btn btn-outline-success btn-sm" value="취소" />
							</form>
						</div>
						<div class="reply">
							<form action="update" method="post">
									<input type="hidden" name="idx" value="${cm.co_idx }"/>
									<input type="text" name="name" id="name" readonly="readonly"
										value="${cm.mb_nick }"> 
									<input type="password" name="password"
										id="password" required="required" placeholder="비번입력"> <br />
									<textarea style="margin-top: 10px;" rows="3" cols="40"
										name="content" id="content" required="required" placeholder="내용입력">${cm.rv_content }</textarea>
									<br /> 
									<input type="submit" value="글수정"
										class="btn btn-outline-success btn-sm" /> 
									<input type="reset"
										value="다시쓰기" class="btn btn-outline-success btn-sm" /> 
									<input type="button" class="btn btn-outline-success btn-sm" value="취소" />
							</form>
						</div>
					</div>
				</c:if>
				</div>
			</c:forEach>
		</c:if>

<br /><br />
	<div>
	
			<p
				style="font-size: 20px; text-align:center; font-weight: bold;">답변작성</p>
		</div>
	<form action="${pageContext.request.contextPath}/replyInsertOk.do" onsubmit="return formCheck();">
		<%-- 페이지번호, 페이지 크기, 블록크기를 숨겨서 넘긴다.  --%>
					<input type="hidden" name="p"  value="${cv.currentPage }"/>
					<input type="hidden" name="s"  value="${cv.pageSize }"/>
					<input type="hidden" name="b"  value="${cv.blockSize }"/>
<%-- 					<input type="hidden" name="rv_idx"  value="${sesstionScope.mvo.rv_idx }"/> --%>
					<input type="hidden" id="rv_idx" name="rv_idx" value=""/>
				
		<section style="padding-right: 10%; padding-left: 10%; margin: 0 auto; padding-bottom: 2%">
			<textarea id="replyContent" style="background-color: white; color: black;"></textarea>
		</section>
			<div style="text-align: center; padding-bottom: 3%">
				<input type="submit" class="btn btn-outline-secondary btn-sm"
					style="margin: auto;" value="답변달기">
				</div>
		</form>




             	





	
	
	
	
	
	
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