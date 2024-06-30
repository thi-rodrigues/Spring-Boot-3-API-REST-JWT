package med.voll.api.domain.validacao.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidacaoMedicoComOutraConsultaNoMesmoHorario implements Validacao {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Override
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());
		
		if (Boolean.TRUE.equals(medicoPossuiOutraConsultaNoMesmoHorario))
			throw new ValidacaoException("Médico já possui outra consulta agendada no mesmo horário");
	}

}
