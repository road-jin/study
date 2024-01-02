## 1. UserDetailsService와 UserDetailsManager

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



## 2. UserDetails interface

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
