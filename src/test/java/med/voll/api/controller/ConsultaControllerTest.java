package med.voll.api.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.enums.Especialidade;
import med.voll.api.domain.record.DadosAgendamentoConsulta;
import med.voll.api.domain.record.DadosDetalhamentoConsulta;
import med.voll.api.service.AgendaConsultasService;

@SpringBootTest
@AutoConfigureMockMvc // para poder usar o MockMvc
@AutoConfigureJsonTesters // para poder usar o JacksonTester
class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mockMvc; // simula requisição HTTP
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJSON;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJSON;
	
	@MockBean // moca esse objeto, para não ser injetado de verdade
	private AgendaConsultasService agendaConsultasService;

	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
	@WithMockUser // passando um user mocado, para informar ao spring que tem um usuario autenticado
	void agendar_cenario_1() throws Exception {
		var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
	@WithMockUser // passando um user mocado, para informar ao spring que tem um usuario autenticado
	void agendar_cenario_2() throws Exception {
		var data = LocalDateTime.now().plusHours(1L);
		
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, 5L, data);
		when(agendaConsultasService.agendar(any())).thenReturn(dadosDetalhamento); // mock da chamada
		
		var response = mockMvc.perform(
				post("/consultas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosAgendamentoConsultaJSON.write(new DadosAgendamentoConsulta(2L, 5l, data, Especialidade.CARDIOLOGIA)).getJson())
			).andReturn().getResponse();
		
		
		var jsonEsperado = dadosDetalhamentoConsultaJSON.write(dadosDetalhamento).getJson();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

}
