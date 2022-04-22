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

<meta charset="utf-8" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/assets/css/swiper.min.css">
<script type="text/javascript">




	function logoutSubmit() {
		var logout = document.logout;
		logout.submit();
	}
	function selectInfo(index){
		var infoIdx = document.getElementById('mb_idx');
		infoIdx.value = document.getElementById('idx'+index).innerHTML;
		if(infoIdx != null){
			infoSubmit();							
		}
	}
	function infoSubmit(){
		document.getElementById('infoForm').submit();
	}
</script>
<style>

.swiper-container {
	width: 90%;
	height: 200px;
	margin: 20px auto;
}

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
	<%@ include file="../headerFooter/header.jsp"%>

		<!-- Banner -->
		<div class="col-sm-8">
			<img src="">
		</div>
		<br> <br>
		<div>
			<p style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">회원관리</p>
			</div>
			<form action="/selectSearchMember.do" method="post">
				<sec:csrfInput/>
			<div style="padding-right: 8%; padding-bottom: 3%;">
			<div class="col-sm-1" style="float: right;">
				<input type="submit" value="검색" >
			</div>

			<div class="col-sm-2" style="float: right;">
				<input type="text" name="searchText" value="${searchtext }"/> 
			</div>

			<div class="col-sm-1.8" style="float: right;">
				<select name="searchType" id="searchType" style="float: left;">
					<option value="all" selected>전체</option>
					<option value="id" ${searchType eq 'id' ? 'selected' : '' }>아이디</option>
					<option value="name" ${searchType eq 'name' ? 'selected' : '' }>이름</option>
					<option value="nick" ${searchType eq 'nick' ? 'selected' : '' }>닉네임</option>
					<option value="role" ${searchType eq 'role' ? 'selected' : '' }>권한</option>
				</select>
			</div>
		</div>
		</form>
		<div style="padding-left: 160px;">
		전체 : ${pv.totalCount} 개 
		</div>
		<div style="padding-left: 12%;">
			<form id="infoForm" action="/admin/memberManageInfo.do">
			<div style="width:90%; height:200px; overflow:auto">
				<table style="width: 100%; font-size: 17px;" id="infoTable">
					<tr>
						<c:if test="${pv.totalCount==0 }">
							<td colspan="6">등록된 사용자가 없습니다.</td>
						</c:if>
					</tr>
					<tr>
						<th> No </th>
						<th> 아이디 </th>
						<th> 이름 </th>
						<th> 닉네임 </th>
						<th> 권한 </th>
					</tr>
					<c:if test="${pv.totalCount > 0 }">
						<c:if test="${not empty pv.list }">
							<c:forEach var="vo" items="${pv.list }" varStatus="num">
								<tr style="cursor:pointer;" id="info${num.index }" onclick="selectInfo('${num.index}')">
									<td id="idx${num.index }">${vo.mb_idx }</td>
									<td>${vo.mb_ID }</td>
									<td>${vo.mb_name }</td>
									<td>${vo.mb_nick }</td>
									<td>${vo.gr_role }</td>
								</tr>
							</c:forEach>
						</c:if>
					</c:if>
				</table>
				</div>
				
				<input type="hidden" id="mb_idx" name="mb_idx">
			</form>
		</div>
		<br>
		<form method="post">
			<sec:csrfInput/>
			<div style="padding-left: 12%;">
				<table class="table table-bordered" style="width: 90%; color: white; font-size: 17px;">
					<tr>
						<th colspan="5" style="text-align: left; padding-left: 2%; font-size: 20px; font-weight: bold;" >정보보기</th>
					</tr>
					<tr>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">아이디</td>
						<td style="width: 300px; background-color:#1C1D26;">
							<c:out value="${mv.mb_ID }"/>
							<input type="hidden" name="mb_ID" value="${mv.mb_ID }">
						</td>
						
						<td style="width: 100px; text-align: center; background-color:#2D2E36;">비밀번호</td>
						<td style="width: 300px; background-color:#1C1D26;"> 
							<button class="btn btn-secondary" formaction="/resetPasswordOk.do">임시번호 발급</button>
						
						</td>
					</tr>
					<tr>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">이름</td>
						<td style="width: 300px; background-color:#1C1D26;"><c:out value="${mv.mb_name }"/></td>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">닉네임</td>
						<td style="width: 300px; background-color:#1C1D26;"><c:out value="${mv.mb_nick }"/></td>
					</tr>
					<tr>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">이메일</td>
						<td style="width: 300px; background-color:#1C1D26;">
							<c:out value="${mv.mb_email }"/>
							<input type="hidden" name="mb_email" value="${mv.mb_email }">
						</td>
						
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">전화번호</td>
						<td style="width: 300px; background-color:#1C1D26;"><c:out value="${mv.mb_tel }"/></td>
					</tr>
					<tr>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">생일</td>
						<td style="width: 300px; background-color:#1C1D26;"><c:out value="${mv.mb_birth }"/></td>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">권한</td>
						<td style="width: 300px; background-color:#1C1D26;"><c:out value="${mv.gr_role }"/></td>
					</tr>
					<tr>
						<td style="width: 100px; text-align: center; background-color:#2D2E36; ">주소</td>
						<td style="width: 300px; background-color:#1C1D26;" colspan="1"><c:out value="${mv.mb_address1 }"/><br><c:out value="${mv.mb_address2 }"/></td>
					</tr>
				</table>
				<br>
			</div>
		</form>
	</div>

<!-- Footer -->
	<%@ include file="../headerFooter/footer.jsp"%>

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