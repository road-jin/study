package com.eazybytes.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eazybytes.domain.Customer;
import com.eazybytes.domain.CustomerRepository;

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
