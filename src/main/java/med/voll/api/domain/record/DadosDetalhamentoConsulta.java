package med.voll.api.domain.record;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long medico, Long idPaciente, LocalDateTime data) {

}
