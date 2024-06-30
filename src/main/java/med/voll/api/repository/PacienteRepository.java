package med.voll.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import med.voll.api.domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

	@Query("""
            select m.ativo
            from Paciente m
            where
            m.id = :id
            """)
	boolean findAtivoById(Long id);
}
	