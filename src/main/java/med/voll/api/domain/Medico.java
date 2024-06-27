package med.voll.api.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.enums.Especialidade;
import med.voll.api.domain.record.MedicoRecord;
import med.voll.api.domain.record.MedicoUpdateRecord;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	private Boolean ativo;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@Embedded
	private Endereco endereco;
	
	public Medico(MedicoRecord medicoRecord) {
		this.nome = medicoRecord.nome();
		this.email = medicoRecord.email();
		this.telefone = medicoRecord.telefone();
		this.crm = medicoRecord.crm();
		this.especialidade = medicoRecord.especialidade();
		this.endereco = new Endereco(medicoRecord.endereco());
		this.ativo = true;
	}
	
	public Medico(Medico medicoSalvo, MedicoUpdateRecord medicoUpdateRecord) {
		this.id = medicoSalvo.getId();
		this.nome = medicoUpdateRecord.nome() != null ? medicoUpdateRecord.nome() : medicoSalvo.getNome();
		this.email = medicoSalvo.getEmail();
		this.telefone = medicoSalvo.getTelefone();
		this.crm = medicoSalvo.getCrm();
		this.especialidade = medicoUpdateRecord.especialidade() != null ? medicoUpdateRecord.especialidade() : medicoSalvo.getEspecialidade();
		this.endereco = medicoUpdateRecord.enderecoRecord() != null ? new Endereco(medicoUpdateRecord.enderecoRecord()) : medicoSalvo.getEndereco();
		this.ativo = medicoUpdateRecord.ativo() != null ? medicoUpdateRecord.ativo() : medicoSalvo.getAtivo();
	}
}