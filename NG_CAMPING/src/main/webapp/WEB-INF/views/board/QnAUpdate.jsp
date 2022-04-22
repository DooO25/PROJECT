<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="ko">
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
		var value = $("#title").val();
		if(!value || value.trim().length==0){
			alert('제목을 반드시 입력해야 합니다.');
			$("#title").val("");
			$("#title").focus();
			return false; 
		}
	
		var value = $("#content").summernote('code');
		// alert("값 : " + value);
		if(!value || value.trim()=="<p><br></p>"){
			alert('내용은 반드시 입력해야 합니다.');
			$("#content").val("");
			$("#content").focus();
			return false; 
		}
		return true;
	}
	function sendUpdateParam() {
		$.ajax({
			type : "POST", // Post 방식으로 찾아야겠네 이거 ㅇㅇ 일단 영상은 있는데...
			url : "board/QnAUpdateOk.do", // 컨트롤러에서 대기중인 URL 주소이다.
			data :$('#qnaView').serialize(),
			dataType : "text",
			
			success : function(idx) { // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
				var jsonStr = JSON.parse(idx); console.log(jsonStr);
				document.sendData.p.value = jsonStr.p;
				document.sendData.s.value = jsonStr.s;
				document.sendData.b.value = jsonStr.b;
				document.sendData.qna_idx.value = jsonStr.qna_idx;
				document.sendData.role.value = jsonStr.role;
				document.sendData.submit(); 
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) { // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
			}
		});
}
	function change(val) {
		if (val.value == 'fst') {
			document.getElementById("selectfst").style.display = 'inline';
			document.getElementById("selectsnd").style.display = 'none';
		} else if (val.value == 'snd') {
			document.getElementById("selectfst").style.display = 'none';
			document.getElementById("selectsnd").style.display = 'inline';
		}
	}
</script>


<style type="text/css">

p {
    margin: 0 0 1em 0;
}

b {
  	 font-weight: bold;
}
</style>
</head>
<body class="is-preload landing">



		<!-- Header -->
		<header id="header">
			<h1 id="logo">
				<a href="/main.do"><img
					src="${pageContext.request.contextPath }/resources/assets/css/images/logo.png"
					alt="" /> </a>
			</h1>
			<c:set value="${sessionScope.mvo.gr_role}" var="role" />
			<c:set value="${sessionScope.mvo.mb_nick}" var="nick" />
			<c:set value="${sessionScope.mvo.del}" var="del" />
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
								<li><a href="/board/notice.do">공지사항</a></li>
								<li><a href="/board/review.do">캠핑후기</a></li>
								<li><a href="/board/QnA.do">QnA</a></li>
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
										<li><a href="#">일반 야영장</a></li>
										<li><a href="#">자동차 야영장</a></li>
										<li><a href="#">카라반</a></li>
										<li><a href="#">글램핑</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
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
										<li><a href="#">일반 야영장</a></li>
										<li><a href="#">자동차 야영장</a></li>
										<li><a href="#">카라반</a></li>
										<li><a href="#">글램핑</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
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
										<li><a href="#">일반 야영장</a></li>
										<li><a href="#">자동차 야영장</a></li>
										<li><a href="#">카라반</a></li>
										<li><a href="#">글램핑</a></li>
									</ul></li>
								<li><a href="#">캠핑톡</a>
									<ul>
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }"> <a href="#"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a href="#">관리자페이지</a>
									<ul>
										<li><a href="/admin/memberManage.do">회원관리</a></li>
										<li><a href="#">캠핑톡 관리</a></li>
										<li><a href="#">로그관리</a></li>
									</ul></li>
							</c:when>
							<c:otherwise>
								<li>"${nick}"님 메일인증을 해주시길바랍니다.</li>
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
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
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

      <!-- Banner -->
      <div class="col-sm-8">
         <img src="">
      </div>
      <div style="padding-top: 70px; padding-left: 10%">
			<div class="col-sm-2">
				<select name="chart" id="chart" style="float: left;"
					onchange="change(this)">
					<option value="fst" >캠핑장</option>
					<option value="snd" selected>캠핑톡</option>
				</select>
			</div>
			<div id="selectfst" class="col-sm-2" style="float: left; display: none;">
				<select name="list" id="list" onchange="window.open(value,'_self');">
					<option selected disabled>-선택-</option>
					<option value="../camp/campsite.do" >캠핑장 찾기</option>
				</select>
			</div>
			<div id="selectsnd" class="col-sm-2"
				style="float: left; ">
				<select name="list" id="list" onchange="window.open(value,'_self');">
					<option selected disabled>-선택-</option>
					<option value="/board/review.do" >캠핑후기</option>
					<option value="/board/notice.do" >공지사항</option>
					<option value="/board/QnA.do" >QnA</option>
				</select>
			</div>
		</div>
      <br> <br>
      <div>
         <p
            style="font-size: 50px; padding-left: 12%; padding-top: 3%; font-weight: bold;">QnA</p>
      </div>
   
      <br >
      <section
         style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
         <form action="${pageContext.request.contextPath}/board/QnAUpdateOk.do" method="post" id="qnaView" onsubmit="return formCheck();">
             	<%-- 페이지번호, 페이지 크기, 블록크기를 숨겨서 넘긴다.  --%>
             	<sec:csrfInput/>	
					<input type="hidden" name="p"  value="${cv.pageSize }"/>
					<input type="hidden" name="s"  value="${cv.pageSize }"/>
					<input type="hidden" name="b"  value="${cv.blockSize }"/>
					<input type="hidden" name="qna_idx"  value="${qv.qna_idx }"/>

				
					<div class="row">
							<div class="col-sm-7">
			            	    <label for="ID"  style="font-size: 20px;font-weight: bold;"> 제목</label>
			            		<input type="text" id="title" name="qna_title" style="background-color: white; color: black;" value="${qv.qna_title }"><br>
							</div>
						</div>
		  <label for="ID"  style="font-size: 20px;font-weight: bold;"> 질문</label>
          <textarea id="content" name="qna_content" style="height:250px;color:black; background-color: white;">${qv.qna_content }</textarea>


         </form>
           <div style="padding-top: 1%; float: right;">
         <input type="button" value="수정" class="btn btn-primary btn-sm" onclick="sendUpdateParam();">
         </div>
      </section>
      <br />
	<%-- 실제적으로 갈 jsp --%>
	<form action='<c:url value='${pageContext.request.contextPath }/board/QnAView.do'/>' method="post" id="sendData" name="sendData">
       	    <sec:csrfInput/>
               <input type="hidden" name="p" value="${cv.currentPage }"/>
               <input type="hidden" name="s" value="${cv.pageSize }"/>
               <input type="hidden" name="b" value="${cv.blockSize }"/>
               <input type="hidden" name="qna_idx" value="${qv.qna_idx }"/>
               <input type="hidden" name="role" value="${mvo.gr_role}"/>
               
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