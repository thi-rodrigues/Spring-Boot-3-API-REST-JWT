package med.voll.api.domain.validacao;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;

@Component
public class ValidacaoHorarioClinica implements Validacao {

	@Override
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var domingo = dadosAgendamentoConsulta.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var aberturaClinica = dadosAgendamentoConsulta.data().getHour() < 7;
		var fechamentoClinica = dadosAgendamentoConsulta.data().getHour() > 18;
		
		if (domingo || aberturaClinica || fechamentoClinica)
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica!");
	}
}
