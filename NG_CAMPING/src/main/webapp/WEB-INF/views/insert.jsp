<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

#membertitle {
	padding-top: 100px;
}

#wrapper {
	width: 500px;
	padding-left: 20%;
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
				<a href="index.jsp"><img
					src="${pageContext.request.contextPath }/resources/assets/css/images/logo.png"
					alt="" /> </a>
			</h1>
			<nav id="nav">
				<ul>
					<li><a href="index.jsp">Home</a></li>
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
					<li><a href="/insert.do">회원가입</a></li>
					<li><a href="html/login.html">로그인</a></li>
				</ul>
			</nav>
		</header>
		<!-- Banner -->

		<section id="banner1">
			<div id="content1">
				<div id="member_position">
					<h1 style="font-size: 30px">회원가입</h1>
					<p />
					<div style="width: 50%;">
						<label for="exampleFormControlTextarea1">개인정보 수집 및 이용 동의 </label>
						<textarea class="form-control" id="exampleFormControlTextarea1"
							readonly="readonly">
개인정보보호법에 따라 NG캠핑 회원가입 신청하시는 분께 수집하는 
개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간, 
동의 거부권 및 동의 거부 시 불이익에 관한 사항을 안내 드리오니 자세히 읽은 후 
동의하여 주시기 바랍니다.

1. 수집하는 개인정보
이용자는 회원가입을 하지 않아도 정보 검색, 게시판확 인 등 대부분의 서비스를 
회원과 동일하게 이용할 수 있습니다. 이용자가 캠핑후기, 게시판 글쓰기 같은
회원제 서비스를 이용하기 위해 회원가입을 할 경우, NG캠핑은 서비스 이용을 
위해 필요한 최소한의 개인정보를 수집합니다.

회원가입 시점에 NG캠핑은 이용자로부터 수집하는 개인정보는 아래와 같습니다.

- 회원 가입 시에 ‘아이디, 패스워드, 닉네임, 생년월일, 이메일, 전화번호, 주소'를 
  필수항목으로 수집합니다. 만약 이용자가 입력하는 생년월일이 만18세 미만 
  경우에는 회원가입이 어려우므로 관리자에게 문의하여주세요.

서비스 이용 과정에서 이용자로부터 수집하는 개인정보는 아래와 같습니다.

- 회원정보 또는 개별 서비스에서 회원 정보(별명수정, 비밀번호 수정)를
  설정할 수 있습니다.
  
  서비스 이용 과정에서 IP 주소, 쿠키, 서비스 이용 기록, 위치정보가 생성되어 
  수집될 수 있습니다. 

구체적으로 
1) 서비스 이용 과정에서 이용자에 관한 정보를 자동화된 방법으로 생성하여 이를 
   저장(수집)하거나,
2) 서비스 이용 과정에서 위치정보가 수집될 수 있으며, NG캠핑에서 제공하는 
   위치기반 서비스에 대해서 따로 저장(수집)하지 않습니다. 

2. 수집한 개인정보의 이용
NG캠핑 관련 제반 서비스의 회원관리, 서비스 개발・제공 및 향상, 안전한 
인터넷 이용환경 구축 등 아래의 목적으로만 개인정보를 이용합니다.

3. 개인정보의 보관기간

NG캠핑은 원칙적으로 이용자의 개인정보를 회원 탈퇴 시 지체없이 
파기하고 있습니다. 단, 이용자에게 개인정보 보관기간에 대해 별도의 동의를
얻은 경우, 또는 법령에서 일정 기간 정보보관 의무를 부과하는 경우에는 해당 
기간 동안 개인정보를 안전하게 보관합니다.

이용자에게 개인정보 보관기간에 대해 회원가입 시 또는 서비스 가입 시 동의를 
얻은 경우는 아래와 같습니다.

- 부정 가입 및 이용 방지
  1. 부정 이용자의 가입인증 : 탈퇴일로부터 6개월 보관
  2. 탈퇴한 이용자의 휴대전화번호(복호화가 불가능한 일방향 암호화(해시처리)) : 
     탈퇴일로부터 6개월 보관
  3. 휴대전화번호:등록/수정/삭제 요청 시로부터 최대1년
  4. ID : 서비스 탈퇴 후 6개월 보관
  5. 로그인 기록: 3개월
  6. 참고로 NG캠핑은 ‘개인정보 유효기간제’에 따라 1년간 서비스를 이용하지 
     않은 회원의 개인정보를 별도로 분리 보관하여 관리하고 있습니다.

4. 개인정보 수집 및 이용 동의를 거부할 권리

이용자는 개인정보의 수집 및 이용 동의를 거부할 권리가 있습니다. 회원가입 시 
수집하는 최소한의 개인정보, 즉, 필수 항목에 대한 수집 및 이용 동의를 
거부하실 경우, 회원가입이 어려울 수 있습니다.
</textarea>
					</div>
					<br>
					<div class="form-check" id="check">
						위 내용을 읽고 개인정보 수집에 동의합니다. &nbsp;&nbsp;&nbsp;&nbsp; <input
							class="form-check-input" type="checkbox" value=""
							id="flexCheckDefault"> <label class="form-check-label"
							for="flexCheckDefault"> 동의합니다. </label>
					</div>


					<div id="membertitle">
						<b> 회원정보 입력 </b>
						<p>
						<div class="row">
							<div class="col-md-3 mb-4">
								<label for="id">아이디</label> <input type="text"
									class="form-control" id="Id" placeholder="" value="" required
									maxlength="12">

							</div>

							<div class="col-md-3 mb-4">
								<label for="nickname">비밀번호</label> <input type="password"
									class="form-control" id="password" placeholder="" value=""
									required maxlength="15">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3 mb-4">
								<label for="id">닉네임</label> <input type="text"
									class="form-control" id="nickname" placeholder="" value=""
									required maxlength="10">
							</div>


							<div class="col-md-3 mb-4">
								<label for="nickname">이메일</label> <input type="email"
									class="form-control" id="email" placeholder="you@example.com"
									required>
							</div>
						</div>

						<div class="row">
							<label for="nickname">생년월일</label>

							<div class="col-md-2 mb-6">
								<input type="text" id="birthYear" placeholder="" value=""
									required maxlength="4">
							</div>
							<div class="col-md-2 mb-6">
								<select name="month">
									<option value="">-- 선택 --</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
								</select>
							</div>

							<div class="col-md-2 mb-6">
								<select name="day">
									<option value="">-- 선택 --</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
								</select>
							</div>
						</div>
						<p>
						<div class="col-md-6 mb-3">
							주소 &nbsp;&nbsp;&nbsp; <input type="button" class="btn-check"
								id="zipCodebtn" onclick="daumPostcode();"> <label
								class="btn btn-outline-primary" for="zipCodebtn">우편번호찾기</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="zipcode"
									placeholder="" required>
							</div>
							<input type="text" class="form-control" id="address"
								placeholder="" required> <input type="text"
								class="form-control" id="address2" placeholder="상세주소">
						</div>
						<div id="wrapper">
							<button class="btn btn-primary btn-block" type="button"
								style="font-size: 20px">가입하기</button>
						</div>
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