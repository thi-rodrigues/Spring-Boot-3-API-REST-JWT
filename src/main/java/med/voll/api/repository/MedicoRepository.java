package med.voll.api.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import med.voll.api.domain.Medico;
import med.voll.api.domain.enums.Especialidade;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page<Medico> findAllByAtivoTrue(Pageable pageable);

	@Query("""
			FROM Medico m
			WHERE m.ativo = TRUE AND m.especialidade = :especialidade 
			AND m.id NOT IN (
				SELECT c.medico.id FROM Consulta c WHERE c.data = :data
				AND c.motivoCancelamento IS NULL
			)
			ORDER BY RAND()
			LIMIT 1
			""")
	Medico esclherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
	
    @Query("""
            select m.ativo
            from Medico m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);

	
}
