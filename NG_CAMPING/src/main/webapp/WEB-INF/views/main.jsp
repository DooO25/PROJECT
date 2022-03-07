<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" />
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
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<style>
.swiper-container {
	width: 90%;
	height: 200px;
	margin: 20px auto;
}

.swiper-slide {
	text-align: center;
	font-size: 18px;
	width: 60%;
	/* Center slide text vertically */
	display: -webkit-box;
	display: -ms-flexbox;
	display: -webkit-flex;
	display: flex;
	-webkit-box-pack: center;
	-ms-flex-pack: center;
	-webkit-justify-content: center;
	justify-content: center;
	-webkit-box-align: center;
	-ms-flex-align: center;
	-webkit-align-items: center;
	align-items: center;
}

.swiper-slide:nth-child(2n) {
	width: 40%;
}

.swiper-slide:nth-child(3n) {
	width: 20%;
}


</style>

<noscript>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>
</head>
<body class="is-preload landing">
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">
			<h1 id="logo">
				<a href="/main.do"><img src="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" alt="" />
				</a>
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
					<li><a href="/insert.do">회원가입</a></li>
					<li><a href="html/login.html">로그인</a></li>
				</ul>
			</nav>
		</header>

		<!-- Banner -->
		<section id="banner">
			<div class="content">
				<header class="hightitle">
					<h2>캠핑은 NG캠핑!</h2>
					<p>
						여러분의 캠핑생활을 도와드립니다!<br /> 스크롤을 내려 캠핑정보를 확인하세요!
					</p>
				</header>
				
				<!-- Swiper -->
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide">Slide 1</div>
						<div class="swiper-slide">Slide 2</div>
						<div class="swiper-slide">Slide 3</div>
						<div class="swiper-slide">Slide 4</div>
						<div class="swiper-slide">Slide 5</div>
						<div class="swiper-slide">Slide 6</div>
						<div class="swiper-slide">Slide 7</div>
						<div class="swiper-slide">Slide 8</div>
						<div class="swiper-slide">Slide 9</div>
						<div class="swiper-slide">Slide 10</div>
					</div>
					
					<!-- Add Pagination -->
					<div class="swiper-pagination"></div>
				</div>
				<script src="${pageContext.request.contextPath }/resources/assets/js/swiper.min.js"></script>
				<script>
					var swiper = new Swiper('.swiper-container', {
						pagination : '.swiper-pagination',
						slidesPerView : 4,
						centeredSlides : true,
						paginationClickable : true,
						spaceBetween : 30
					});
				</script>

				<!-- Add Pagination -->
				<div class="swiper-pagination"></div>
			 <!-- <a href="#one" class="goto-next scrolly">Next</a>  -->
		</div>	
		</div>
	</section>


	<!-- One -->
	<section id="one" class="spotlight style1 bottom">
		<span class="image fit main"><img src="${pageContext.request.contextPath }/resources/assets/css/images/pic02.jpg" alt="" /></span>
		<div class="content">
			<div class="container">
				<div class="row">
					<div class="col-4 col-12-medium">
						<header>
							<h2>저희 NG캠핑장은</h2>
							<p>
								가족 또는 연인, 친구들과 함께!<br> 혹은 캠핑을 좋아하는 캠핑족들을 위해 <br> 제작된
								페이지입니다.
							</p>
						</header>
					</div>
					<div class="col-4 col-12-medium">
						<p>
							평소 캠핑에 관심이 많은 당신! 혹은 캠핑을 <br> 하고 싶은 당신! 막상 하고싶은데 캠핑장이 <br>
							어디에 있는지 지금당장 가도 운영을 하는지 <br> 궁금하시죠? 그런분들을 위해 준비했습니다. <br>
							바로 저의 NG캠핑에 모든것이 다 있습니다
						</p>
					</div>
					<div class="col-4 col-12-medium">
						<p>
							캠핑 초보자에서부터 배테랑까지 다양한 <br> 캠핑족들을 위해 캠핑장 정보를 공유합니다.<br>
							뿐만아니라 여러분들이 직접 후기를 적어 <br> 가장 좋았던 캠핑장을 골라 추천까지!<br>
							여러분들의 재미있는 캠핑을 추구합니다.
						</p>
					</div>
				</div>
			</div>
		</div>
	<!-- <a href="#two" class="goto-next scrolly">Next</a>  -->
	</section>

	<!-- Two -->
	<section id="two" class="spotlight style1 left">
		<span class="image fit main"><img
			src="http://san.chosun.com/site/data/img_dir/2021/06/23/2021062301705_0.jpg"
			alt="" /></span>
		<div class="content">
			<header>
				<h2>
					일반 야영장을 <br> 찾으시나요?
				</h2>
				<p>
					여러분의 위치에서부터 전국에 있는 <br> 일반 야영장을 찾아드립니다.
				</p>
			</header>
			<p>
				사람들이 거의 찾지 않는 지역에서 낭만을<br> 즐기고 싶은 당신! 그런당신을 위해 준비했습니다.
			</p>
			<ul class="actions">
				<li><a href="#" class="button">더보기</a></li>
			</ul>
		</div>
		<!-- <a href="#three" class="goto-next scrolly">Next</a> -->
	</section>

	<!-- Three -->
	<section id="three" class="spotlight style2 right">
		<span class="image fit main bottom"><img
			src="https://menu.mt.co.kr/moneyweek/thumb/2020/11/20/06/2020112014268082492_1.jpg"
			alt="" /></span>
		<div class="content">
			<header>
				<h2>
					자동차야영장을<br>찾으시나요?
				</h2>
				<p>
					여러분의 위치에서부터 전국에 있는<br>자동차 야영장을 찾아드립니다.
				</p>
			</header>
			<p>
				차박캠핑을 좋아하는 당신, 당신을위해 <br> 자동차 야영장을 추천합니다!
			</p>
			<ul class="actions">
				<li><a href="#" class="button">더보기</a></li>
			</ul>
		</div>
		<!--  <a href="#four" class="goto-next scrolly">Next</a>  -->
	</section>


	<section id="four" class="spotlight style3 left">
		<span class="image fit main"><img
			src="https://i2.wp.com/kingshope.kr/wp-content/uploads/2021/07/20210715_091542.png?fit=1024%2C617&ssl=1"
			alt="" /></span>
		<div class="content">
			<header>
				<h2>
					카라반을 <br> 찾으시나요?
				</h2>
				<p>
					여러분의 위치에서부터 전국에 있는 <br> 카라반을 찾아드립니다.
				</p>
			</header>
			<p>
				차박과는 다른 느낌을 내고싶다고요? <br>그렇다면 카라반을 추천합니다!
			</p>
			<ul class="actions">
				<li><a href="#" class="button">더보기</a></li>
			</ul>
		</div>
		 <!-- <a href="#five" class="goto-next scrolly">Next</a> -->
	</section>


	<section id="five" class="spotlight style4 right">
		<span class="image fit main bottom"><img
			src="https://www.5gcamp.com/files/camping/2018/10/14/e63b570aafb5e6bb859e699563b94367192926.jpg"
			alt="" /></span>
		<div class="content">
			<header>
				<h2>
					글램핑을 <br> 찾으시나요?
				</h2>
				<p>
					여러분의 위치에서부터 전국에 있는 <br> 글램핑을 찾아드립니다.
				</p>
			</header>
			<p>
				가족 또는 연인, 친구들과 함께 재미있는 <br>캠핑 분위기를 즐기고싶다면 <br>글램핑을 추천합니다!
			</p>
			<ul class="actions">
				<li><a href="#" class="button">더보기</a></li>
			</ul>
		</div>
		<!--  <a href="#six" class="goto-next scrolly">Next</a> -->
	</section>




	<!-- Four -->
	<section id="six" class="wrapper style1 special fade-up">
		<div class="container">
			<header class="major">
				<h2>현재 저희 NG캠핑의 캠핑장 갯수입니다.</h2>
				<p>저희 NG캠핑은 총 0개의 캠핑장의 정보를 가지고 있습니다.</p>
			</header>
			<div class="box alt">
				<div class="row gtr-uniform">
					<section class="col-4 col-6-medium col-12-xsmall">
						<span class="icon solid alt major"> <span
							class="icon solid alt major fas fa-mountain"></span>
						</span>
						<h3>000개</h3>
						<h3>일반야영장</h3>
						<p>일반야영장 테마의 캠핑장 갯수입니다.</p>
					</section>

					<section class="col-4 col-6-medium col-12-xsmall">
						<span class="icon solid alt major fas fa-car"></span>
						<h3>000개</h3>
						<h3>지동차야영장</h3>
						<p>자동차야영장 테마의 캠핑장 갯수입니다.</p>
					</section>

					<section class="col-4 col-6-medium col-12-xsmall">
						<span class="icon solid alt major fas fa-campground"></span>
						<h3>000개</h3>
						<h3>글램핑/카라반</h3>
						<p>글램핑/카라반 테마의 캠핑장 갯수입니다.</p>
					</section>
					<section>
					<!-- 	<br> <br> <a href="#seven" class="goto-next scrolly">Next</a> -->
					</section>
				</div>
			</div>
		</div>
	</section>

	<!-- 게시판 및 공지사항 -->
	<section id="seven" style="background-color: #E44C65">
		<div class="container">
			<div class="box alt">
				<div class="row gtr-uniform">
					<section class="col-4 col-12-xsmall" style="top: 20px">
						<table>
							<tr>
								<td>
									<h3 style="text-align: left">공지사항</h3>
								</td>
								<td><i class="fa fa-plus fa-1" style="float: right"></i></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
						</table>
						<br>
					</section>
					<section class="col-4 col-12-xsmall" style="top: 20px">
						<table>
							<tr>
								<td>
									<h3 style="text-align: left">공지사항</h3>
								</td>
								<td><i class="fa fa-plus fa-1" style="float: right"></i></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
						</table>
						<br>
					</section>

					<section class="col-4 col-12-xsmall" style="top: 20px">
						<table>
							<tr>
								<td>
									<h3 style="text-align: left">공지사항</h3>
								</td>
								<td><i class="fa fa-plus fa-1" style="float: right"></i></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left"><i
									class="fa fa-angle-right" aria-hidden="true"></i> <a href=""><font
										color="white">일반야영장 테마의 캠핑장 갯수입니다.</font></a></td>
							</tr>
						</table>
						<br>
					</section>
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
	<script src="${pageContext.request.contextPath }/resources/assets/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/jquery.scrolly.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/jquery.dropotron.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/jquery.scrollex.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/browser.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/breakpoints.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/util.js"></script>
	<script src="${pageContext.request.contextPath }/resources/assets/js/main.js"></script>
	<div style="position: fixed; bottom: 10px; right: 10px; z-index: 100;">
		<a href="#banner" style="text-decoration: underline;"><img
			src="${pageContext.request.contextPath }/resources/assets/css/images/top.png"></a>
	</div>
</body>
</html>