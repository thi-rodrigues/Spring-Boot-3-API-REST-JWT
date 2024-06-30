package med.voll.api.domain.validacao.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidacaoPacienteSemOutraConsultaNoDia implements Validacao {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Override
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var primeiroHorario = dadosAgendamentoConsulta.data().withHour(7);
		var ultimoHorario = dadosAgendamentoConsulta.data().withHour(18);
		
		var pacientePossuiOutraConsultaNoMesmoDia = consultaRepository.existsByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente(), primeiroHorario, ultimoHorario);
		
		if (Boolean.TRUE.equals(pacientePossuiOutraConsultaNoMesmoDia))
			throw new ValidacaoException("Paciente já possui uma consulta agendada no mesmo dia");
	}

}
