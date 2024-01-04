package org.example.refresh_test.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

public class JwtUtil {
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Value( "${jwt.secret.key}" )
	private String secreKey;

	private Key key;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secreKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String resolveToken( HttpServletRequest request, String header) {
		String bearerToken = request.getHeader(header);
		if( StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(BEARER_PREFIX.length());
		}

		return null;
	}

	public String createAccessToken(String loginId) {
		return this.createToken(loginId, 1);
	}

	public String createRefreshToken(String loginId) {
		return this.createToken(loginId, 24);
	}

	private String createToken(String loginId, long expirationHour ) {
		LocalDateTime expDateTime = LocalDateTime.now().plusHours( expirationHour );
		Date expDate = Date.from(expDateTime.atZone(ZoneId.systemDefault()).toInstant());

		return BEARER_PREFIX +
				Jwts.builder()
						.subject(loginId)
						.issuedAt(new Date())
						.expiration(expDate)
						.signWith(key)
						.compact();
	}
}
