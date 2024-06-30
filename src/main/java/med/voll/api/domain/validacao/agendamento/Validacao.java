package med.voll.api.domain.validacao.agendamento;

import med.voll.api.domain.record.DadosAgendamentoConsulta;

public interface Validacao {

	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta);
}
