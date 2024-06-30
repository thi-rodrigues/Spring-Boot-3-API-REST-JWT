package med.voll.api.domain.validacao.consulta;

import med.voll.api.domain.record.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);

}