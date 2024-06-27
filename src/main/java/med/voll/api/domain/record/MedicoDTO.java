package med.voll.api.domain.record;

import lombok.Data;
import med.voll.api.domain.Endereco;
import med.voll.api.domain.enums.Especialidade;

@Data
public class MedicoDTO {
	
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	private Boolean ativo;
	private Especialidade especialidade;
	private Endereco endereco;
}
