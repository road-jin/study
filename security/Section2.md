## Spring Security 설정

Spring Security는 기본적으로 사용자가 정의한 URL에 대해서 보호합니다.
사용자가 정의한 모든 URL을 기본값으로 보호하도록 하는 설정 클래스를 보겠습니다.

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
class SpringBootWebSecurityConfiguration {

	
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnDefaultWebSecurity
	static class SecurityFilterChainConfiguration {

		@Bean
		@Order(SecurityProperties.BASIC_AUTH_ORDER)
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
			http.formLogin(withDefaults());
			http.httpBasic(withDefaults());
			return http.build();
		}

	}
}
```

defaultSecurityFilterChain() 메서드를 보면 HttpSecurity 파라미터를 사용합니다.  
HttpSecurity를 사용하여 authorizeHttpRequests() 메서드를 호출하여 모든 URL 요청에 대해서 인증 되어야한다고 설정합니다.

http.formLogin() 메서드는 HTML 양식(form 태그)을 통해 제공하는 사용자 이름과 비밀번호를 이용하여  인증을 받을 수 있으며,  
http.httpBasic()를 통해서 REST API 호출로 인증을 받을 수 있습니다.

사용자가 SecurityFilterChain Bean을 지정한다면 @ConditionalOnDefaultWebSecurity에 의해서   
defaultSecurityFilterChain 설정은 무시됩니다. 



### 커스텀 Security 설정

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
				.requestMatchers("/notices", "/contact").permitAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
}
```

@Configuration, @Bean 어노테이션으로 싱글톤 빈 설정을 합니다.  
HttpSecurity를 사용하여 authorizeHttpRequests() 메서드를 호출하여 어떤 URL을 인증해야 하고 접근을 허가하도록 할지 정의합니다.

- requestMatchers() :Http Method 및 url을 입력하여 적용할 URL들을 정의합니다.
- authenticated() : requestMatchers에 정의된 URL들을 인증하도록 합니다.
- permitAll() : requestMatchers에 정의된 URL들을 접근할 수 있도록 합니다.
- anyRequest().permitAll() : 그 외의 모든 요청에 대해서 접근을 허가합니다.
- anyRequest().authenticated() : 그 외의 모든 요청에 대해서 인증하도록 합니다.
- anyRequest().denyAll() : 그 외의 모든 요청에 대해서 접근을 거부합니다.