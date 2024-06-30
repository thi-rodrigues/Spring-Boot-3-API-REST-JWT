package med.voll.api.domain.record;

import java.time.LocalDateTime;

import med.voll.api.domain.Consulta;

public record DadosDetalhamentoConsulta(Long id, Long medico, Long idPaciente, LocalDateTime data) {

	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
	}

}
