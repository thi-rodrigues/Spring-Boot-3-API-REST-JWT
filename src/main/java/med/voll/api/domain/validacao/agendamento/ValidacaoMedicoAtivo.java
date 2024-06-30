package med.voll.api.domain.validacao.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.MedicoRepository;

@Component
public class ValidacaoMedicoAtivo implements Validacao {

	@Autowired
    private MedicoRepository medicoRepository;
	
	@Override
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		if (dadosAgendamentoConsulta.idMedico() == null)
			return;
					
		var medicoAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());
		
		if(Boolean.FALSE.equals(medicoAtivo))
			throw new ValidacaoException("Consulta não pode ser agendado com médico excluído!");
		
	}

}
