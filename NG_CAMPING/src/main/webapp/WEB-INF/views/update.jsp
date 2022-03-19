<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%-- 다음 우편번호 API --%>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<script type="text/javascript">
	$(function() {
		$("#password1").keyup(function() {
			var pw = $('#password').val();
			var pw2 = $('#password1').val();

			if (pw != '' || pw2 != '') {
				if (pw == pw2) {
					$('#pwcheckval').html('Matching').css('color', 'white');
				} else {
					$('#pwcheckval').html('Not Matching').css('color', 'yellow');
				}

			}

		});

		$("#password1").focus(function() {
			if ($("#password").val() == '') {
				alert('비밀번호 입력칸을 먼저 입력해주세요.');
				$("#password").focus();
			}
		});
	});

	function daumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				var addr = ''; // 주소 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('zipcode').value = data.zonecode;
				document.getElementById("address").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.getElementById("address2").focus();
			}
		}).open();
	}
	// 폼검증하는 자바스크립트 함수
	function formCheck() {
		var tel = /^[0-9]{11}$/;
		var num = /^[0-9]{4}$/;
		var han = /^[가-힣]{3,4}$/;
		var eng = /^[a-zA-Z]$/;
		var regPass = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/;
		var emailcheck = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+)$/;

		var thisDate = new Date();
		var thisYear = thisDate.getFullYear();

		var value = $("#birthYear").val();
		if (!num.test(value)) {
			alert('년도는 숫자만 입력해주세요. 공백불가');
			$("#birthYear").focus();
			return false;
		} else if (value <= '1900') {
			alert('1900년도 이상으로 입력해주세요.');
			$("#birthYear").focus();
			return false;
		} else if (thisYear - value <= 18) {
			alert('18세 이상만 회원가입이 가능합니다.');
			$("#birthYear").focus();
			return false;
		}

		var value = $("#password").val();
		if (!regPass.test(value)) {
			alert('영문, 숫자, 특수기호 조합으로 8-15자리로 입력해주세요.');
			$("#password").focus();
			return false;
		}

		var value = $("#email").val();
		if (!emailcheck.test(value)) {
			alert('이메일 형식을 제대로 적어주세요 @포함');
			$("#email").focus();
			return false;
		}
		
		var value = $("#hp").val();
		if (!tel.test(value)) {
			alert('전화번호는 -제외한 11글자만 입력해주세요.');
			$("#hp").focus();
			return false;
		}

		var value = $("#username").val();
		if (!han.test(value)) {
			alert('이름은 한글만 입력해주세요. 3~4글자만 가능. 공백불가');
			$("#username").focus();
			return false;
		}

		if (!$('#flexCheckDefault').is(":checked")) {
			alert("약관에 동의해주세요.");
			return false;
		}

		if (!idCheckYn) { // 처음에 false / 중복이여도 false
			alert('ID 중복확인을 해주세요. ');
			return false; // 
		}

		if (!FnNickcheck) { // 처음에 false / 중복이여도 false
			alert('닉네임 중복확인을 해주세요.');
			return false; // 
		}

		var value = $("#month").val();
		if (value === '') {
			alert('태어난 월을 선택해주세요.');
			$("#month").val("");
			$("#month").focus();
			return false;
		}

		var value = $("#day").val();
		if (value === '') {
			alert('태어난 날짜를 선택해주세요.');
			$("#day").val("");
			$("#day").focus();
			return false;
		}

		var year = $('#birthYear').val() + $('#month').val() + $('#day').val();

		$('#mb_birth').val(year);

	}

	// 아이디 체크여부
	var idCheckYn = false; // 전역변수여서 어디든 사용 가능.

	function FnIdcheck() {
		var check = /^[a-zA-Z0-9]{6,12}$/;
		var value = $('#ID').val();
		if (value != null && check.test(value)) {
			$.ajax({
				type : "GET", 
				url : "idCheck.do", // 컨트롤러에서 대기중인 URL 주소이다.
				data : {
					"userid" : value
				},
				dataType : "text",
				success : function(count) { // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
					// 보니깐 count로 할거같던데
					if (count == 0) { // 있으면
						alert("ID 사용가능");
						idCheckYn = true; // 아이디 중복처리를 했다는걸 여기서 true값을 줌.
					} else if (count > 0) { // 0 이면 없음
						alert("ID 중복 또는 공백문제입니다. 특수문자도 제외");
						idCheckYn = false; // 중복된걸 통과시킬순없음 X

					} else { // 이건 오류 (밑에도 타겠지만 값이 안넘오는 경우에 여기서 걸릴거임.)
						alert("기타 오류입니다.");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) { // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
					alert("회원가입 실패")
				}
			});
		} else {
			alert("이름은 6글자이상 12자 이하만 입력되며 공백 및 특수문자가 불가능합니다.");
		}
	}

	function FnNickcheck() {
		var check = /^[가-힣a-zA-Z0-9]{2,10}$/;
		var value = $('#nick').val();
		if (value != null && check.test(value)) {
			$.ajax({
				type : "GET", // Post 방식으로 찾아야겠네 이거 ㅇㅇ 일단 영상은 있는데...
				url : "nickCheck.do", // 컨트롤러에서 대기중인 URL 주소이다.
				data : {
					"nick" : value
				},
				dataType : "text",
				success : function(count1) { // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
					// 보니깐 count로 할거같던데
					if (count1 == 0) { // 있으면
						alert("닉네임 사용가능");
						idCheckYn = true; // 아이디 중복처리를 했다는걸 여기서 true값을 줌.
					} else if (count1 > 0) { // 0 이면 없음
						alert("닉네임 중복 또는 공백문제입니다.");
						idCheckYn = false; // 중복된걸 통과시킬순없음 X

					} else { // 이건 오류 (밑에도 타겠지만 값이 안넘오는 경우에 여기서 걸릴거임.)
						alert("기타 오류입니다.");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) { // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
					alert("가입 실패")
				}
			});
		} else {
			alert("닉네임은 2글자이상 10자 이하만 입력되며 공백 및 특수문자가 불가능합니다.");
		}
	}
</script>

<style type="text/css">
#banner1 {
	padding: 100px 0;
	top: 20px;
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
	padding-left: 33%;
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
			<nav id="nav">
				<ul>
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
							<li><a href="#">공지사항</a></li>
							<li><a href="#">캠핑후기</a></li>
							<li><a href="#">QnA</a></li>
						</ul></li>
					<c:choose>
						<c:when test="${memberVO.mb_use == '1' && roleVO.gr_role == 'user'}">
							<li><a href="/logout.do">로그아웃</a></li>	
							<li><a href="/update.do">정보수정</a></li>
						</c:when>
						<c:when test="${memberVO.mb_use == '1' && roleVO.gr_role == 'admin'}">
							<li><a href="/logout.do">로그아웃</a></li>	
							<li><a href="/what.do">관리페이지</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="/insert.do">회원가입</a></li>
							<li><a href="/login.do">로그인</a></li>							
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</header>
		<!-- Banner -->

		<section id="banner1">
			<div id="content1">
				<div id="member_position">
					<h1 style="font-size: 30px">회원가입</h1>
					<p />
					<br>
					<div class="mb-4">
						<form action="/insertOk.do" method="post"
							onsubmit="return formCheck();">
							<div style="width: 50%;">
								<div class="title">회원정보수정</div>
								<br>
								<div class="mb-4 row">
									<label for="ID" class="col-sm-3 col-form-label"> 아이디</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" id="ID" name="mb_ID"
											placeholder="아이디입력" readonly value="${memberVO.mb_ID }">
									</div>
								</div>

								<div class="mb-4 row">
									<label for="password" class="col-sm-3 col-form-label">비밀번호</label>
									<div class="col-sm-5">
										<input type="password" class="form-control" id="password"
											name="mb_password" placeholder="영어,숫자,특수문자 8~15글자" required
											maxlength="15">
									</div>
									<div class="col-sm-3 col-form-label" id="pwcheckval"></div>
								</div>

								<div class="mb-4 row">
									<label for="password" class="col-sm-3 col-form-label"
										id="pwcheck">비밀번호확인 </label>
									<div class="col-sm-5">
										<input type="password" class="form-control" id="password1"
											placeholder="비밀번호확인" required maxlength="15">
									</div>
								</div>

								<div class="mb-4 row">
									<label for="username" class="col-sm-3 col-form-label">
										이름</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" id="username"
											name="mb_name" placeholder="이름 입력" readonly value="${memberVO.mb_name }">
									</div>
								</div>

								<div class="mb-4 row">
									<label for="nick" class="col-sm-3 col-form-label"> 닉네임</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" id="nick"
											name="mb_nick" value="${memberVO.mb_nick }" required maxlength="10">
									</div>
									<div class="col-sm-3">
										<input type="button" id="nickcheck" value="중복확인"
											onclick="FnNickcheck();" />
									</div>
								</div>


								<div class="mb-4 row">
									<label for="email" class="col-sm-3 col-form-label"> 이메일</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" id="email"
											name="mb_email" value="${memberVO.mb_email }" required>
									</div>
								</div>

								<div class="mb-4 row">
									<label for="hp" class="col-sm-3 col-form-label">전화번호</label>
									<div class="col-sm-5">
										<input type="text" id="hp" name="mb_tel" placeholder="-미포함"
											value="${memberVO.mb_tel }" required maxlength="11">
									</div>
								</div>

								<div class="mb-4 row">
									<label for="birth" class="col-sm-3 col-form-label">
										생년월일</label>
									<div class="col-sm-5">
										<input type="text" id="birth" name="mb_birth"
											readonly value="${memberVO.mb_birth }">
									</div>
								</div>

								<div class="col mb-3">
									주소 &nbsp;&nbsp;&nbsp; <input type="button" class="btn-check"
										id="zipCodebtn" onclick="daumPostcode();"> <label
										class="btn btn-outline-primary" for="zipCodebtn">찾기</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="zipcode"
											name="mb_zipcode" value="${memberVO.mb_zipcode }" required readonly
											style="background-color: #272833">
									</div>
									<div class="col-sm-30">
										<input type="text" class="form-control" id="address"
											name="mb_address1" value="${memberVO.mb_address1 }" required readonly
											style="background-color: #272833"> <input type="text"
											class="form-control" id="address2" name="mb_address2"
											value="${memberVO.mbaddress2 }" required>
									</div>
								</div>
								<div class="mb-3 row">
									<div class="col-sm-12" style="text-align: right;">
										<!-- 시큐리티에서 사용자가 지정한 폼을 사용하려면 반드시 아래의 코드를 첨부해줘야 한다.-->
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" /> <input type="submit"
											class="btn-check" id="submitBtn"> <label
											class="btn btn-outline-success" for="submitBtn">회원가입</label>
										<input type="reset" class="btn-check" id="resetBtn"> <label
											class="btn btn-outline-success" for="resetBtn">다시쓰기</label> <input
											type="button" class="btn-check" id="cancelBtn"
											onclick="location.href='/'"> <label
											class="btn btn-outline-success" for="cancelBtn">돌아가기</label>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
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