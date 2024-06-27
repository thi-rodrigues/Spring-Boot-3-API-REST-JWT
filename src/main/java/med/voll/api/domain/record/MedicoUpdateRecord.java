package med.voll.api.domain.record;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enums.Especialidade;

public record MedicoUpdateRecord(@NotNull Long id, String nome, EnderecoRecord enderecoRecord, Especialidade especialidade, Boolean ativo) {
	

}
