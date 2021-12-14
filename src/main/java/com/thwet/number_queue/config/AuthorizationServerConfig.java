/**
 * 
 */
package com.thwet.number_queue.config;

/**
 * @author Thwet Thwet Mar
 *
 * Dec 14, 2021
 */

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.thwet.number_queue.common.Configurations;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	public CustomPasswordEncoder passwordEncoder;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		Properties config = Configurations.getConfigs();
		String jwt_key = config.getProperty("security.oauth2.client.token.jwt_key");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwt_key);
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		Properties config = Configurations.getConfigs();
		String cliendId = config.getProperty("security.oauth2.client.id");
		String cliendPassword = config.getProperty("security.oauth2.client.password");
		String grant_type = config.getProperty("security.oauth2.client.grant_type");
		String authorization_code = config.getProperty("security.oauth2.client.authorization_code");
		String refresh_token = config.getProperty("security.oauth2.client.refresh_token");
		String implicit = config.getProperty("security.oauth2.client.implicit");
		String scope_read = config.getProperty("security.oauth2.client.scope.read");
		String scope_write = config.getProperty("security.oauth2.client.scope.write");
		String scope_trust = config.getProperty("security.oauth2.client.scope.trust");
		String accessTokenValiditySeconds = config.getProperty("security.oauth2.client.accessTokenValiditySeconds");
		String refreshTokenValiditySeconds = config.getProperty("security.oauth2.client.refreshTokenValiditySeconds");

		configurer.inMemory().withClient(cliendId).secret(passwordEncoder.encode(cliendPassword))
				.authorizedGrantTypes(grant_type, authorization_code, refresh_token, implicit)
				.scopes(scope_read, scope_write, scope_trust)
				.accessTokenValiditySeconds(Integer.valueOf(accessTokenValiditySeconds))
				.refreshTokenValiditySeconds(Integer.valueOf(refreshTokenValiditySeconds));
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
				.accessTokenConverter(accessTokenConverter());
	}
}
