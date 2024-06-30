package med.voll.api.domain.validacao.agendamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidacaoHorarioAntecedencia implements Validacao {
	
	@Override
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var minutes = Duration.between(LocalDateTime.now(), dadosAgendamentoConsulta.data()).toMinutes();

		if (minutes < 30)
			throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos!");
	}
}
