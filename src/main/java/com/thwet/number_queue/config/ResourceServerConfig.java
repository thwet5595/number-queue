/**
 * 
 */
package com.thwet.number_queue.config;

/**
 * @author Thwet Thwet Mar
 *
 * Dec 14, 2021
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resource_id";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/","/api/requesttoken")
				.permitAll().antMatchers("/**/api/**")
				.access("hasAuthority('ROLE_USER')")
				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

	// TODO: Don't delete.
	/*
	 * @Bean CustomAccessDeniedHandler accessDeniedHandler() { return new
	 * CustomAccessDeniedHandler(); }
	 * 
	 * @Bean CustomAuthenticationEntryPoint authenticationEntryPoint() { return new
	 * CustomAuthenticationEntryPoint(); }
	 */
}
