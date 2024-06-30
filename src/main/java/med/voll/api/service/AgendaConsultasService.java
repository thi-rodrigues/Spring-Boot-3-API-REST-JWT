package med.voll.api.service;

import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.domain.record.DadosCancelamentoConsulta;
import med.voll.api.domain.record.DadosDetalhamentoConsulta;

public interface AgendaConsultasService {

	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta agendamentoConsulta);

	void cancelar(DadosCancelamentoConsulta dadosCancelamentoConsulta);
}
