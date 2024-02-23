package eg.retail.store.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eg.retail.store.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@EnableConfigurationProperties({ SecurityProperties.class })
@Log4j2
@RequiredArgsConstructor
public class JWTUtil {

	private static final String LANGUAGE_KEY = "defaultUserLanguage";
	private static final String ROLES_KEY = "roles";
	private static final String ROLE_GROUPS_KEY = "roleGroups";

	private final ObjectMapper objectMapper;
	private final SecurityProperties securityProperties;

	public String createToken(Authentication authentication, boolean isRefresh) {

		long currentTimeInMillis = System.currentTimeMillis();

		long tokenValidityInMillis = TimeUnit.SECONDS
				.toMillis(!isRefresh ? securityProperties.getJwtAuth().getTokenValidity()
						: securityProperties.getJwtAuth().getRefreshTokenValidity());

		Date validity = new Date(currentTimeInMillis + tokenValidityInMillis);

		AppUser appUser = (AppUser) authentication.getPrincipal();

		return Jwts.builder().setSubject(authentication.getName())
				.claim(ROLES_KEY,
						appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(currentTimeInMillis)).setExpiration(validity).setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS256, securityProperties.getJwtAuth().getSecretKey()).compact();
	}

	public Authentication getAuthentication(String token) {

		List<? extends GrantedAuthority> authorities = new ArrayList<>();

		List<String> roles = getRoles(token);

		if (roles != null) {
			authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}

		AppUser principal = new AppUser(getSubject(token), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public  boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(securityProperties.getJwtAuth().getSecretKey()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.warn("Invalid JWT signature. Token: {}", authToken);
			log.trace("Invalid JWT signature trace: ", e);
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token. Token: {}", authToken);
			log.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			log.warn("Expired JWT token. Token: {}", authToken);
			log.trace("Expired JWT token trace: ", e);
		} catch (UnsupportedJwtException e) {
			log.warn("Unsupported JWT token. Token: {}", authToken);
			log.trace("Unsupported JWT token trace: ", e);
		} catch (IllegalArgumentException e) {
			log.warn("JWT token compact of handler are invalid. Token: {}", authToken);
			log.trace("JWT token compact of handler are invalid trace: ", e);
		}
		return false;
	}

	public String getSubject(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public Date getIssuedAt(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public String getId(String token) {
		return getClaimFromToken(token, Claims::getId);
	}

	public Object getLanguage(String token) {
		return getClaims(token).get(LANGUAGE_KEY);
	}

	public List<String> getRoles(String token) {
		Claims claims = getClaims(token);

		List<String> roles = objectMapper.convertValue(claims.get(ROLES_KEY), new TypeReference<List<String>>() {
		});
		if (CollectionUtils.isEmpty(roles) && claims.get(ROLE_GROUPS_KEY) != null) {
			return new ArrayList<>(objectMapper
					.convertValue(claims.get(ROLE_GROUPS_KEY), new TypeReference<Map<String, List<String>>>() {
					}).keySet());
		} else {
			return roles;
		}
	}

	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(securityProperties.getJwtAuth().getSecretKey()).parseClaimsJws(token)
				.getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getClaims(token);
		return claimsResolver.apply(claims);
	}

	public String resolveToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		return extractToken(authorization);
	}

	public String extractToken(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && bearerToken.toLowerCase().startsWith("bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}

	
	public static void main(String arg[]) {
		
	}

}
