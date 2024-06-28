package med.voll.api.security.service;

import med.voll.api.security.Usuario;

public interface TokenService {

	public String gerarToken(Usuario usuario);

	public String validarToken(String tokenJWT);
}
