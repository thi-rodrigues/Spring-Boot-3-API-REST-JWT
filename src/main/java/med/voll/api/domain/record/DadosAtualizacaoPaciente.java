package med.voll.api.domain.record;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente(@NotNull Long id, String nome, String telefone, EnderecoRecord endereco) {
}
