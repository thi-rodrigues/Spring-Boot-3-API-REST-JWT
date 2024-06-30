package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.domain.record.DadosCancelamentoConsulta;
import med.voll.api.domain.record.DadosDetalhamentoConsulta;
import med.voll.api.service.AgendaConsultasService;

@RestController
@RequestMapping("consultas")
public class ConsultaController {
	
	@Autowired
	private AgendaConsultasService agendaConsultasService;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		DadosDetalhamentoConsulta agendamento = agendaConsultasService.agendar(dadosAgendamentoConsulta);
		return ResponseEntity.ok(agendamento);
	}
	
	@PostMapping("/cancelar")
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamentoConsulta) {
		agendaConsultasService.cancelar(dadosCancelamentoConsulta);
		return ResponseEntity.noContent().build();
	}

}
