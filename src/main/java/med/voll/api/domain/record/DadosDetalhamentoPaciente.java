package med.voll.api.domain.record;

import med.voll.api.domain.Endereco;
import med.voll.api.domain.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String cpf, String telefone,
		Endereco endereco) {

	public DadosDetalhamentoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(),
				paciente.getEndereco());
	}
}
