package med.voll.api.security.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import med.voll.api.security.Usuario;
import med.voll.api.security.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	@Override
	public String gerarToken(Usuario usuario) {
		try {
		    var algoritimo = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("API Voll.med")
		        .withSubject(usuario.getLogin())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritimo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token JWT", exception);
		}
	}
	
	@Override
	public String validarToken(String tokenJWT) {
		try {
			var algoritimo = Algorithm.HMAC256(secret);
		    return JWT.require(algoritimo)
		        .withIssuer("API Voll.med")
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		} catch (JWTVerificationException exception){
		    throw new RuntimeException("Token JWT inválido ou expirado!");
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // TODO
	}

}
