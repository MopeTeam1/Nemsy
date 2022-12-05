# 모바일 프로그래밍 팀1-발의법률안 창구
### 발의법률안 창구
국민들이 간편하게 발의법률안에 대해 소통할 수 있는 커뮤니티 앱 🏛

<img width="440" alt="image" src="https://user-images.githubusercontent.com/84428520/205608038-87184b51-db73-4583-a740-d5d62d4592d1.png">

### 팀원
[홍승현](https://github.com/SuperH0ng) : 팀장-서버 개발 및 REST Client 구현  
[이은선](https://github.com/Eun-sun-Lee) : 팀원-안드로이드 개발(로그인/회원가입/마이 페이지 회원 관리, 커뮤니티 목록 페이지), REST Client 구현  
[김유진](https://github.com/yuujinkim) : 팀원-서버 개발 및 안드로이드 개발(발의법률안 목록/상세 페이지), REST Client 구현  
[김나현](https://github.com/nxhxxn) : 팀원-안드로이드 개발(마이 페이지, 커뮤니티 글 작성 페이지), REST Client 구현  
[박민선](https://github.com/decollzoq) : 팀원-안드로이드 개발(로그인/회원가입 페이지 ui, 커뮤니티 상세 페이지), REST Client 구현  

### github 주소
https://github.com/MopeTeam1

### 개발환경
* 클라이언트 : 안드로이드 스튜디오 / 사용 언어 - JAVA
* 서버 : Spring Boot
* 회원 관리 : 파이어베이스
* DBMS : MySQL
* DevOps : AWS
* 협업툴 : Github, Notion

### SDK 버전 
안드로이드 12
```
defaultConfig {
        applicationId "com.example.nemsy"
        minSdk 30
        targetSdk 32
        versionCode 1
        versionName "1.0"
    }
```
### 앱 사용 시 유의사항
1. 저희 앱은 발의법률안에 대해 사용자들과 소통하기 위한 커뮤니티 앱이므로 로그인 후 사용 가능합니다.  
* 저희 앱을 처음 사용하시는 분들께선 회원가입을 꼭 해주세요.  
2. 저희 앱은 프래그먼트로 화면 구성을 하였기 때문에 앱 초기 접속 시 화면 로딩 시간이 좀 걸릴 수 있습니다.  
3. 발의법률안 상세 페이지에서 좋아요 혹은 싫어요 둘 중 하나만 누를 수 있습니다.  
* 좋아요가 눌린 상태에서 싫어요를 누르면 좋아요가 취소됩니다.  
* 싫어요가 눌린 상태에서 좋아요를 누르면 싫어요가 취소됩니다.  

### 페이지 구성

### 페이지 구성-1.
