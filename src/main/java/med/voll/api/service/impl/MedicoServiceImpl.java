package med.voll.api.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.Medico;
import med.voll.api.domain.record.MedicoDTO;
import med.voll.api.domain.record.MedicoUpdateRecord;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.service.MedicoService;

@Service
public class MedicoServiceImpl implements MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public void update(MedicoUpdateRecord medicoUpdateRecord) {
		var medicoSalvo = medicoRepository.findById(medicoUpdateRecord.id()).get();
		Medico medico = new Medico(medicoSalvo, medicoUpdateRecord);
		medicoRepository.save(medico);
	}

	@Override
	public void inativar(Long id) {
		var medicoSalvo = medicoRepository.findById(id).get();
		medicoSalvo.setAtivo(false);
		medicoRepository.save(medicoSalvo);
	}

	@Override
	public MedicoDTO findById(Long id) {
		Medico medico = medicoRepository.findById(id).get();
		
		ModelMapper modelMapper = new ModelMapper();
		MedicoDTO map = modelMapper.map(medico, MedicoDTO.class);
		return map;
	}
}
