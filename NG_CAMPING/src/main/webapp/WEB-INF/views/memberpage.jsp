<%@page import="pro.s2k.camp.vo.MemberVO"%>
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
<%-- 다음 우편번호 API --%>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">

<script type="text/javascript">
	function ajaxTOsend() {
		$.ajax({
			type : "GET",
			url : "memberpageOk.do",
			data : $('#memberVOForm').serialize(),
			dataType : "text",
			success : function(result) {
				console.log(result)
				if (result == 1) {
					alert('넥네임이 수정되었습니다.. 다시 로그인해주세요.');
					location.href = '/main.do';
				} else if (result == 0) {
					alert('오류가 발생했습니다. 닉네임을 다시 수정해주세요.');
					$("#nick").val = '';

				}

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) { // java에서 에러가 나는 경우 탐.
				alert("오류발생")
			}

		});
	}

	function logoutSubmit() {
		var logout = document.logout;
		logout.submit();
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
</script>
<style>
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
<body class="is-preload landing">


	<!-- header -->
	<%@ include file="header_footer/header.jsp"%>


	<section id="banner1">
		<div id="content1">
			<div id="member_position">
				<div class="mb-4">
					<form action="/memberpageOk.do" method="get" name="memberVOForm"
						id="memberVOForm">
						<input type="hidden" name="mb_ID" value="${mvo.mb_ID}">
						<div style="width: 70%;">
							<div class="title" style="font-size: 25px;">회원정보</div>
							<br>
							<table class="table table-dark"
								style="color: white; border: 1px solid white; border-radius: 10px;">
								<tr>
									<th colspan="3"
										style="color: white; background-color: black; text-align: left;">${mvo.mb_name }님의
										정보입니다.</th>
								</tr>

								<tr>
									<td class="td-1" style="">아이디</td>
									<td class="td-2" colspan="2">${mvo.mb_ID }</td>
								</tr>
								<tr>
									<td class="td-1">이름</td>
									<td class="td-2" colspan="2">${mvo.mb_name }</td>
								</tr>

								<tr>
									<td class="td-1">닉네임</td>
									<td class="td-2"><input type="text" class="form-control"
										id="nick" name="mb_nick" style="width: 250px;" required
										maxlength="10" value="${mvo.mb_nick }"></td>
									<td class="td-2" style="text-align: center;"><input
										type="button" id="nickcheck" value="중복확인"
										onclick="FnNickcheck();" /></td>
								</tr>

								<tr>
									<td class="td-1">이메일</td>
									<td class="td-2" colspan="2">${mvo.mb_email }</td>
								</tr>

								<tr>
									<td class="td-1" style="vertical-align: middle;">주소</td>
									<td class="td-3" colspan="2">${mvo.mb_zipcode }
										${mvo.mb_address1 }${mvo.mb_address2 }</td>


								</tr>
							</table>
							<div style="float: right;">

								<input type="button" id="pwdcorrect" value="비밀번호변경"
									class="btn btn-primary"
									" onclick="location.href='/passwordcorrect.do'"
									style="color: white;"> <input type="submit"
									id="memcorrect" value="수정하기" class="btn btn-success"
									onclick="ajaxTOsend();"> <input type="button"
									id="memdelete" value="탈퇴하기" class="btn btn-danger"
									onclick="location.href='/memberdelete.do'"
									style="color: white;">

							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<%@ include file="header_footer/footer.jsp"%>

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
	<div style="position: fixed; bottom: 10px; right: 10px; z-index: 100;">
		<a href="#banner" style="text-decoration: underline;"><img
			src="${pageContext.request.contextPath }/resources/assets/css/images/top.png"></a>
	</div>
</body>
</html>