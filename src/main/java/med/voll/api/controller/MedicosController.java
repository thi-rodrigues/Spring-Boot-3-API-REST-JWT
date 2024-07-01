package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.Medico;
import med.voll.api.domain.record.MedicoDTO;
import med.voll.api.domain.record.MedicoListRecord;
import med.voll.api.domain.record.MedicoRecord;
import med.voll.api.domain.record.MedicoUpdateRecord;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.service.MedicoService;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicosController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private MedicoService medicoService;

	@PostMapping
	public ResponseEntity<MedicoRecord> create(@RequestBody @Valid MedicoRecord medicoRecord, UriComponentsBuilder uriBuilder) {
		Medico medico = medicoRepository.save(new Medico(medicoRecord));
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(medicoRecord);
	}
	
	@GetMapping
	@Secured("ROLE_USER")
	public ResponseEntity<Page<MedicoListRecord>> list(
			@PageableDefault(size = 10, sort = {"nome"}, direction = Direction.DESC) Pageable pageable) {
		Page<MedicoListRecord> medicoList = medicoRepository.findAllByAtivoTrue(pageable).map(MedicoListRecord::new);
		return ResponseEntity.ok(medicoList);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<MedicoUpdateRecord> update(@RequestBody @Valid MedicoUpdateRecord medicoUpdateRecord) {
		medicoService.update(medicoUpdateRecord);
		return ResponseEntity.ok(medicoUpdateRecord);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		medicoService.inativar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicoDTO> findById(@PathVariable("id") Long id) {
		MedicoDTO medicoListRecord = medicoService.findById(id);
		return ResponseEntity.ok(medicoListRecord);
	}
	
	
}
