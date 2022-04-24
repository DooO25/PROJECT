<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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


<!-- 서머노트를 위해 추가해야할 부분 -->
<script
	src="${pageContext.request.contextPath}/resources/summernote/summernote-lite.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/summernote/lang/summernote-ko-KR.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/summernote/summernote-lite.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<script type="text/javascript">
	function logoutSubmit() {
		var logout = document.logout;
		logout.submit();
	}
	function sendFile(file, el) {
		var form_data = new FormData();
		var csrf_token = "${_csrf.token}";
      	form_data.append('file', file);
      	$.ajax({
        	data: form_data,
        	type: "POST",
        	url: '/imageUpload.do',
        	cache: false,
        	contentType: false,
        	enctype: 'multipart/form-data',
        	processData: false,
        	beforeSend: function(xhr){
        		xhr.setRequestHeader("X-CSRF-TOKEN", csrf_token);
        	},
        	success: function(img_name) {
          		$(el).summernote('editor.insertImage', img_name);
        	},
        	error : function(request, status, error){
        		alert('에러!!!');
        		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	}
      	});
    }
	//-----------------------------------------------------------------------------------------------------------
	
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
.note-editor.note-frame .note-editing-area .note-editable, .note-editor.note-airframe .note-editing-area .note-editable
	{
	padding: 10px;
	background: white;
	overflow: auto;
	word-wrap: break-word;
}

b {
	font-weight: bold;
}
</style>
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
						<li><a style="cursor: pointer;">캠핑장</a>
							<ul>
								<li><a style="cursor: pointer;">일반 야영장</a></li>
								<li><a style="cursor: pointer;">자동차 야영장</a></li>
								<li><a style="cursor: pointer;">카라반</a></li>
								<li><a style="cursor: pointer;">글램핑</a></li>
							</ul></li>
						<li><a style="cursor: pointer;">캠핑톡</a>
							<ul>
								<li><a href="/board/notice.do">공지사항</a></li>
								<li><a href="/baord/review.do">캠핑후기</a></li>
								<li><a href="/board/QnA.do">QnA</a></li>
							</ul></li>
						<li><a href="/insert.do">회원가입</a></li>
						<li><a href="/login.do">로그인</a></li>


					</c:if>
					<c:if test="${sessionScope.mvo ne null }">
						<c:choose>
							<c:when test="${role eq 'ROLE_USER' }">
								<li>"${name}" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a style="cursor: pointer;">캠핑장</a>
									<ul>
										<li><a style="cursor: pointer;">일반 야영장</a></li>
										<li><a style="cursor: pointer;">자동차 야영장</a></li>
										<li><a style="cursor: pointer;">카라반</a></li>
										<li><a style="cursor: pointer;">글램핑</a></li>
									</ul></li>
								<li><a style="cursor: pointer;">캠핑톡</a>
									<ul>
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
									</ul></li>

								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<sec:csrfInput/> <a style="cursor: pointer;"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a href="/login.do">마이페이지</a></li>
							</c:when>
							<c:when test="${role eq 'ROLE_ADMIN' }">
								<li>"${name}" 님 환영합니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a style="cursor: pointer;">캠핑장</a>
									<ul>
										<li><a style="cursor: pointer;">일반 야영장</a></li>
										<li><a style="cursor: pointer;">자동차 야영장</a></li>
										<li><a style="cursor: pointer;">카라반</a></li>
										<li><a style="cursor: pointer;">글램핑</a></li>
									</ul></li>
								<li><a style="cursor: pointer;">캠핑톡</a>
									<ul>
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<sec:csrfInput/> <a style="cursor: pointer;"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a style="cursor: pointer;">관리자페이지</a></li>
							</c:when>
							<c:otherwise>
								<li>"${name}"님 메일인증을 해주시길바랍니다.</li>
								<li><a href="/main.do">Home</a></li>
								<li><a style="cursor: pointer;">캠핑장</a>
									<ul>
										<li><a style="cursor: pointer;">일반 야영장</a></li>
										<li><a style="cursor: pointer;">자동차 야영장</a></li>
										<li><a style="cursor: pointer;">카라반</a></li>
										<li><a style="cursor: pointer;">글램핑</a></li>
									</ul></li>
								<li><a style="cursor: pointer;">캠핑톡</a>
									<ul>
										<li><a href="/board/notice.do">공지사항</a></li>
										<li><a href="/board/review.do">캠핑후기</a></li>
										<li><a href="/board/QnA.do">QnA</a></li>
									</ul></li>
								<c:url value="/logout" var="logoutURL" />
								<li>
									<form action="${logoutURL }" method="post" name="logout">
										<sec:csrfInput/> <a style="cursor: pointer;"
											onclick="logoutSubmit()">로그아웃</a>
									</form>
								</li>
								<li><a style="cursor: pointer;">마이페이지</a></li>
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
					<option value="/board/QnA.do">QnA</option>
				</select>
			</div>
		</div>
		<br> <br>
		<div>
			<p style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">공지사항</p>
		</div>

		<br>
		<section
			style="padding-right: 10%; padding-left: 10%; margin: 0 auto;">
			<form enctype="multipart/form-data" method="post" onsubmit="return formCheck();">
				<sec:csrfInput/>
				<input type="hidden" name="mb_idx" value="${sessionScope.mvo.mb_idx }" />
				<input type="hidden" name="mb_nick" value="${sessionScope.mvo.mb_nick }"/>
				<div class="row">
					<div class="col-sm-7">
						<label for="ID" style="font-size: 20px; font-weight: bold;">제목</label> 
						<input type="text" id="title" name="nt_title" style="background-color: white; color: black;">
						<br>
					</div>
				</div>
				<textarea id="content" name="nt_content" class="summernote"></textarea>
				<div>첨부파일</div>
				<div> 
					<span style="color:red;font-size: 9pt;">※ 이미지는 내용에 직접 첨부하세요!!!</span>
					<div id="fileBox">
						<div id="fileItem" class="fileItem"><input type="file" name="uploadFile" multiple="multiple"/></div>
					</div>
					<br />
				</div>
				<c:if test="${role eq 'ROLE_ADMIN' }">
					<div style="padding-top: 1%; float: right;">
						<input value="목록" class="btn btn-dark btn-sm" type="button" style="margin-right: 2px;" onclick="location.href='/board/notice.do'"> 
						<input type="submit" value="전송" class="btn btn-primary btn-sm" formaction="/board/noticeInsertOk.do">
					</div>
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
	<script>
		$(function(){
			$('.summernote').summernote(
				{
				// 에디터 높이
				height : 150,
				// 에디터 한글 설정
				lang : "ko-KR",
				placeholder: "입력하여 주세요",
				disableDragAndDrop : true,
				// 에디터에 커서 이동 (input창의 autofocus라고 생각하시면 됩니다.)
				focus : true,
				toolbar : [
						// 글꼴 설정
						[ 'fontname', [ 'fontname' ] ],
						// 글자 크기 설정
						[ 'fontsize', [ 'fontsize' ] ],
						// 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
						[
								'style',
								[ 'bold', 'italic', 'underline',
										'strikethrough', 'clear' ] ],
						// 글자색
						[ 'color', [ 'forecolor', 'color' ] ],
						// 표만들기
						[ 'table', [ 'table' ] ],
						// 글머리 기호, 번호매기기, 문단정렬
						[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
						// 줄간격
						[ 'height', [ 'height' ] ],
						// 그림첨부, 링크만들기, 동영상첨부
						[ 'insert', [ 'picture', 'link', 'video' ] ],
						// 코드보기, 확대해서보기, 도움말
						[ 'view', [ 'codeview', 'fullscreen', 'help' ] ] ],
				// 추가한 글꼴
				fontNames : [ 'Arial', 'Arial Black', 'Comic Sans MS',
						'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋음체',
						'바탕체' ],
				// 추가한 폰트사이즈
				fontSizes : [ '8', '9', '10', '11', '12', '14', '16', '18',
						'20', '22', '24', '28', '30', '36', '50', '72' ],
				callbacks : {
					onImageUpload : function(files, editor, welEditable) {
						for (var i = files.length - 1; i >= 0; i--) {
							sendFile(files[i], this);
						}
					}
				}
			});
		});
	</script>
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
