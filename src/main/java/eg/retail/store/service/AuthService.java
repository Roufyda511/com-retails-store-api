package eg.retail.store.service;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import eg.retail.store.model.AppUser;
import eg.retail.store.security.JWTUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final JWTUtil jwtUtil;

	public String createToken(int userId) {
		AppUser use = new AppUser(String.valueOf(userId), "", new ArrayList<>());
		Authentication authentication = new UsernamePasswordAuthenticationToken(use, "", use.getAuthorities());
		return "bearer "+jwtUtil.createToken(authentication, false);
		
	}
}
