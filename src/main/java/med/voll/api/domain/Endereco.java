package med.voll.api.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.record.EnderecoRecord;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	
	private String logradouro;
	private String bairro;
	private String cep;
	private String cidade;
	private String uf;
	private String complemento;
	private String numero;
	
	public Endereco(EnderecoRecord endereco) {
		this.logradouro = endereco.logradouro();
		this.bairro = endereco.bairro();
		this.cep = endereco.cep();
		this.cidade = endereco.cidade();
		this.uf = endereco.uf();
		this.complemento = endereco.complemento();
		this.numero = endereco.numero();
	}
	
}
