package com.eazybytes.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eazybytes.filter.AuthoritiesLoggingAfterFilter;
import com.eazybytes.filter.AuthoritiesLoggingAtFilter;
import com.eazybytes.filter.CsrfCookieFilter;
import com.eazybytes.filter.JWTTokenGeneratorFilter;
import com.eazybytes.filter.JWTTokenValidatorFilter;
import com.eazybytes.filter.RequestValidationBeforeFilter;

@Configuration
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
				.requestMatchers("/notices", "/contact", "/register", "/error").permitAll()
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

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
