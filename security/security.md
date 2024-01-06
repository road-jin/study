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

