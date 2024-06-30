package med.voll.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.domain.record.DadosCancelamentoConsulta;
import med.voll.api.domain.record.DadosDetalhamentoConsulta;
import med.voll.api.domain.validacao.agendamento.Validacao;
import med.voll.api.domain.validacao.consulta.ValidadorCancelamentoDeConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.service.AgendaConsultasService;

@Service
public class AgendaConsultasServiceImpl implements AgendaConsultasService {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private List<Validacao> validadores;
	
	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

	@Override
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta agendamentoConsulta) {
		if (agendamentoConsulta.idMedico() != null && !medicoRepository.existsById(agendamentoConsulta.idMedico()))
			throw new ValidacaoException("ID do médico não existe");
		
		if (!pacienteRepository.existsById(agendamentoConsulta.idPaciente()))
			throw new ValidacaoException("ID do paciente não existe");
		
		validadores.forEach(v -> v.validar(agendamentoConsulta));
			
		var medico = escolherMedico(agendamentoConsulta);
		
		if (medico == null)
			throw new ValidacaoException("Não existe médico disponível nessa data!");
			
		var paciente = pacienteRepository.getReferenceById(agendamentoConsulta.idPaciente());
		var consulta = new Consulta(null, medico, paciente, agendamentoConsulta.data(), null, true);
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta agendamentoConsulta) {
		if (agendamentoConsulta.idMedico() != null)
			return medicoRepository.getReferenceById(agendamentoConsulta.idMedico());
		
		if (agendamentoConsulta.especialidade() == null)
			throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
		
		return medicoRepository.esclherMedicoAleatorioLivreNaData(agendamentoConsulta.especialidade(), agendamentoConsulta.data());
	}
	
	@Override
	public void cancelar(DadosCancelamentoConsulta dados) {
	    if (!consultaRepository.existsById(dados.idConsulta())) {
	        throw new ValidacaoException("Id da consulta informado não existe!");
	    }

	    validadoresCancelamento.forEach(v -> v.validar(dados));

	    var consulta = consultaRepository.getReferenceById(dados.idConsulta());
	    consulta.cancelar(dados.motivo());
	}

}
