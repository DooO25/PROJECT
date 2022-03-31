<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

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
<!-- axicon -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/axicon/axicon.min.css" />

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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/assets/js/common.js"></script>


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
	function formCheck() {
		var value = $("#replyContent").val();
		if (!value || value.trim().length == 0) {
			alert('답변을 반드시 입력해야 합니다.');
			$("#replyContent").val("");
			$("#replyContent").focus();
			return false;
		}
	}
/* 	function formCheck2(idx) {
		var value = $("#rereplyContent"+idx).val();
		if (!value || value.trim().length == 0) {
			alert('답변을 반드시 입력해야 합니다.');
			$("#rereplyContent"+idx).val("");
			$("#rereplyContent"+idx).focus();
			return false;
		}
	
	} */
	/* const url = new URL(window.location.href);
	 const urlParams = url.searchParams;
	 alert(urlParams.get('idx'));
	 alert("${rv.rv_idx}");
	 document.getelementbyid('rv_idx').value = urlParams.get('idx');  */

	function sendInsertParam() {
		$.ajax({
			type : "POST", // Post 방식으로 찾아야겠네 이거 ㅇㅇ 일단 영상은 있는데...
			url : "answerInsertOk.do", // 컨트롤러에서 대기중인 URL 주소이다.
			data : $('#answerInsert').serialize(),
			dataType : "text",

			success : function(idx) { // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
				var jsonStr = JSON.parse(idx);
				console.log(jsonStr);
				/* location.href='/reviewView.do?p='+jsonStr.p+'&s='+jsonStr.s+'&b='+jsonStr.b+'&rv_dix='+jsonStr.rv_idx; */
				document.sendData.p.value = jsonStr.p;
				document.sendData.s.value = jsonStr.s;
				document.sendData.b.value = jsonStr.b;
				document.sendData.qna_idx.value = jsonStr.qna_idx;
				document.sendData.role.value = jsonStr.role;
				document.sendData.mb_idx.value = jsonStr.mb_idx;

				document.sendData.submit();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) { // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
			}
		});
	}
	 
	function sendUpdateParam() {
		$.ajax({
			type : "POST", // Post 방식으로 찾아야겠네 이거 ㅇㅇ 일단 영상은 있는데...
			url : "answerInsertOk.do", // 컨트롤러에서 대기중인 URL 주소이다.
			data : $('#answerUpdate').serialize(),
			dataType : "text",

			success : function(idx) { // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
				var jsonStr = JSON.parse(idx);
				console.log(jsonStr);
				/* location.href='/reviewView.do?p='+jsonStr.p+'&s='+jsonStr.s+'&b='+jsonStr.b+'&rv_dix='+jsonStr.rv_idx; */
				document.sendData.p.value = jsonStr.p;
				document.sendData.s.value = jsonStr.s;
				document.sendData.b.value = jsonStr.b;
				document.sendData.qna_idx.value = jsonStr.qna_idx;
				document.sendData.role.value = jsonStr.role;
				document.sendData.mb_idx.value = jsonStr.mb_idx;

				document.sendData.submit();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) { // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
			}
		});
	}
	

		

			
	$(function() {
			$("#answerUpdate").css('display','none');
		});
	function update() {
			$("#updateBtn").click(function() {
					 if ($("#answerUpdate").css('display') == 'none') {
						$("#answerUpdate").css('display', 'inline');
					
			
				} else {
					$("#answerUpdate").css('display', 'none');
				
				} 
				/* if ($(this).parent().next().css('display') == 'inline') {
					$(this).parent().next().css('display','none');
					$(this).parent().next().slideUp(500);
				}else{
					$(this).parent().next().css('display','inline');
					$(this).parent().next().slideDown(500)
				} */
			});
	}

	alert("${qv.qna_idx}");
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
					<option value="/review.do" >캠핑후기</option>
					<option value="/QnA.do"selected>QnA</option>
				</select>
			</div>
		</div>
		<br> <br>
		<div>
			<p
				style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">QnA</p>
		</div>
	</div>


	<section style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
		<form
			action='<c:url value='${pageContext.request.contextPath }/QnAUpdate.do'/>'
			method="post" id="ruView">
			<sec:csrfInput />
			<input type="hidden" name="p" value="${cv.currentPage }" /> 
			<input type="hidden" name="s" value="${cv.pageSize }" /> 
			<input type="hidden" name="b" value="${cv.blockSize }" /> 
			<input type="hidden" name="qna_idx" value="${qv.qna_idx }" />
			<input type="hidden" name="mb_idx" value="${mvo.mb_idx }" />
				<div style="text-align: right;">
					<c:choose>
						<c:when test="${mi == mvo.mb_idx }">
							<c:if test="${qv.qna_read == 0  }">
								 <a href="#"onclick="document.getElementById('QnA').submit()">
								 <input type="submit" class="btn btn-outline-secondary btn-sm" value="수정" /></a>
							</c:if>
							<c:if test="${qv.qna_read < 2}">
								<input type="submit" formaction="/QnADeleteOk.do"
									class="btn btn-outline-secondary btn-sm" value="삭제" />
							</c:if>
								<input type="button" onclick="location.href='/QnA.do'"
									class="btn btn-outline-secondary btn-sm" value="목록" />
						</c:when>
						<c:otherwise>
								<input type="button" onclick="location.href='/QnA.do'"
									class="btn btn-outline-secondary btn-sm" value="목록" />
						</c:otherwise>
					</c:choose>
				</div>
		</form>

		<br>
		<table class="table" style="border: 1px solid white;">
			<thead class="thead-dark">
				<tr>
					<th>
						<div
							style="text-align: left; font-size: 20px; font-weight: bold; padding-bottom: 1%; padding-left: 2%; padding-right: 2%;">
							${qv.qna_title }</div>
						<div class="row" style="padding-left: 2%">
							<div class="col-4" style="text-align: left; font-size: 15px;">닉네임
								: ${qv.mb_nick }</div>
							<div class="col-3" style="text-align: left; font-size: 15px;">
								등록일 :
								<fmt:formatDate value="${qv.qna_modiDate }"
									pattern="yyyy년 MM월 dd일 HH:mm:ss" />
							</div>
							<div class="col-4" style="text-align: right; font-size: 15px;">관리자
									<c:choose>
											<c:when test="${qv.qna_read==0 }">
												<div>미확인</div>
											</c:when>
											<c:when test="${qv.qna_read==1 }">
												<div>확인중</div>
											</c:when>
											<c:when test="${qv.qna_read==2 }">
												<div>답변완</div>
											</c:when>
									</c:choose>
							</div>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="color: white; padding-left: 3%;">${qv.qna_content }</td>
				</tr>
			</tbody>
		</table>
						
							</section>
	<br />
	<!-- 글 목록이 표시되어야 한다. -->
	<section style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
				
				<c:if test="${qv2.qna_ref==null }">
					<div style="border: 1px solid gray; text-align: center;">
						등록된	답변이 없습니다.
					</div>
				</c:if>
			<c:if test="${qv2.qna_ref!=null }">
	<table class="table" style="border: 1px solid white;">
			<thead class="thead-dark">
				<tr>
					<th>
						<div class="row" style="padding-left: 2%">
							<div class="col-4" style="text-align: left; font-size: 15px;">닉네임
								: ${qv2.mb_nick }</div>
							<div class="col-3" style="text-align: left; font-size: 15px;">
								등록일 :
								<fmt:formatDate value="${qv2.qna_modiDate }"
									pattern="yyyy년 MM월 dd일 HH:mm:ss" />
							</div>
					
							</div>
					</th>
				</tr>
			</thead>
				<tbody>
				<tr>
					<td style="color: white; padding-left: 3%;">${qv2.qna_content }</td>
				</tr>
			</tbody>
		</table>				
			</c:if>
	</section>
			
	
	
	 
	
	<br />
	<c:if test="${role eq 'ROLE_ADMIN' }">
		<%-- 답변글 은 3 --%>
		<c:if test="${qv2.qna_read != 3 }">
		<div>
			<p style="font-size: 20px; text-align: center; font-weight: bold;">관리자 답변</p>
		</div>
		<form action="${pageContext.request.contextPath}/answerInsertOk.do"
			 method="post" id="answerInsert">
			<sec:csrfInput />
			<input type="hidden" name="mb_idx" value="${mvo.mb_idx }" />
			<input type="hidden" name="qna_idx" value="${qv.qna_idx }" />
			<input type="hidden" name="qna_title" value="답변" />
			<input type="hidden" name="role" value="${mvo.gr_role }" />
			<section
				style="padding-right: 10%; padding-left: 10%; margin: 0 auto; padding-bottom: 2%">
	          <textarea id="content" name="qna_content" style="height:250px;color:black; background-color: white;" required="required">안녕하십니까, ${mvo.mb_nick } 입니다.</textarea>
			</section>
			<div style="text-align: center; padding-bottom: 3%">
				<input type="button" 
					class="btn btn-outline-secondary btn-sm" style="margin: auto;" onclick="sendInsertParam();"
					value="답변달기">
			</div>
		</form>
		</c:if>
		<c:if test="${qv2.qna_read == 3 }">
		<div style="text-align: center; padding-bottom: 3%">
				<input type="submit"  id="updateBtn"
					class="btn btn-outline-secondary btn-sm" style="margin: auto;"  onclick="update();"
					value="수정하기">
		</div>
			<form id="answerUpdate" action="${pageContext.request.contextPath}/answerUpdateOk.do"
				 method="post" id="answerUpdate">
				<sec:csrfInput />
				<input type="hidden" name="mb_idx" value="${mvo.mb_idx }" />
				<input type="hidden" name="qna_idx" value="${qv.qna_idx }" />
					<input type="hidden" name="qna_title" value="답변" />
					<input type="hidden" name="role" value="${mvo.gr_role }" />
				<section
					style="padding-right: 10%; padding-left: 10%; margin: 0 auto; padding-bottom: 2%">
		          <textarea id="content" name="qna_content" style="height:250px;color:black; background-color: white;" required="required">안녕하십니까, ${mvo.mb_nick } 입니다.</textarea>
				</section>
				<div style="text-align: center; padding-bottom: 3%">
					<input type="button" 
						class="btn btn-outline-secondary btn-sm" style="margin: auto;"  onclick="sendUpdateParam();"
						value="수정">
				</div>
			</form>
		</c:if>
	</c:if>
	<%-- 실제적으로 갈 jsp --%>
	<form action="${pageContext.request.contextPath}/QnA.do"
		method="post" id="sendData" name="sendData">
		<sec:csrfInput />
		<input type="hidden" name="p" value="${cv.currentPage }" /> <input
			type="hidden" name="s" value="${cv.pageSize }" /> <input
			type="hidden" name="b" value="${cv.blockSize }" /> <input
			type="hidden" name="qna_idx" value="${qv.qna_idx }" /> <input
			type="hidden" name="role" value="${mvo.gr_role }" />
			<input type="hidden" name="mb_idx" value="${mvo.mb_idx }" />
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