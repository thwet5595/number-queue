/**
 * 
 */
package com.thwet.number_queue.controller;

/**
 * @author Thwet Thwet Mar
 *
 * Dec 14, 2021
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thwet.number_queue.common.Configurations;
import com.thwet.number_queue.config.CustomPasswordEncoder;
import com.thwet.number_queue.model.User;
import com.thwet.number_queue.repo.UserRepository;
import com.thwet.number_queue.service.UserService;

@RequestMapping("api")
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	CustomPasswordEncoder passwordEncoder;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	HttpServletRequest httpServletRequest;

	private final RestTemplate restTemplate = new RestTemplate();

	@PostMapping("/requesttoken")
	public ResponseEntity<?> login(@RequestBody User userData,
			@RequestHeader(value = "Authorization") String authorization) {

		LOGGER.debug("==================Enter Requesttoken API==================");

		Map<String, String> resultMap = new HashMap<>();

		// RestTemplate restTemplate = new RestTemplate();
		Properties config = Configurations.getConfigs();
		String grant_type = config.getProperty("security.oauth2.client.grant_type");
		String client_id = config.getProperty("security.oauth2.client.id");

		try {

			userService.authenticate(userData.getEmail(), userData.getPassword());

			LOGGER.debug("==================Authenticate==================");

			// Check user is already login
			User user = userService.findByEmail(userData.getEmail());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", authorization);

			MultiValueMap<String, String> formVars = new LinkedMultiValueMap<String, String>();
			formVars.add("grant_type", grant_type);
			formVars.add("username", userData.getEmail());
			formVars.add("password", userData.getPassword());
			formVars.add("client_id", client_id);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(formVars,
					headers);

			LOGGER.debug("==================Token URL==================" + httpServletRequest.getScheme() + "://"
					+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/oauth/token");

			ResponseEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:8086/oauth/token", request,
					String.class);

			LOGGER.debug("==================Response==================" + response.getBody());
			if (response != null && response.getStatusCode() == HttpStatus.OK) {
				ObjectMapper mapper = new ObjectMapper();

				try {
					JsonNode root = mapper.readTree(response.getBody());
					String accessToken = root.path("access_token").asText();
					String token_type = root.path("token_type").asText();
					String refresh_token = root.path("refresh_token").asText();
					String expires_in = root.path("expires_in").asText();
					String scope = root.path("scope").asText();
					String jti = root.path("jti").asText();
					String sessionId = UUID.randomUUID().toString();

					resultMap.put("access_token", accessToken);
					resultMap.put("token_type", token_type);
					resultMap.put("refresh_token", refresh_token);
					resultMap.put("expires_in", expires_in);
					resultMap.put("scope", scope);
					resultMap.put("jti", jti);
					resultMap.put("sessionId", sessionId);

				} catch (JsonProcessingException e) {
					LOGGER.error("Error! Authorisation Token Parsing Failed" + e);
				} catch (IOException e) {
					LOGGER.error("Error! Generating Authorisation Token Failed" + e);
				}

			}

		} catch (Exception e) {
			LOGGER.error("Error! Request Token => " + e.getMessage());
			if (e instanceof BadCredentialsException) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			} else if (e instanceof LockedException) {
				return new ResponseEntity<>("Your Account is Locked! Please contact the bank.", HttpStatus.LOCKED);
			}
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}