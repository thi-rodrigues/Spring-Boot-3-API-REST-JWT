package med.voll.api.service;

import med.voll.api.domain.record.MedicoDTO;
import med.voll.api.domain.record.MedicoUpdateRecord;

public interface MedicoService {
	
	public void update(MedicoUpdateRecord medicoUpdateRecord);

	public void inativar(Long id);
	
	public MedicoDTO findById(Long id);

}
