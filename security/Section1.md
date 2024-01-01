## Spring Security란?

인증, 권한 부여 및 일반적인 공격에 대한 보호를 제공하는 프레임워크입니다.



### Servlets & Filters

![image-20240101162814224](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240101162814224.png)

**Servlet**  
브라우저에서 자바 어플리케이션에 요청을 보내면 자바 코드는 HTTP 프로토콜을 이해할 수 없어서  
WAS(Tomcat)가 Servlet Container를 통하여 브라우저로부터 받은 HTTP 메세지를 ServletRequest 객체 로 변환하여 사용할 수 있게 합니다.  
그리고 자바 어플리케이션에 브라우저로 응답을 보내줄 때도 servlet은 ServeltResponse 객체를 이용하여   
브라우저가 이해할 수 있는 HTTP 메시지로 변환합니다.

**Filter**  
Filter는 특별한 종류의 Servlet으로 웹 어플리케이션을 향해 들어오는 모든 요청을 가로채어 필터 로직을 실행될 수 있도록 합니다.  
특정 URL 패턴을 사용하여 특정 URL 요청에 대하여 적용할 수 있습니다.  
필터는 로직에 의해서 적절하지 않은 요청이라고 판단할 경우 서블릿(DispatcherServlet) 호출을 하지 않습니다.  
그리고 필터는 체인으로 구성되는데, 중간에 필터를 자유롭게 추가할 수 있습니다.



### Security 내부 흐름

![image-20240101162517523](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240101162517523.png)

Spring Security Filter들은 백엔 서버에 들어오는 모든 요청을 감시합니다.  
웹 어플리케이션에 구성해둔 경로와 설정에 따라 Filter들은 이것이 보호된 자원인지 공개적으로 접근 가능한 자원인지 판별하여  
이에 따라 해당 유저에게 인증을 강제해야 할지 아닐지 결정합니다.



1. 유저가 로그인 페이지에 본인의 자격 증명을 입력하여 웹 어플리케이션에 인증을 요청을 합니다.
2. Spring Security Filter는 유저 이름과 비밀번호를 인증 객체로 변환합니다.
3. 인증 요청을 Authentication Manager에게 넘깁니다.  
    - 인증이 실패해도 모든 인증 제공자들에게 전송해서 전부 실패한 경우에만 실패 했다고 응답을 합니다.
4. 실제 인증을 수행하기 위해 유효한 인증 제공자(Authentication Providers) 있는지 확인하고 넘깁니다.
5. UserDetailsManager, UserDetailsService를 통하여 서버의 유저 정보를 가져옵니다.
6. 서버 유저 비밀번호와 요청한 유저 비밀번호를 불러와 암호화 또는 해싱하여 유효한지 확인 후 인증 제공자에게 알려줍니다.
7. 인증 제공자의 프로세스가 끝나면 인증 관리자에게 넘깁니다.
8. 인증 관리자로부터 정보를 Security Filter로 넘깁니다.
9. Security Filter에서 2단계에서 생성한 인증 객체를 Security Context에 저장합니다.  
    인증 객체에는 인증이 성공적이었는지 세션ID가 무엇인지 등을 갖게 되며,  
    로그인이 성공적이었다면 다음 요청부터는 인증을 요구하지 않습니다.  
10. 보호된 자원으로부터의 응답을 합니다.

실제 흐름은 다음과 같습니다.

![image-20240101194547577](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240101194547577.png)