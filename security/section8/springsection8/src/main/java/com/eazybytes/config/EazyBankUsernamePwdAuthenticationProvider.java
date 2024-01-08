package com.eazybytes.config;

import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eazybytes.domain.Authority;
import com.eazybytes.domain.Customer;
import com.eazybytes.domain.CustomerRepository;

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
			return new UsernamePasswordAuthenticationToken(username, password,
				getGrantedAuthorities(customer.getAuthorities()));
		}

		throw new BadCredentialsException("Invalid password!");
	}

	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		return authorities.stream()
			.map(authority -> (GrantedAuthority)new SimpleGrantedAuthority(authority.getName()))
			.toList();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
