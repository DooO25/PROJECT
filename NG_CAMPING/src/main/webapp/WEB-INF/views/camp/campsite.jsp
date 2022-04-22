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

	var cat1_num = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
			16);
	var cat1_name = new Array('서울', '부산', '대구', '인천', '광주', '대전', '울산', '강원',
			'경기', '경남', '경북', '전남', '전북', '제주', '충남', '충북');

	var cat2_num = new Array();
	var cat2_name = new Array();

	cat2_num[1] = new Array(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
			30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41);
	cat2_name[1] = new Array('강남구', '강동구', '강북구', '강서구', '관악구', '광진구', '구로구',
			'금천구', '노원구', '도봉구', '동대문구', '동작구', '마포구', '서대문구', '서초구', '성동구',
			'성북구', '송파구', '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구');

	cat2_num[2] = new Array(42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54,
			55, 56, 57);
	cat2_name[2] = new Array('강서구', '금정구', '남구', '동구', '동래구', '부산진구', '북구',
			'사상구', '사하구', '서구', '수영구', '연제구', '영도구', '중구', '해운대구', '기장군');

	cat2_num[3] = new Array(58, 59, 60, 61, 62, 63, 64, 65);
	cat2_name[3] = new Array('남구', '달서구', '동구', '북구', '서구', '수성구', '중구', '달성군');

	cat2_num[4] = new Array(66, 67, 68, 69, 70, 71, 72, 73, 74, 75);
	cat2_name[4] = new Array('계양구', '남구', '남동구', '동구', '부평구', '서구', '연수구',
			'중구', '강화군', '옹진군');

	cat2_num[5] = new Array(76, 77, 78, 79, 80);
	cat2_name[5] = new Array('광산구', '남구', '동구', '북구', '서구');

	cat2_num[6] = new Array(81, 82, 83, 84, 85);
	cat2_name[6] = new Array('대덕구', '동구', '서구', '유성구', '중구');

	cat2_num[7] = new Array(86, 87, 88, 89, 90);
	cat2_name[7] = new Array('남구', '동구', '북구', '중구', '울주군');

	cat2_num[8] = new Array(91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102,
			103, 104, 105, 106, 107, 108);
	cat2_name[8] = new Array('강릉시', '동해시', '삼척시', '속초시', '원주시', '춘천시', '태백시',
			'고성군', '양구군', '양양군', '영월군', '인제군', '정선군', '철원군', '평창군', '홍천군',
			'화천군', '횡성군');

	cat2_num[9] = new Array(109, 110, 111, 112, 113, 114, 115, 116, 117, 118,
			119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131,
			132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144,
			145, 146, 147, 148);
	cat2_name[9] = new Array('고양시 덕양구', '고양시 일산구', '과천시', '광명시', '광주시', '구리시',
			'군포시', '김포시', '남양주시', '동두천시', '부천시 소사구', '부천시 오정구', '부천시 원미구',
			'성남시 분당구', '성남시 수정구', '성남시 중원구', '수원시 권선구', '수원시 장안구', '수원시 팔달구',
			'시흥시', '안산시 단원구', '안산시 상록구', '안성시', '안양시 동안구', '안양시 만안구', '오산시',
			'용인시', '의왕시', '의정부시', '이천시', '파주시', '평택시', '하남시', '화성시', '가평군',
			'양주군', '양평군', '여주군', '연천군', '포천군');

	cat2_num[10] = new Array(149, 150, 151, 152, 153, 154, 155, 156, 157, 158,
			159, 160, 161, 162, 163, 164, 165, 166, 167, 168);
	cat2_name[10] = new Array('거제시', '김해시', '마산시', '밀양시', '사천시', '양산시', '진주시',
			'진해시', '창원시', '통영시', '거창군', '고성군', '남해군', '산청군', '의령군', '창녕군',
			'하동군', '함안군', '함양군', '합천군');

	cat2_num[11] = new Array(169, 170, 171, 172, 173, 174, 175, 176, 177, 178,
			179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191,
			192);
	cat2_name[11] = new Array('경산시', '경주시', '구미시', '김천시', '문경시', '상주시', '안동시',
			'영주시', '영천시', '포항시 남구', '포항시 북구', '고령군', '군위군', '봉화군', '성주군',
			'영덕군', '영양군', '예천군', '울릉군', '울진군', '의성군', '청도군', '청송군', '칠곡군');

	cat2_num[12] = new Array(193, 194, 195, 196, 197, 198, 199, 200, 201, 202,
			203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214);
	cat2_name[12] = new Array('광양시', '나주시', '목포시', '순천시', '여수시', '강진군', '고흥군',
			'곡성군', '구례군', '담양군', '무안군', '보성군', '신안군', '영광군', '영암군', '완도군',
			'장성군', '장흥군', '진도군', '함평군', '해남군', '화순군');

	cat2_num[13] = new Array(215, 216, 217, 218, 219, 220, 221, 222, 223, 224,
			225, 226, 227, 228, 229);
	cat2_name[13] = new Array('군산시', '김제시', '남원시', '익산시', '전주시 덕진구', '전주시 완산구',
			'정읍시', '고창군', '무주군', '부안군', '순창군', '완주군', '임실군', '장수군', '진안군');

	cat2_num[14] = new Array(230, 231, 232, 233);
	cat2_name[14] = new Array('서귀포시', '제주시', '남제주군', '북제주군');

	cat2_num[15] = new Array(234, 235, 236, 237, 238, 239, 240, 241, 242, 243,
			244, 245, 246, 247, 248);
	cat2_name[15] = new Array('공주시', '논산시', '보령시', '서산시', '아산시', '천안시', '금산군',
			'당진군', '부여군', '서천군', '연기군', '예산군', '청양군', '태안군', '홍성군');

	cat2_num[16] = new Array(249, 250, 251, 252, 253, 254, 255, 256, 257, 258,
			259, 260);
	cat2_name[16] = new Array('제천시', '청주시 상당구', '청주시 흥덕구', '충주시', '괴산군', '단양군',
			'보은군', '영동군', '옥천군', '음성군', '진천군', '청원군');

	function cat1_change(key, sel) {
		if (key == '')
			return;
		var name = cat2_name[key];
		var val = cat2_num[key];

		for (i = sel.length - 1; i >= 0; i--){}
			sel.options[i] = null;
		sel.options[0] = new Option('-선택-', '', '', 'true');
		for (i = 0; i < name.length; i++) {
			sel.options[i + 1] = new Option(name[i], val[i]);
		}
	}

	function sendType() {
		var selectOption = document.getElementById("h_area1");
			selectOption = selectOption.options[selectOption.selectedIndex].text;
		
		var selectOption2 = document.getElementById("h_area2");
			selectOption2 = selectOption2.options[selectOption2.selectedIndex].text;
		
		document.getElementById("searchType").value = selectOption;
		document.getElementById("searchType2").value = selectOption2; 
	    document.getElementById("formAction").submit(); 
	
	} 
</script>



<style type="text/css">
image /x-icon {
	vertical-align: middle;
	height: 90px;
	width: 60px;
}

img {
	vertical-align: middle;
	height: 30px;
	width: 30px;
}

element.style {
	width: 500px;
	height: 100px;
	margin: 0 auto;
}

#map {
	margin-top: 50px;
	margin-left: 300px;
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

#map {
	position: static;
}

table {
	text-align: center;
}

.th-1 {
	width: 20px;
}

.th-2 {
	width: 80px;
}

select option[value=""][disabled] {
	display: none;
}

.info-title {
    display: block;
    background: #50627F;
    color: #fff;
    text-align: center;
    height: 24px;
    line-height:22px;
    border-radius:4px;
    padding:0px 10px;
}
</style>

<noscript>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath }/resources/assets/css/noscript.css" />
</noscript>
<script language=javascript>
	
</script>

</head>
<body class="is-preload landing">
	<%@ include file="../headerFooter/header.jsp"%>

	<!-- Banner -->
	<div class="col-sm-8">
		<img src="">
	</div>
	<div style="padding-top: 70px; padding-left: 10%">
		<div class="col-sm-2">
			<select name="chart" id="chart" style="float: left;"
				onchange="change(this)">
				<option value="fst" selected>캠핑장</option>
				<option value="snd">캠핑톡</option>
			</select>
		</div>
		<div id="selectfst" class="col-sm-2" style="float: left;">
			<select name="list" id="list" onchange="window.open(value,'_self');" >
				<option selected disabled>-선택-</option>
				<option value="/camp/campsite.do" >캠핑장 찾기</option>
			</select>
		</div>
		<div id="selectsnd" class="col-sm-2"
			style="float: left; display: none;">
			<select name="list" id="list" onchange="window.open(value,'_self');">
				<option selected disabled>-선택-</option>
				<option value="../board/review.do" >캠핑후기</option>
				<option value="../board/notice.do">공지사항</option>
				<option value="../board/QnA.do">QnA</option>
			</select>
		</div>
	</div>
	<br>
	<br>
	<div>
		<p
			style="font-size: 50px; padding-left: 12%; padding-top: 5%; font-weight: bold;">캠핑장 찾기</p>
	</div>
	<div
		style="width: 50%; height: 900px; border: 1px solid gray; padding-left: 10px; padding-right: 0px; float: left;">

		<form action="/selectSearchCamp.do" method="get" id="formAction">
			<sec:csrfInput />
			<div class="title">캠핑장 검색하기</div>
			<br>
			<div class="mb-4 row">
				<label for="searchText" class="col-sm-3 col-form-label"> 캠핑장
					이름 </label>
				<div class="col-sm-5" style="padding-left: 0px;">
					<input type="text" class="form-control" id="searchText"
						name="searchText">
				</div>
			</div>

			<div class="mb-4 row" style="">
				<label for="ID" class="col-sm-3 col-form-label"> 지역 </label>
				<div class="col-sm-3" style="padding-left: 0px;">
					<select id="h_area1" onChange="cat1_change(this.value,h_area2)"
						class="h_area1">
						<option selected disabled>-선택-</option>
						<option value='1'>서울</option>
						<option value='2'>부산</option>
						<option value='3'>대구</option>
						<option value='4'>인천</option>
						<option value='5'>광주</option>
						<option value='6'>대전</option>
						<option value='7'>울산</option>
						<option value='8'>강원</option>
						<option value='9'>경기</option>
						<option value='10'>경남</option>
						<option value='11'>경북</option>
						<option value='12'>전남</option>
						<option value='13'>전북</option>
						<option value='14'>제주</option>
						<option value='15'>충남</option>
						<option value='16'>충북</option>
					</select>
				</div>
				
				<input type="hidden" id="searchType" name="searchType" value="" /> <input
					type="hidden" id="searchType2" name="searchType2" value="" />
				
				<div class="col-sm-3" style="padding-left: 0px;">
					<select id="h_area2" onChange="cat2_change(this.value)">
						<option>-선택-</option>
					</select>
				</div>
			</div>
			<div style="text-align: left;">
				
				<input type="checkbox" id="animalCheck" value="가능"
					name="animalCheck"> <label class="form-check-label"
					for="animalCheck"> 반려동물 동반여부 </label> <br>
			
			</div>
			<div style="text-align: center;">
				<button onclick="sendType();" class="btn btn-primary" 
					style="width: 200px; height: 40px;" value="검색">검색</button>
			</div>
		</form>
	
		<div style="padding-top: 60px;"></div>
		<div class="col-sm-3" style="font-size: 20px;">검색결과 리스트</div>
		<br />
		<div style="width:100%; height:390px; overflow:auto">
			<table>
				<tr>
					<th class="th-1" style="padding-top: 5px;">사진</th>
					<th class="th-2" style="padding-top: 5px;">내용</th>
				</tr>
				<c:if test="${pv.totalCount==0 }">
					<td colspan="2">등록된 글이 없습니다.</td>
				</c:if>
				<c:if test="${pv.totalCount>0 }">
					<c:if test="${not empty pv.list }">
						<c:forEach var="vo" items="${pv.list }" varStatus="vs">
							<input type="hidden" id="facltNm${vs.index }" name="var" value="${vo.facltNm }"/>
							<input type="hidden" id="mapX${vs.index }" value="${vo.mapX }"/>
							<input type="hidden" id="mapY${vs.index }" value="${vo.mapY }"/>
							<input type="hidden" id="idx${vs.index }" value="${vo.idx }"/>
							<input type="hidden" id="induty${vs.index }" value="${vo.induty }"/>
							<input type="hidden" id="addr1${vs.index }" value="${vo.addr1 }"/>
							<input type="hidden" id="firstImageUrl${vs.index }" value="${vo.firstImageUrl }"/>
							<input type="hidden" id="lineIntro${vs.index }" value="${vo.lineIntro }"/>
							<tr>
								<td style="border: 1px solid gray;">
									<img style="width: 100%; height: 100%;"	src="${vo.firstImageUrl }" onerror="this.style.display='none'" />
								</td>
								<td>
								<%-- document.getElementById('dView${vs.index }').submit() --%>
									<a style="cursor: pointer;" onclick="test3(${vs.index});"><c:out value="${vo.facltNm }"></c:out> </a>
								
									<div style="padding-top: 20px;">${vo.lineIntro }</div>
									<div style="color: gray; text-align: left; font-weight: bold;">${vo.addr1 }</div>
								</td>
							</tr>
						</c:forEach>	
					</c:if>	
				</c:if>
			</table>
		</div>

        <c:if test="${pv.totalCount!=0 }">
 			<c:if test="${pv.searchType==null }">
				<div style="border: none;text-align: center;">
						${pv.pageList}
				</div>
			</c:if>  
			<c:if test="${pv.searchType!=null }">
				<div style="border: none;text-align: center;">
					${pv.pageList3}
				</div>
			</c:if>
        </c:if>
	</div>
	
	<div id="map" style="width: 50%; height: 900px; margin-left: 10px;"></div>
	<br>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=eae203b516d5693eb4a9560f2bb8505b"></script>

	<script>
	  var infowindow;
      var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
      var options = { //지도를 생성할 때 필요한 기본 옵션
         center : new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
         level : 6
      //지도의 레벨(확대, 축소 정도)
      };

     
      
      var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
		
      if (navigator.geolocation) {

         // GeoLocation을 이용해서 접속 위치를 얻어옵니다
         navigator.geolocation.getCurrentPosition(function(position) {

                  var lat = position.coords.latitude, // 위도
                 	  lon = position.coords.longitude; // 경도

                  var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                  message = '<div style="padding:5px; color:white; width:250px; background-color:blue;">현재위치</div>';
                  // 마커와 인포윈도우를 표시합니다
                  displayMarker(locPosition, message);

               });

      } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

         var locPosition = new kakao.maps.LatLng(33.450701, 126.570667), message = 'geolocation을 사용할수 없어요..'

         displayMarker(locPosition, message);
      }


      // 마커 이미지의 이미지 주소입니다
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var mappingData = {};
      var positions = new Array();
      var overlaies = new Array();
      var csrf_token = "${_csrf.token}";
      
      function test() {
         $.ajax({
            type : "post",
            url : "/camp/selectCampSitel.do",
            dataType : "json",
            beforeSend : function(xhr) {
               xhr.setRequestHeader("X-CSRF-TOKEN", csrf_token);
               xhr.setRequestHeader("p",<c:out value='${pv.currentPage}'/>);
            },
            success : function(data) {
               var list = data;
               for (var i = 0; i < list.length; i++) {
                  var spotObj = {
                     name : list[i].idx,
                     title : list[i].facltNm,
                     latlng : new kakao.maps.LatLng(list[i].mapY,
                           list[i].mapX),
                     kndNm : list[i].induty
                  };
                  var overlay = {
                     name : list[i].facltNm
                  }
                  positions.push(spotObj);
                  overlaies.push(overlay);
               }

               for (var a = 0; a < positions.length; a++) {
                  // 마커 이미지의 이미지 크기 입니다
                  var imageSize = new kakao.maps.Size(30, 30);

                  // 마커 이미지를 생성합니다   
                  var markerImage = new kakao.maps.MarkerImage(
                           imageSrc, imageSize);

                  var marker = new kakao.maps.Marker({
                     map : map, // 마커를 표시할 지도
                     position : positions[a].latlng, // 마커를 표시할 위치
                     title : positions[a].title, // 마커의 타이틀(마우스를 올리면 타이틀 표기)
                     image : markerImage
                  // 마커 이미지 
                  });
                  marker.setMap(map); // 마커 표기

                  // 이것이 중요 
                  // 마커의 정보와 데이터를 담아둔 맵핑데이터 
                  mappingData[list[a].facltNm] = {
                     data : list[a],
                     marker : marker
                  };

                  // 마커를 클릭 시 커스텀 오버레이 생성
                  kakao.maps.event.addListener(marker, 'click', test2(
                        map, marker));
               }
            },
            error : function(request, status, error) {
               alert('에러가 발생하였습니다. 관리자에게 문의하세요.'+"code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
         });
      }
      
      function test2(map, marker) {
          return function() {
             var code = marker.getTitle();
             map.setCenter(marker.getPosition());
             var iwContent = '<div style="padding:5px; color:black; width:800px; height:300px; font-size: 20px;">'
                   + '<form action="/camp/detailPage.do" method="post" target="_blank" onsubmit=window.open("/camp/detailPage.do", "상세보기페이지", "width=1225, height=900, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );">'
                   + '<input type="hidden" name="var" value="'+code+'">'
                   + '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />'
                   + '<div style="font-size:35px; padding-top :20px; font-weight: bold;">'
                   + mappingData[code].data.facltNm
                   + '</div> <br>'
                   + '<div class="row">'
                   + '<div class="col-md-1" style="padding-right: 1px;"><img src="' + mappingData[code].data.firstImageUrl +'"style="width:200px; height:200px;"></div>'
                   + '<div class="col-md-11 style="width :100%;">'
                   + '<div class="col-md-6" style="padding-left: 200px; width :100%; font-weight: bold; font-size:20px;">주소 : '
                   + mappingData[code].data.addr1
                   + '</div>'
                   + '<div class="col-md-6" style="padding-left: 200px; width :100%; font-weight: bold; font-size:20px;">유형 : '
                   + mappingData[code].data.induty
                   + '</div>'
                   + '<div class="col-md-6" style="padding-left: 200px; width :100%; font-weight: bold; font-size:20px;">소개 : '
                   + mappingData[code].data.lineIntro
                   + '</div>'
                   + '</div>'
                   + '</div>'
                   + '<div style="text-align:center"> <input type="submit" style="background-color:blue; color:white;" value="상세페이지"> </div>'
                   + '</form>'
                   + '</div>'

             // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
             console.log(mappingData[code].data);
             iwPosition = new kakao.maps.LatLng(marker.getPosition()); //인포윈도우 표시 위치입니다
             iwRemoveable = true;
             // 인포윈도우를 생성합니다
             var infowindow = new kakao.maps.InfoWindow({
                position : iwPosition,
                content : iwContent,
                removable : iwRemoveable
             });
	             infowindow.open(map, marker);
          }
       }
	  function test3(idx) {
		  var campFacltNm = document.getElementById('facltNm'+idx).value;
		  var campX = document.getElementById('mapX'+idx).value;
		  var campY = document.getElementById('mapY'+idx).value;
		  var campIdx = document.getElementById('idx'+idx).value;
		  var campInduty = document.getElementById('induty'+idx).value;
		  var campAddr1 = document.getElementById('addr1'+idx).value;
		  var campFirstImageUrl = document.getElementById('firstImageUrl'+idx).value;
		  var campLineIntro = document.getElementById('lineIntro'+idx).value;
		  var spotObj = {
             name : campIdx,
             title : campFacltNm,
             latlng : new kakao.maps.LatLng(campY,campX),
             kndNm : campInduty
             };
          var overlay = {
             name : campFacltNm
          }
             positions.push(spotObj);
             overlaies.push(overlay);

       	  var marker = new kakao.maps.Marker({
             position : new kakao.maps.LatLng(campY,campX), // 마커를 표시할 위치
             title : campFacltNm, // 마커의 타이틀(마우스를 올리면 타이틀 표기)
          // 마커 이미지 
          });  
          /* map.setCenter(marker.getPosition()); */
          // 마커를 클릭 시 커스텀 오버레이 생성
          /* kakao.maps.event.addListener(marker, 'click', test4( map, marker));  */
          test4(map,marker,idx);
      }
      
     function test4(map,marker,idx) {
             map.setCenter(marker.getPosition());
	         var campFacltNm = document.getElementById('facltNm'+idx).value;
	       	 var campInduty = document.getElementById('induty'+idx).value;
			 var campAddr1 = document.getElementById('addr1'+idx).value;
			 var campFirstImageUrl = document.getElementById('firstImageUrl'+idx).value;
			 var campLineIntro = document.getElementById('lineIntro'+idx).value;
			 
             var iwContent = '<div style="padding:5px; color:black; width:800px; height:300px; font-size: 20px;">'
                   + '<form action="/camp/detailPage.do" method="post" target="_blank" onsubmit=window.open("/camp/detailPage.do", "상세보기페이지", "width=1225, height=900, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );">'
                   + '<input type="hidden" name="var" value="'+campFacltNm+'">'
                   + '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />'
                   + '<div style="font-size:35px; padding-top :20px; font-weight: bold;">'
                   + campFacltNm
                   + '</div> <br>'
                   + '<div class="row">'
                   + '<div class="col-md-1" style="padding-right: 1px;"><img src="' + campFirstImageUrl +'"style="width:200px; height:200px;"></div>'
                   + '<div class="col-md-11 style="width :100%;">'
                   + '<div class="col-md-6" style="padding-left: 200px; width :100%; font-weight: bold; font-size:20px;">주소 : '
                   + campAddr1
                   + '</div>'
                   + '<div class="col-md-6" style="padding-left: 200px; width :100%; font-weight: bold; font-size:20px;">유형 : '
                   + campInduty
                   + '</div>'
                   + '<div class="col-md-6" style="padding-left: 200px; width :100%; font-weight: bold; font-size:20px;">소개 : '
                   + campLineIntro
                   + '</div>'
                   + '</div>'
                   + '</div>'
                   + '<div style="text-align:center"> <input type="submit" style="background-color:blue; color:white;" value="상세페이지"> </div>'
                   + '</form>'
                   + '</div>'

             // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
             iwPosition = new kakao.maps.LatLng(marker.getPosition()); //인포윈도우 표시 위치입니다
             iwRemoveable = true;
             // 인포윈도우를 생성합니다
             var infowindow = new kakao.maps.InfoWindow({
                position : iwPosition,
                content : iwContent,
                removable : iwRemoveable
             });
	             infowindow.open(map, marker);
       } 
  	
		
      function displayMarker(locPosition, message) {

         // 마커를 생성합니다
         var marker = new kakao.maps.Marker({
            map : map,
            position : locPosition
         });

         var iwContent = message, // 인포윈도우에 표시할 내용
         iwRemoveable = true;

         // 인포윈도우를 생성합니다
         var infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
         });

         // 인포윈도우를 마커위에 표시합니다 
          infowindow.open(map, marker);

         // 지도 중심좌표를 접속위치로 변경합니다
         map.setCenter(locPosition);
      }
      $(document).ready(function() {
         test();
      });
     
   </script>


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
 	<script
		src="${pageContext.request.contextPath}/resources/assets/js/common.js"></script>



</body>
</html>