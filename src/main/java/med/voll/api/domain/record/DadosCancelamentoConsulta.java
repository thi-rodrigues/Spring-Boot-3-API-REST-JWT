package med.voll.api.domain.record;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
		
		@NotNull 
		Long idConsulta,

		@NotNull
		String motivo
		) {
}
