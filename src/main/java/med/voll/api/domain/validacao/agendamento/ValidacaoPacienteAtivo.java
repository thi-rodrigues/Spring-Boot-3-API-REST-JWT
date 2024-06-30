package med.voll.api.domain.validacao.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.PacienteRepository;

@Component
public class ValidacaoPacienteAtivo implements Validacao {

	@Autowired
    private PacienteRepository pacienteRepository;
	
	@Override
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		if (dadosAgendamentoConsulta.idMedico() == null)
			return;
					
		var pacienteAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());
		
		if(Boolean.FALSE.equals(pacienteAtivo))
			throw new ValidacaoException("Consulta não pode ser agendado por paciente excluído!");
		
	}

}
