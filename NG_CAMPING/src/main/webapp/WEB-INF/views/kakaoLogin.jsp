<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<body>
    <a href="#" id="kakaoLogin"><img src="${pageContext.request.contextPath }/resources/images/kakaoLogin.png" alt="카카오계정 로그인" style="height: 100px;"/></a>

    <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
    function saveToDos(token) { //item을 localStorage에 저장합니다. 
        typeof(Storage) !== 'undefined' && sessionStorage.setItem('AccessKEY', JSON.stringify(token)); 
    };

    window.Kakao.init('7d11b135269a5b882d3536ba3b4394b3');
    
    function kakaoLogin() {
        window.Kakao.Auth.login({
            scope: 'profile_nickname, account_email', //동의항목 페이지에 있는 개인정보 보호 테이블의 활성화된 ID값을 넣습니다.
            success: function(response) {
                saveToDos(response.access_token)  // 로그인 성공하면 사용자 엑세스 토큰 sessionStorage에 저장
                window.Kakao.API.request({ // 사용자 정보 가져오기 
                    url: '/v2/user/me',
                    success: (res) => {
                        const kakao_account = res.kakao_account;
                        alert('로그인 성공');
                        console.log(kakao_account);
         /*  window.location.href='/insert.do' */
                    }
                });
            },
            fail: function(error) {
                console.log(error);
            }
        });
    };
    
    
    const login = document.querySelector('#kakaoLogin');
    login.addEventListener('click', kakaoLogin);
</script>
</body>
</html>