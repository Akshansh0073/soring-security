package com.akshansh.learn_spring_security.basic;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SpringSecurityConfig {

	@Bean 
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//		http.formLogin(withDefaults());
		http.sessionManagement(
						session ->
							session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.withUsername("akshansh").password("{noop}password").roles("Admin").build();
//		UserDetails user = User.withUsername("divyansh").password("{noop}password").roles("User").build();
//		
//		return new InMemoryUserDetailsManager(admin,user);
//	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		UserDetails admin = User.withUsername("akshansh")
				.password("dummy").passwordEncoder(str -> passwordEncoder().encode(str))
				.roles("Admin","User")
				.build();
		UserDetails user = User.withUsername("divyansh")
				.password("dummy").passwordEncoder(str -> passwordEncoder().encode(str))
				.roles("User").build();
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		return jdbcUserDetailsManager;
	}
	

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
}
