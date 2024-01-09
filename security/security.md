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

------



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



------



## UserDetailsService와 UserDetailsManager

### UserDetailsService & UserDetailsManager 관계

![image-20240102153650553](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240102153650553.png)

UserDetailsService와 UserDetailsManager는 모두 사용자 정보를 제공하는 UserDetails를 구현합니다.

**UserDetailsService**  

사용자 정보를 가져오는 인터페이스입니다.



**UserDetailsManager**  

UserDetailsService를 확장하여 새로운 사용자를 생성하고 기존 사용자를 수정할 수 있는 기능을 제공합니다.

구현체 종류

- InMemoryUserDetailsManager : 메모리 기반으로 유저 정보를 관리합니다.
- JdbcUserDetailsManager : 데이터베이스 기반으로 유저 정보를 관리합니다.
- LdapUserDetialsManager : Ldap 기반으로 유저 정보를 관리합니다.



### UserDetailsManager

**주요 메서드**

- ChangePassowrd() : 사용자 비밀번호를 변경합니다.
- createUser() : 사용자를 생성합니다.
- deleteUser() : 사용자를 삭제합니다.
- loadUserByUsername() : 사용자 이름으로 사용자를 가져옵니다.
- updateUser() : 사용자를 수정합니다.
- userExists() : 해당 사용자가 있는지 확인합니다.



**InMemoryUserDetailsManager**  

인증 제공자가 UserDetailsSerive 인터페이스의 loadUserByUsername() 메서드를 통하여 유저 정보를 가져옵니다.  
해당 유저를 가져오기 위해 생성을 해야하는데 InMemoryUserDetailsManager를 사용하여 유저를 설정하겠습니다.

```java
@Configuration
public class ProjectSecurityConfig {

@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		/*UserDetails admin = User.withDefaultPasswordEncoder()
			.username("admin")
			.password("12345")
			.authorities("admin")
			.build();

		UserDetails user = User.withDefaultPasswordEncoder()
			.username("user")
			.password("12345")
			.authorities("read")
			.build();*/

		UserDetails admin = User.withUsername("admin")
			.password("12345")
			.authorities("admin")
			.build();

		UserDetails user = User.withUsername("user")
			.password("12345")
			.authorities("read")
			.build();
		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
```

User 클래스는 UserDetails의 sub class이며, UserDetails 객체를 사용하여 유저 정보를 관리합니다.  
두가지 방법으로 UserDetails를 사용합니다. 

- User.withDefaultPasswordEncoder()
    - 패스워드를 인코딩이 안전하지 않아 Deprecated가 되어 있지만, 현재 데모 이므로 사용하였습니다.
- User.withUsername()
    - withDefaultPasswordEncoder() 처럼 직접 패스워드를 인코딩 하지 않아 개발자가 PsswordEncoder를 등록해야합니다.

UserDetails 정보

- username : 사용자 이름
- password: 비밀번호
- authorities: 권한



**JdbcUserDetailsManager**  

데이터베이스를 이용하여 사용자 정보를 가져옵니다.  
jdbcUserDeatilsManager를 통해 인증을 해보겠습니다.

```sql
create table users(
  id int not null auto_increment primary key, 
  username varchar(50) not null,
  password varchar(500) not null,
  enabled boolean not null
);

create table authorities (
  id int not null auto_increment primary key, 
  username varchar(50) not null, 
  authority varchar(50) not null
);

insert ignore into users values(null, 'happy', '12345', '1');
insert ignore into authorities values(null, 'happy', 'write');
```

Spring security가 이미 테이블 및 컬럼을 지정하여 쿼리를 생성하였으므로 해당 테이블이 있어야합니다.  
user.ddl 파일에 테이블 및 컬럼이 있으나 학습하기 위해 내용을 조금 바꿔서 테이블을 생성합니다.

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
```

```properties
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/security
    username: root
    password: root
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

JdbcUserDetailsManager를 빈으로 등록하고 application.yml로 데이터베이스 설정 후 서버를 재시작합니다.  
`localhost:8080/myAccount` 접속 후 생성한 사용자(happy) 이름과 패스워드를 입력하면 정상적으로 인증된 것을 확인할 수 있습니다.

jdbcUserDetailsManager의 테이블과 컬럼을 커스텀해서 쓰고 싶은 경우가 있을 때는 UserDetailsService를 구현해야합니다.  
그래서 데이터베이스를 통해서 사용자가 정의한 테이블과 컬럼으로 사용자 정보를 가져올 수 있도록 구현해 보겠습니다.

```sql
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO customer (id, email, pwd, role) VALUES (1, 'mandu@example.com', '12345', 'admin');
```

사용자가 정의한 테이블과 컬럼이며, 이 테이블을 통해 사용자를 생성하고 조회하도록 합니다.

```java
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String pwd;

	private String role;

	protected Customer() {
	}

	public Customer(Long id, String email, String pwd, String role) {
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public String getRole() {
		return role;
	}
}

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);
}
```

Spring Data JPA를 사용하여 데이터베이스에서 사용자 정보를 가져오고 Customer 객체로 자동적으로 변환해주기 위해 위와 같이 작성합니다.

```java
@Service
public class EazyBankUserDetails implements UserDetailsService {

	private final CustomerRepository customerRepository;

	public EazyBankUserDetails(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
		return new User(customer.getEmail(), customer.getPwd(), authorities);
	}
}
```

UserDetailsService 인터페이스를 구현하여 loadUserByUsername 메서드를 통해서 사용자 정보를 가져옵니다.  
CustomerRepository.findByEmail() 메서드를 통해서 이메일로 Customer를 가져오고, 사용자가 없는 경우 예외를 발생시킵니다.  
그리고 UserDetails를 리턴하기 위해 User 인스턴스를 생성합니다.  
Role은 여러개를 가지고 있을 수 있어서 Role을 SimpleGrantedAuthority 클래스 변환 후 List 형식으로   
User 인스턴스에 username, password와 같이 넣어 줍니다.

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.build();
	}
}

@RestController
public class LoginController {

	private final CustomerRepository customerRepository;

	public LoginController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		try {
			customerRepository.save(customer);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Given user details are successfully registered");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An exception occured due to " + ex.getMessage());
		}
	}
}
```

REST API로 회원가입을 할 수 있도록 Controller를 생성하고 Security 설정에서 `/register` URL에 대해서 접근을 허가하고  
Security 기본 값인 CSRF을 비활성화 해줍니다.

**CSRF**

- 크로스 사이트 요청 위조는 웹사이트 취약점 공격의 하나로, 사용자가 자신의 의지와는 무관하게   
    공격자가 의도한 행위를 특정 웹사이트에 요청하게 하는 공격입니다. 
- GET을 제외한 HTTP 메서드에서 상태를 변화시킬 만한 요청은 전부 막도록 되어 있습니다.
- https://www.baeldung.com/spring-security-csrf



### UserDetails interface

사용자 정보를 나타내는 인터페이스입니다.

### 주요 메서드

- getAuthorities() 
    - 사용자의 권한 또는 역할을 가져올 수 있으며, 역할에 의한 접근 로직을 구현할 수 있도록 합니다.
- getPassword()
    - 사용자의 비밀번호를 반환합니다.
- getUsername()
    - 사용자의 이름을 반환합니다.
- isAccountNonExpired()
    - 사용자가 만료 되었는지를 나타내며, 만료가 안된 경우 true이고 만료된 경우 false를 반환합니다.
- isAccountNonLocked()
    - 사용자 계정이 잠겨있는지를 나타내며, 잠기지 않은 경우 true이고 잠긴 경우 false를 반환합니다.
- isCredentialsNonExpired()
    - 사용자의 인증이 만료 되었는지를 나타내며, 만료가 안된 경우 true이고 만료된 경우 false를 반환합니다.
- isEnabled()
    - 사용자의 활성화 여부를 나타냅니다.

### UserDetails와 Authentication의 관계

![image-20240102164208962](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240102164208962.png)

인증 제공자(AuthenticationProvider)가 UserDetailsService를 통해서 인증을 성공하여 UserDetails를 반환 되었으면  
인증 제공자는 UserDetailsService를 활용하여 Authentication을 반환합니다.   
Authentication를 Security Contenxt에 저장하고 Authentication를 통해서 인증이 되었는지를 확인할 수 있습니다.



------



## PasswordEncoders

### Password Authencation

![image-20240106105419575](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240106105419575.png)

Spring Security는 Password를 인증할 때 UserDetailService 클래스의 loadUserByUsername()으로 사용자 정보를 가져온 후   
PasswordEncoder에 의해서 사용자 입력 비밀번호와 서버의 사용자 비밀번호를 검증하는 방식입니다.   
Security의 여러 PasswordEncoder 중 기본 값인 NoOpPasswordEncoder는 순수하게 값으로 비교합니다.



### Encoding VS Encryption VS Hashing

![image-20240106104722069](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240106104722069.png)

비밀번호를 취급할 때 어떤 형식으로 저장하여 검증을 할지에 대해서 여러가지 옵션들이 있습니다.   
그 중에서 인코딩, 암호화, 해싱에 대해 알아보겠습니다.



**Encoding & Decoding**

원본 데이터를 다른 형식으로 변환하는 과정을 인코딩이라고 하며, 누구나 디코딩 과정을 통하여 인코딩 전의 값(원본 데이터)로 변환할 수 있습니다.   
인코딩 예로는 ASCII, BASE64, UNICODE 등이 있습니다.



**Encryption & Decryption**

원본 데이터를 특정 알고리즘과 해당 알고리즘에 비밀 키 값을 고려하여 다른 형식으로 변환하는 과정을 암호화라고 하며,  
비밀 키를 가지고 있으면 복호화를 통하여 암호화 전의 값(원본 데이터)로 변환할 수 있습니다.



**Hashing**

원본 데이터를 수학적 해싱 기능을 사용하여 해시 값으로 변환하는 과정을 해싱이라고 하며,  
해시 값은 원본 데이터로 되돌릴 수 없습니다.  
해시 값과 입력된 데이터를 검증할 때는 입력된 데이터를 해싱하여 해시 값을 얻어서 비교를 하여 검증합니다.



### PasswordEncoder Interface

```java
public interface PasswordEncoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);

    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
```

- encode(charSequence rawPassword)
    - 입력된 패스워드를 인자로 받아서 String 타입의 다른 형식의 값으로 반환합니다.
- matches(CharSequence rawPassword, String encodedPassword)
    - 입력된 패스워드와 loadUserByUsername() 으로 서버에서 가져온 유저 비밀번호를 인자로 받아서 검증을 합니다. 
    - 맞으면 true, 틀리면 false입니다.
- boolean upgradeEncoding(String encodedPassword)
    - 더 나은 보안을 위해서 인코딩된 암호를 다시 인코딩 해야하는 경우 true를 반환하고 그렇지 않으면 false를 반환합니다.
    - 기본 값은 false입니다.



**구현체 종류**

- NoOpPasswordEncoder
    - 유저가 입력한 데이터 그대로 저장되는 형식입니다.
- StandardPasswordEncoder
    - SHA-256 해싱 알고리즘을 사용하여 구현하였으며, 추가적인 보호을 제공하기 위해 전체 시스템 비밀 값을 사용합니다.
    - 보안 적인 이유로 Deprecated가 되었습니다.
- Pbkdf2PasswordEncoder
    - PBKDF2 알고리즘을 사용하여 구현하였습니다.
    - 최근에는 CPU, GPU의 발전에 따라 많은 데이터와 명령을 처리할 수 있는 고성능  GPU 기계를 갖고 있다면  
        해시 값에 무차별 대입 공격을 가하여 비밀번호를 추측할 수 있습니다.
- BCryptPasswordEncoder
    - BCrypt 해싱 알고리즘을 사용하여 구현하였습니다.
    - 작업량 또는 라운드 수에 따라 해싱 알고리즘이 사용하는 CPU 연산이 더 많아집니다.
- SCryptPasswrodEncoder
    - SCrypt 해싱 알고리즘을 사용하여 구현하였습니다.
    - CPU 비용, 메모리 비용을 강제적으로 요구하게 할 수 있습니다.
- Argon2PasswordEncoder
    - Argon2 해싱 알고리즘을 사용하여 구현하였습니다.
    - CPU 비용, 메모리 비용, 다중 스레드를 강제적으로 요구하게 할 수 있습니다.



**PasswordEncoder 등록**

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
```



------



## Authentication Provider

```java
public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;

    boolean supports(Class<?> authentication);
}
```

- authenticate(Authentication authentication)
    - authentication 객체를 이용하여 인증을 합니다.
    - 성공 시 authentication 객체를 반환하고, 실패시 AuthenticationException 예외를 발생시킵니다.
- supports(Class<?> authentication)
    - 해당 authentication을 지원하는 인증 제공자인지 확인하는 메서드이며, 지원하는 경우  true, 아니면 false를 리턴합니다.



### Custom

```java
@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	public EazyBankUsernamePwdAuthenticationProvider(CustomerRepository customerRepository,
		PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		Customer customer = customerRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("No user registered with this details!"));

		if (passwordEncoder.matches(password, customer.getPwd())) {
			List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
			return new UsernamePasswordAuthenticationToken(username, password, authorities);
		}

		throw new BadCredentialsException("Invalid password!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
```

![image-20240106204215048](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240106204215048.png)

![image-20240106203734950](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240106203734950.png)

------



## CORS

![image-20240107153723666](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240107153723666.png)

Cross Origin Resource Sharing의 약자로 보안 상의 이유로 브라우저에서 다른 출처에 대해서 통신을 하지 못하도록 되어 있지만,  
CORS 설정을 통하여 출처가 다른 서버간의 리소스 공유를 허용하도록 합니다.

**출처**

HTTP scheme(HTTP, HTTPS), domain, port 조합을 출처라고  합니다.



### 설정

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}

	private CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return  source;
	}
}
```

**주요 메서드**

- CorsConfiguration : CORS를 설정하는 클래스입니다.
    - setAllowedOrigins() : CORS를 허용할 출처들을 설정합니다.
    - setAllowedMethods() : CORS를 허용할 HTTP Method를 설정합니다.
    - setAllowCredentials() : 사용자 인증이 지원되는 여부를 설정하며, 인증 정보들을 넘기고 받는 것을 의미합니다.
    - setAllowedHeaders() : CORS를 허용할 요청 헤더를 설정합니다.
    - setExposedHeaders() : CORS를 허용할 응답 헤더를 설정합니다.
- UrlBasedCorsConfigurationSource : URL 경로 패턴을 기반으로 CORS를 허용 하도록 하는 클래스입니다.
    - registerCorsConfiguration() : URL 패턴과, CorsConfiguration를 받아 설정합니다.



------



## CSRF

![image-20240107162901135](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240107162901135.png)

![image-20240107162914271](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240107162914271.png)

Cross-site request forgery 약자로 웹사이트 취약점 공격의 하나입니다.  
사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)를 특정 웹사이트에 요청하게 하는 공격을 말합니다.  
Spring security는 기본적으로 GET을 제외한 모든 URL 요청에세 대해서 CSRF 보안 설정이 되어 있습니다.

**예시**

1. netflix.com 서버에 로그인하여 인증 후 넷플릭스 서버는 쿠키를 생성하여 사용자의 인증을 여러번 묻지 않도록 합니다.
2. 공격자는 링크를 클릭 시 넷플릭스에 사용자 정보를 변경하게 하는 공격용 URL을 준비합니다.
3. 사용자는 공격용 URL를 클릭하면, 쿠키를 통하여 사용자의 인증을 하기 때문에 공격자가 이용자의 넥플릭스 사용자 정보를 수정하게 됩니다.



### 해결 방법

![](https://docs.spring.io/spring-security/reference/_images/servlet/exploits/csrf-processing.png)

서버는 무작위 생성 값이 있는 CSRF 토큰을 생성하여 클라이언트에 준 후   
HTTP 요청이 있을 때 마다  요청에서 온 CSRF 토큰과 서버의  CSRF 토큰을 비교합니다.  
값이 일치하지 않으면 HTTP 요청이 거부됩니다.



### 설정

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityContext(context -> context.requireExplicitSave(false))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
}

public class CsrfCookieFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		if (Objects.nonNull(csrfToken.getHeaderName())) {
			response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
		}

		filterChain.doFilter(request, response);
	}
}
```

- ProjectSecurityConfig
    - csrf()
        - csrfTokenRequestHandler() : csrf 토큰 처리 및 HTTP 헤더 또는 요청 파라미터에서 확인하는 역할을 설정합니다.
            - CsrfTokenRequestAttributeHandler
                - CSRF 토큰을  HttpServletRequest의 Attribute로 사융할 수 있게 합니다.
                - 요청에서 토큰 값을 요청 헤더(X-CSRF-TOKEN 또는 X-XSRF-TOKEN 중 하나)   
                    또는 요청 파라미터(기본적으로 _csrf)로 확인합니다.
            - XorCsrfTokenRequestAttributeHandler
                - CsrfTokenRequestAttributeHandler을 확장하였으며, BREACH 공격에 대해 보안 대책을 추가하였습니다.
            - Customizing 
                - CsrfTokenRequestHandler 인터페이스를 구현하여 커스텀 할 수 있습니다.
        - csrfTokenRepository() : 토큰을 저장, 생성, 조회하기 위한 토큰 저장소를 설정합니다.
            - HttpSessionCsrfTokenRepository
                - CSRF 토큰을 HttpSession을 이용하는 저장소입니다.
                - 기본적으로 X-CSRF-TOKEN이라는 HTTP 요청 헤더 또는 요청 매개변수 _csrf에서 토큰을 읽습니다.
            - CookieCsrfTokenRepository
                - 쿠키를 사용하여 자바스크립트 기반 애플리케이션에서 CSRF 토큰을 지속시킬 수 있습니다.
                - XSRF-TOKEN이라는 쿠키에 쓰고 기본적으로 X-XSRF-TOKEN 또는   
                    요청 매개변수 _csrf라는 HTTP 요청 헤더에서 쿠키를 읽습니다.
            - Customizing
                - CsrfTokenRepository 인터페이스를 구현하여 커스텀 할 수 있습니다.
        - ignoringRequestMatchers() :  메서드를 통하여 csrf 보안 설정을 적용하지 않아도 되는 URL들을 명시합니다.
    - sessionManagement()
        - sessionCreationPolicy() : 세션 생성 정책을 설정합니다.
    - addFilterAfter() : 두번째 인자의 필터 이후에 첫번째 인자 필터가 실행하도록 해주는 설정입니다.
        - BasicAuthenticationFilter가 로그인 동작이 하기 때문에 로그인 동작이 완료 된 후에 CSRF 토큰이 생성됩니다.
- CsrfCookieFilter
    - OncePerRequestFilter 확장하여 구현하였으며, OncePerRequestFilter는 요청 당 한번만 처리하는 필터입니다.
    - 생성된 CSRF 토큰을 request Attribute에서 꺼내와서 Header 넣어주는 작업을 합니다.



------



## Authoriztion

권한 부여는 사용자 인증 후에 사용자가 가지고 있는 고유한 권한이나 역할을 가지고 있으며,  
해당 역할이나 권한에 따라 접근할 수 있는 기능이 다릅니다.  
또한 인증에 실패하면 401 에러가 발생하지만, 접근 할 수 없는 기능인 경우는 403 에러가 발생합니다.



### GrantedAuthority interface

```java
public interface GrantedAuthority extends Serializable {

	String getAuthority();
}

public final class SimpleGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String role;

	public SimpleGrantedAuthority(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof SimpleGrantedAuthority sga) {
			return this.role.equals(sga.getAuthority());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.role.hashCode();
	}

	@Override
	public String toString() {
		return this.role;
	}

}

```

권한 혹은 역할을 배정하고 싶은 사람은 GrantedAuthority을 구현한 SimpleGrantedAuthority의 생성자를 통하여   
문자열의 형식으로 역할의 이름을 지정합니다.



![image-20240107211444632](../../../../Users/minkyujin/Library/Application%20Support/typora-user-images/image-20240107211444632.png)
Authentication, UserDetails 모두 GrantedAuthority 인터페이스를 필드로 참조하거나 사용하여 권한이나 역할을 가져오도록 하고 있습니다.



### 사용자가 여러개의 권한(Authority)을 가지도록 설정

```sql
CREATE TABLE `authorities` (
   `id` int NOT NULL AUTO_INCREMENT,
   `customer_id` int NOT NULL,
   `name` varchar(50) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `customer_id` (`customer_id`),
   CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
);

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (1, 'VIEWACCOUNT');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (1, 'VIEWCARDS');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (1, 'VIEWLOANS');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (1, 'VIEWBALANCE');
```

```java
@Entity
@Table(name = "authorities")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

@Entity
public class Customer {

	...

	@JsonIgnore
	@OneToMany(mappedBy="customer", fetch= FetchType.EAGER)
	private Set<Authority> authorities;

  ...

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
}

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	...

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		Customer customer = customerRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("No user registered with this details!"));

		if (passwordEncoder.matches(password, customer.getPwd())) {
			return new UsernamePasswordAuthenticationToken(username, password,   getGrantedAuthorities(customer.getAuthorities()));
		}

		throw new BadCredentialsException("Invalid password!");
	}

	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		return authorities.stream()
			.map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority.getName()))
			.toList();
	}

	...
}

```

authorities 테이블을 만들어서 권한들을 Customer가 권한들을 가질 수 있도록 하였습니다.  
provider를 수정하여 customer.getAuthorities() 메서드로 현재 사용자의 권한들을 조회할 수 있도록 하고  
GrantedAuthority 변환하여 Authentication에 권한들을 넣을 수 있도록 하였습니다.



### 권환(Authority) 설정

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityContext(context -> context.requireExplicitSave(false))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
				.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
				.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
				.requestMatchers("/myCards").hasAuthority("VIEWCARDS")
        //.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
}
```

권한 설정을 하는 경우에는 인증 후에 권한을 확인 하기 때문에 authenticated URL에 빠져도 됩니다.

- hasAuthority() : 특정 권한을 가진 사용자만 해당 API를 접근할 수 있도록 합니다.
- hasAnyAuthority() : 여러가지 권한 중 하나라도 가지고 있는 사용자라면 해당  API를 접근할 수 있도록 합니다.



### 권한(Authority)과 역할(Role)의 차이

권한은 특정 API 및 하나의 작업에 대해서만 접근 제한 하는 것을 의미하며,   
역할은 권한 이나 작업의 그룹을 나타내며 그룹에 대해서 접근 제한 하는 것을 의미합니다.



### 역할(Role) 설정

```SQL
DELETE FROM `authorities`;

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (1, 'ROLE_USER');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (1, 'ROLE_ADMIN');
```

```java
@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityContext(context -> context.requireExplicitSave(false))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				/*.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
				.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
				.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
				.requestMatchers("/myCards").hasAuthority("VIEWCARDS")*/
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myLoans").hasRole("USER")
				.requestMatchers("/myCards").hasRole("USER")
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
}
```

hasRole(), hasAnyRole()는 `ROLE_` 이 앞에 무조건 붙긴 때문에 ROLE_ 뒷 문자열만 적으면 됩니다.

- hasRole() : 특정 역할을 가진 사용자만 해당 API를 접근할 수 있도록 합니다.
- hasAnyRole() : 여러가지 역할 중 하나라도 가지고 있는 사용자라면 해당  API를 접근할 수 있도록 합니다.



------



## Custom Filter

![image-20240107230635521](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240107230635521.png)

```java
@SpringBootApplication
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {

	...
}
/*
Security filter chain: [
  DisableEncodeUrlFilter
  ForceEagerSessionCreationFilter
  ForceEagerSessionCreationFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CorsFilter
  CsrfFilter
  LogoutFilter
  UsernamePasswordAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  BasicAuthenticationFilter
  CsrfCookieFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]
*/
```

```yaml
logging:
  level:
    org.springframework.security.web.FilterChainProxy: DEBUG
```

@EnableWebSecurity 어노테이션과 logging 설정으로 Security 디버깅을 할 수 있으며,
스프링 시큐리티는 위와 같이 19개 정도의 필터를 사용하고 있습니다.

**주의 사항**
[현재 spring boot 3.2.1에서는 버그로 인하여 securityFilterChain Bean 생성이 되지 않습니다.](https://stackoverflow.com/questions/77715151/spring-boot3-2-1-spring-security-config6-2-1-upgrade-issue-error-creating-b)
spring boot 3.20으로 다운그레이드 시 정상적으로 동작됩니다.



### Filter

```tex
// 흐름
HTTP 요청 → WAS → 필터 → 서블릿 → 컨트롤러

// 제한
HTTP 요청 → WAS → 필터(서블릿 호출 x)

// 체인
HTTP 요청 → WAS → 필터 → 필터2 → 필터3 → 서블릿 → 컨트롤러
출처: https://roadj.tistory.com/15 [나의 구름낀 조각들:티스토리]
```

서블릿이 지원하는 기능으로 서블릿이 호출하기 전에 필터 로직이 실행되며,  
특정 URL 패턴을 사용하여 특정 URL 요청에 대하여 적용할 수 있습니다.  
필터는 로직에 의해서 적절하지 않은 요청이라고 판단할 경우 서블릿 호출을 하지 않습니다.  
그리고 필터는 체인으로 구성되는데, 중간에 필터를 자유롭게 추가할 수 있습니다.  
참고로 서블릿은 spring의 DispatcherServlet 입니다.

```java
public interface Filter {

    public default void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException;

    public default void destroy() {}
}
```

필터 인터페이스를 구현하고 등록하면 서블릿 컨테이너가 필터를 싱글톤 객체로 생성하고, 관리합니다.

- init : 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출합니다.
- doFilter : 요청이 올 떄 마다 해당 메서드가 호출됩니다.
    - ServletRequest : EndPoint로 부터 오는  HTTP 요청입니다.
    - ServletResponse : EndPoint로 보내는 HTTP 응답입니다.
    - FilterChain : 정의된 순서대로 실행되는 필터들의 집합입니다.
- destory : 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출합니다.



### Spring Security에 Custom Filter 주입 방법

HttpSecurity 클래스의 addFilterBefore, addFilterAfter, addFilterAt 메서드를 통해서 Custom Filter를 주입할 수 있습니다.

**addFilterBefore(filter, class)**

![image-20240108112949980](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240108112949980.png)

```java
public class RequestValidationBeforeFilter implements Filter {

	public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
	private Charset credentialsCharset = StandardCharsets.UTF_8;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {
		HttpServletRequest req = (HttpServletRequest)servletRequest;
		HttpServletResponse res = (HttpServletResponse)servletResponse;
		String header = req.getHeader(AUTHORIZATION);

		if (Objects.nonNull(header)) {
			header = header.trim();

			if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
				try {
					byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
					byte[] decoded = Base64.getDecoder().decode(base64Token);
					String token = new String(decoded, credentialsCharset);
					int delim = token.indexOf(":");
					
					if (delim == -1) {
						throw new BadCredentialsException("Invalid basic authentication token");
					}
					
					String email = token.substring(0, delim);
					
					if (email.toLowerCase().contains("test")) {
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}
				} catch (IllegalArgumentException e) {
					throw new BadCredentialsException("Failed to decode basic authentication token");
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}
}

@Configuration
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityContext(context -> context.requireExplicitSave(false))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myLoans").hasRole("USER")
				.requestMatchers("/myCards").hasRole("USER")
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
  
  ...
}
/*
Security filter chain: [
  DisableEncodeUrlFilter
  ForceEagerSessionCreationFilter
  ForceEagerSessionCreationFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CorsFilter
  CsrfFilter
  LogoutFilter
  UsernamePasswordAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  RequestValidationBeforeFilter
  BasicAuthenticationFilter
  CsrfCookieFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]
*/
```

Anguler에서 Authorization Header에 `Basic email:password` 로 넣어서 서버에 보내주며,   
`email:password` base64로 encoding 되어 있습니다.  
RequestValidationBeforeFilter 클래스는 Authorization Header에 값이 제대로 있는지 검증하는 필터입니다.  
이메일에 test가 포함될 시에는 응답 메시지에 400 상태코드를 반환하게 합니다.   
addFilterBefore(filter, class) 메서드로 두번째 인자 클래스 이전에 해당 필터가 실행 될 수 있도록 합니다.



**addFilterAfter(filter, class)**

![image-20240108113003569](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240108113003569.png)

```java
public class AuthoritiesLoggingAfterFilter implements Filter {

	private final Logger LOG =
		Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			LOG.info("User " + authentication.getName() + " is successfully authenticated and "
				+ "has the authorities " + authentication.getAuthorities().toString());
		}
		chain.doFilter(request, response);
	}

}

@Configuration
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityContext(context -> context.requireExplicitSave(false))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myLoans").hasRole("USER")
				.requestMatchers("/myCards").hasRole("USER")
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
  
  ...
}

/*
Security filter chain: [
  DisableEncodeUrlFilter
  ForceEagerSessionCreationFilter
  ForceEagerSessionCreationFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CorsFilter
  CsrfFilter
  LogoutFilter
  UsernamePasswordAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  RequestValidationBeforeFilter
  BasicAuthenticationFilter
  CsrfCookieFilter
  AuthoritiesLoggingAfterFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]
*/
```

LoggingFilter를 추가하여 어떤 유저가 인증이 성공적이고, 해당 유저는 어떠한 권한들을 가졌는지 로깅하는 필터입니다.  
addFilterAfter(filter, class) 메서드로 두번째 인자 클래스 이후에 해당 필터가 실행 될 수 있도록합니다.



**addFilterAt(filter, class)**

![image-20240108113228410](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240108113228410.png)

```java
public class AuthoritiesLoggingAtFilter implements Filter {

	private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		LOG.info("Authentication Validation is in progress");
		chain.doFilter(request, response);
	}

}

@Configuration
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityContext(context -> context.requireExplicitSave(false))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
			.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myLoans").hasRole("USER")
				.requestMatchers("/myCards").hasRole("USER")
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
  
  ...
    
}
/*
Security filter chain: [
  DisableEncodeUrlFilter
  ForceEagerSessionCreationFilter
  ForceEagerSessionCreationFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CorsFilter
  CsrfFilter
  LogoutFilter
  UsernamePasswordAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  RequestValidationBeforeFilter
  AuthoritiesLoggingAtFilter
  BasicAuthenticationFilter
  CsrfCookieFilter
  AuthoritiesLoggingAfterFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]
*/
```

addFilterAt(filter, class) 메서드로 두번째 인자 클래스 이전 이나 이후 둘 중 해당 필터가 무작위 순서로 실행됩니다.
따라서 addFilterAt 행동에 대해서 매우 주의해야합니다.



### Spring이 제공하는 추상 클래스

Custom Filter를 만들기 전에 Spring이 프로그래머에게 필요한 기능들을 미리 만들어서 보다 편하게 Filter를 만들 수 있드록 하는 추상 클래스입니다.

**OncePerRequestFilter**

필터가 반드시 요청 당 한 번만 실행되어야 하는 경우에는 사용하는 추상 클래스입니다.



**GenericFilterBean**

외부 환경 변수 및 서블릿 컨텍스트 매개변수를 제공하는 추상 클래스입니다.



------



## JWT를 사용한 토큰 기반 인증

### JWT

![image-20240108211050632](https://raw.githubusercontent.com/road-jin/imagebox/main/images/image-20240108211050632.png)

정보를 JSON으로 안전하게 전송하기 위한 간결하고 독립적인 방법을 정의하는 개방형 표준(RFC 7519)입니다.
해당 정보는 디지털 서명이 되어 있어 신뢰할 수 있습니다.

**구조**

{Header}.{Payload}.{Signature} 로 되어 있습니다.

- Header : JWT의 서명 알고리즘과 유형으로 되어 있으며, Base64로 인코딩되어 있습니다.
- Payload : Claim(추가 데이터)를 포함하며, 등록, 공개 및 비공개 클레임 세가지 유형이 있습니다.
    - Registered Claims : 이미 공통적으로 정의된 클레임이며, 의무적으로 사용하거나 필수 사항은 아닙니다.
        - iss(Issuer) Claim : 발급자를 나타냅니다.
        - sub(Subject) Claim : 주제를 나타냅니다.
        - aud(Audience) Claim : 수신자를 나타냅니다.
        - exp(Expiration Time) Claim : 만료 시간을 나타냅니다. 현재 시간보다 커야 되며, NumbericDate(숫자)여야 합니다.
        - nbf(Not Before) Claim : NumbericDate(숫자)여야 합니다. 해당 날짜가 지나기 전까지는 토큰이 처리되지 않습니다.
        - iat(Issued At) Claim : 발행 시간을 나타냅니다. NumbericDate(숫자)여야 합니다.
        - jti(JWT ID) Claim : jwt의 고유 식별자를 나타냅니다.
    - Public Claims : 충돌 방지를 위해 [JSON 웹 토큰 레지스트리](https://www.iana.org/assignments/jwt/jwt.xhtml)에 정의된 클레임을 사용하거나, URI 형식의 키를 지정하여 사용합니다.
    - Private Claims : 사용에 동의한 당사자 간에 정보를 공유하기 위해 커스텀 클레임으로 등록되거나 공개된 클레임 아닙니다.
- Signature : 서명 부분을 생성하려면 인코딩된 Header, 인코딩 된 Payload, 비밀키를 가지고서 Header에 지정된 알고리즘으로 생성합니다.
    - 알고리즘이 HMAC SHA256 일 경우 다음과 같습니다.  
        HMACSHA256(base64UrlEncode(header) + "." +  base64UrlEncode(payload),  secret)

### JWT 사용

```java
<gradle.build>
  
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'com.eazybytes'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'io.jsonwebtoken:jjwt-api:0.12.3'

    runtimeOnly 'com.mysql:mysql-connector-j'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

public interface SecurityConstants {

	public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
	public static final String JWT_HEADER = "Authorization";

}

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (Objects.nonNull(authentication)) {
			Date now = new Date();
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			String jwt = Jwts.builder()
				.setIssuer("Eazy Bank")
				.setSubject("JWT Token")
				.claim("username", authentication.getName())
				.claim("authorities", populateAuthorities(authentication.getAuthorities()))
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + 30_000_000))
				.signWith(key).compact();
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/user");
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
	}
}

@Configuration
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
			.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
      .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
      .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myLoans").hasRole("USER")
				.requestMatchers("/myCards").hasRole("USER")
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}

	private CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
		configuration.setExposedHeaders(List.of("Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return  source;
	}
  
  ...
}

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
		if (Objects.nonNull(jwt)) {
			try {
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				Claims claims = Jwts.parser()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
				String username = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
					AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid Token received!");
			}

		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().equals("/user");
	}
}
```

- gradle.build
    -  jwt 사용을 위해 jjwt 관련한 라이브러리 의존성을 추가하였습니다.
- SecurityConstants
    - 시큐리티에 대한 상수 모음 인터페이스
    - JWT_KEY : JWT 서명을 위한 비밀키
- JWTTokenGeneratorFilter
    - JWT를 생성하기 위한 필터
    - OncePerRequestFilter 상속 받아 요청당 한번만 실행됩니다.
    - shouldNotFilter 메서드를 재정의하여 USER URI 이외에는 해당 필터가 실행되지 않도록 설정합니다.
- JWTTokenValidatorFilter
    - JWT를 검증하기 위한 필터
    - OncePerRequestFilter 상속 받아 요청당 한번만 실행됩니다.
    - shouldNotFilter 메서드를 재정의하여 USER URI 이외에는 해당 필터가 실행 되도록 설정합니다.
- ProjectSecurityConfig
    - SessionCreationPolicy.STATELESS 설정으로 Session을 설정하지 않도록 합니다.
    - BasicAuthenticationFilter 이후에 JWTTokenGeneratorFilter 실행 되도록 설정합니다



------



## Method Level Security

메서드에 어노테이션을 붙여 권한 검증을 부여할 수 있도록 합니다.  
`@EnableMethodSecurity` 어노테이션을 이용하여  `@PreAuthorize` , `@PostAuthorize`, `@Secured`, `@RoleAllowed`사용할 수 있게 합니다.

### @PreAuthorize

```java
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(cors -> cors.configurationSource(configurationSource()))
			.csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
			.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
			.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
        //.requestMatchers("/myLoans").hasRole("USER")
				.requestMatchers("/myLoans").authenticated()
				.requestMatchers("/myCards").hasRole("USER")
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
				.requestMatchers("/notices", "/contact", "/register").permitAll()
				.anyRequest().denyAll())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.build();
	}
	
	...
}

public interface LoanRepository extends CrudRepository<Loans, Long> {

	@PreAuthorize("hasRole('USER')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
```

메서드를 실행하기 전에 인가(권한 검증)를 하며 권한이 있는 경우에만 해당 메서드가 실행됩니다.  
Role이 ROLE_USER인 경우에만 findByCustomerIdOrderByStartDtDesc() 메서드가 실행 되도록 합니다.  
[SpEL](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#using-authorization-expression-fields-and-methods) + [SecurityExpressionRoot 클래스의 메서드](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionRoot.html) 사용을 통해서 권한 조건을 줄 수 있습니다.



### @PostAuthorize

```java
public interface LoanRepository extends CrudRepository<Loans, Long> {

	//@PreAuthorize("hasRole('USER')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}

@RestController
public class LoansController {

	private final LoanRepository loanRepository;

	public LoansController(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@GetMapping("/myLoans")
	@PostAuthorize("hasRole('ROOT')")
	public List<Loans> getLoanDetails(@RequestParam int id) {
		return loanRepository.findByCustomerIdOrderByStartDtDesc(id);
	}
}
```

메서드를 실행 후에 인가(권한 검증)를 하며 권한이 있는 경우에만 메서드 결과를 반환합니다.  
`/myLoans`가 실행은 되었지만 결과를 엔드포인트에게 전달 하지 않고 403 상태코드를 반환합니다.



### @PreFilter

```java
@RestController
public class ContactController {

	private final ContactRepository contactRepository;

	public ContactController(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@PostMapping("/contact")
	@PreFilter("filterObject.contactName != 'Test'")
	public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
		Contact contact = contacts.get(0);
		contact.setContactId(getServiceReqNumber());
		contact.setCreateDt(LocalDateTime.now());
		contact = contactRepository.save(contact);
		List<Contact> returnContacts = new ArrayList<>();
		returnContacts.add(contact);
		return returnContacts;
	}
	
	...
}
```

사전에 Collection 파라미터를 필터링을 한 후에 메서드를 실행 할 수 있도록 합니다.  
contacts에서 contactName이 Test 아닌 객체들만 파라미터 담깁니다.

### @PostFilter

```java
@RestController
public class ContactController {

	private final ContactRepository contactRepository;

	public ContactController(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@PostMapping("/contact")
	//@PreFilter("filterObject.contactName != 'Test'")
	@PostFilter("filterObject.contactName != 'Test'")
	public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
		Contact contact = contacts.get(0);
		contact.setContactId(getServiceReqNumber());
		contact.setCreateDt(LocalDateTime.now());
		contact = contactRepository.save(contact);
		List<Contact> returnContacts = new ArrayList<>();
		returnContacts.add(contact);
		return returnContacts;
	}
	
	...
}
```

메서드 실행 후 Collcetion 반환 객체를 필터링을 한 후에 반환 하도록 합니다.  
returnContacts List에서 contactName이 Test가 아닌 객체들만 반환 합니다.