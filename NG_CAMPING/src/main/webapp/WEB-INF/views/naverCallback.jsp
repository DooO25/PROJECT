<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
<script type="text/javascript">
  var naver_id_login = new naver_id_login("NfuiqEV4HltgQ4mfdiO6", "http://localhost:8080/naverCallback.do");
  // 접근 토큰 값 출력
  alert(naver_id_login.oauthParams.access_token);
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  function naverSignInCallback() {
    alert(naver_id_login.getProfileData('name'));
    alert(naver_id_login.getProfileData('email'));
    alert(naver_id_login.getProfileData('nickname'));
    alert(naver_id_login.getProfileData('id'));
    alert(naver_id_login.getProfileData('birthday'));
  }
  

  
  
  
/*   public String generateState()
  {
      SecureRandom random = new SecureRandom();
      return new BigInteger(130, random).toString(32);
  }


//CSRF 방지를 위한 상태 토큰 검증 검증
//세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함


//콜백 응답에서 state 파라미터의 값을 가져옴
String state = request.queryParams(“state”);


//세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
String storedState = request.session().attribute(“state”);


if( !state.euals( storedState ) ) {
   return RESPONSE_UNAUTHORIZED; //401 unauthorized
} else {
   Return RESPONSE_SUCCESS; //200 success
}
 */
</script>
</body>
</html>