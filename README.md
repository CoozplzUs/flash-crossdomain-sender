플래시 크로스도메인 전송 서버
=====
### \[설명\]
플래시 소켓 클라이언트 크로스도메인 인증을 위해 작성된 프로그램

### \[동작설명\]
1. 플래시 소켓 클라이언트에서 TCP 연결을 서버로 하기 위해서 접속을 하게 되면 
   내부적으로 크로스도메인 인증 URL 인 xmlsocket://xxx.xxx.xxx.xxx:843 으로 
   접속을 하여 크로스 도메인 정보를 조회한다.
2. 서버는 크로스 도메인 요청 클라이언트가 접속하면 크로스 도메인 XML 정보를 클라이언트로
   전송한 후 연결을 종료한다.
   
  




