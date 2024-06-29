package med.voll.api.exceptions;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = -5464926359265667624L;
	
	public ValidacaoException(String mensagem) {
		super(mensagem);
	}

}
