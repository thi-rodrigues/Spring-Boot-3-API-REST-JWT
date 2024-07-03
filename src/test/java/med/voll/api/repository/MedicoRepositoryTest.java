package med.voll.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import med.voll.api.domain.enums.Especialidade;
import med.voll.api.domain.record.DadosCadastroPaciente;
import med.voll.api.domain.record.EnderecoRecord;
import med.voll.api.domain.record.MedicoRecord;

@DataJpaTest // Utilizada para testar uma interface Repository
@AutoConfigureTestDatabase(replace = Replace.NONE) // Serve para indicar para o Spring rodar o test usando o DB padrão
@ActiveProfiles("test")
class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("Deve devolver null quando unico medico cadastrado nao esta disponivel na data")
	void escolherMedicoAleatorioLivreNaDataCenario_1() {
		// given ou arrange
		var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
		var medico = cadastrarMedico("medico", "medico@email.com", "123456", Especialidade.CARDIOLOGIA);
		var paciente = cadastrarPaciente("paciente", "paciente@email.com", "12345678910");
		cadastrarConsulta(medico, paciente, proximaSegundaAs10);
		// when ou act
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		// then ou assert
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deve devolver medico quando ele estiver disponivel na data")
	void escolherMedicoAleatorioLivreNaDataCenario_2() {
		// given ou arrange
		var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
		var medico = cadastrarMedico("medico", "medico@email.com", "123456", Especialidade.CARDIOLOGIA);
		// when ou act
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		// then ou assert
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
	    em.persist(new Consulta(null, medico, paciente, data, null, true));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private MedicoRecord dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
	    return new MedicoRecord(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private EnderecoRecord dadosEndereco() {
	    return new EnderecoRecord(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}

}
