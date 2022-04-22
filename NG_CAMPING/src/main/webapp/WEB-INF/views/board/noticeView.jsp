<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath }/resources/assets/css/images/logo.png" />
<title>NG캠핑</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/axicon/axicon.min.css" />
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

#nSession{
	padding-right: 10%; 
	padding-left: 10%; 
	margin: 0 auto;
}

.uploadDiv dl{
	display: table;
	width: 100%;
	margin-bottom: 0;
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #ccc;
}

.uploadDiv dt{
	display: table-cell;
    width: 13%;
    text-align: center;
    vertical-align: middle;
    border-right: 1px solid #d2d2d2;
    box-sizing: border-box;
}

.uploadDiv dd{
	display: table-cell;
    width: 87%;
    padding: 17px 25px;
    box-sizing: border-box;
}

#info>div{
	float:left;
}

#date::before{
	content: "";
    display: inline-block;
    width: 1px;
    height: 12px;
    background: #ccc;
    margin: 0 10px 0 6px;
    vertical-align: -2px;
}
#date::after{
	content: "";
    display: inline-block;
    width: 1px;
    height: 12px;
    background: #ccc;
    margin: 0 10px 0 6px;
    vertical-align: -2px;
}

</style>



<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>




</head>
<body class="is-preload landing">
	<div id="page-wrapper">

	<!-- header -->
	<%@ include file="../headerFooter/header.jsp"%>

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
			<p
				style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">공지사항</p>
		</div>


	</div>
	<section id = "nSession">
		<form method="post">
			<sec:csrfInput/>
			<input type="hidden" name="p" value="${cv.currentPage }"/>
			<input type="hidden" name="s" value="${cv.pageSize }"/>
			<input type="hidden" name="b" value="${cv.blockSize }"/>
			<input type="hidden" name ="nt_idx" value="${nv.nt_idx }">
			<br>
			<table class="table" style="border: 1px solid white;">
				<thead class="thead-dark">
					<tr>
						<th>
							<div>
								<div style="text-align: left; font-size: 25px; font-weight: bold; padding-bottom: 1%; padding-left: 2%;">
									<c:out value="${nv.nt_title }"></c:out>
								</div>
								<div id="info" style="padding-left: 2%">
									<div style="text-align: left; font-size: 15px;"> 
										<c:out value="${nv.mb_nick }"></c:out> 
									</div>
									<div id="date" style="text-align: left; font-size: 15px;">
										<fmt:formatDate value="${nv.nt_regDate }" var="reg" pattern="yyyy.MM.dd"/>
										<fmt:formatDate value="${nv.nt_modiDate }" var="modi" pattern="yyyy.MM.dd"/>
										<c:if test="${modi>reg }">
											수정일 : <c:out value="${modi }"></c:out>
										</c:if> 
											<c:out value="${reg }"></c:out>
									</div>
									<div style="text-align: left; font-size: 15px;">조회수 : 
										<c:out value="${nv.nt_hit }"></c:out>
									</div>
								</div>
							</div>
							<div class="uploadDiv">
								<dl>
									<dt>
										<span style="width: 100%; height: 100%; vertical-align: middle;">
											<span style="display: table-cell;">첨부파일</span>
										</span>
									</dt>
									<dd>
										<c:if test="${not empty nv.fileList }">
											<c:forEach var="fvo" items="${nv.fileList }">
												<c:url var="url" value="/download.do">
													<c:param name="of" value="${fvo.originalName }"></c:param>
													<c:param name="sf" value="${fvo.saveName }"></c:param>
												</c:url>
												<a href="${url }" title="${fvo.originalName }"><i class="axi axi-download2"></i> ${fvo.originalName }</a><br />
											</c:forEach>
										</c:if>
									</dd>
								</dl>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="color: white; padding-left: 3%;">${nv.nt_content }</td>
					</tr>
				</tbody>
			</table>
			<c:choose>
			<c:when test="${role eq 'ROLE_ADMIN' }">
				<div style="text-align: center;">
					<button class="btn btn-outline-secondary"
						style="margin: auto; width: 100px; height: 60px;" formaction="/board/notice.do">목록으로</button>
					<button class="btn btn-outline-secondary"
						style="margin: auto; width: 100px; height: 60px;" formaction="/board/noticeUpdate.do">수정하기</button>
					<button class="btn btn-outline-secondary"
						style="margin: auto; width: 100px; height: 60px;" formaction="/board/noticeDelete.do">삭제하기</button>
				</div>
			</c:when>
			<c:otherwise>
				<div style="text-align: center;">
					<button type="button" class="btn btn-outline-secondary" style="margin: auto;" onclick="history.back()">목록으로</button>
				</div>
			</c:otherwise>
			</c:choose>
		</form>
	</section>
	<br />


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



</body>
</html>