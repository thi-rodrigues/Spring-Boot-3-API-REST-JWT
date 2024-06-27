package med.voll.api.domain.record;

import med.voll.api.domain.Medico;
import med.voll.api.domain.enums.Especialidade;

public record MedicoListRecord(Long id, String nome, String email, String crm, Especialidade especialidade) {
	
	public MedicoListRecord(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}

}
