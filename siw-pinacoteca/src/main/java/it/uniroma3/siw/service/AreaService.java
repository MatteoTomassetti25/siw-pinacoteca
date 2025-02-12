package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.AreaRepository;
import jakarta.transaction.Transactional;

@Service
public class AreaService {

	@Autowired
	protected AreaRepository areaRepository;
	
	@Autowired
	protected OperaService operaService;
	
	public List<Area> findAll(){
		return this.areaRepository.findAll();
	}
	
	public Area findById(Long id) {
		Optional<Area> area = this.areaRepository.findById(id);
		return area.orElse(null);
	}
	
	public Area findByNome(String nome) {
		Optional<Area> area = this.areaRepository.findByNome(nome);
		return area.orElse(null);
	}
	
//	public Area findByCuratore(Curatore curatore) {
//		Optional<Area> area = this.areaRepository.findByCuratore(curatore);
//		return area.orElse(null);
//	}
	
	public Area save(Area area) {
		return this.areaRepository.save(area);
	}
	
	public List<Opera> findAllOpere(Long id){
		List<Opera> result = new ArrayList<Opera>();
		
		for(Opera elem : this.operaService.findAll()) {
			if(elem.getCollocazione().equals(this.findById(id))) {
				result.add(elem);
			}
		}
		return result;
	}
	
	@Transactional
	public void deleteById(Long id) {
		if(this.areaRepository.existsById(id)) {
			this.areaRepository.deleteById(id);
		}else if(areaRepository.findById(id).get().getCuratore()!=null) {
			throw new IllegalArgumentException("Attenzione area assegnata ad un curatore");
		}
		else {
			throw new IllegalArgumentException("Attenzione area non trovata");
		}
	}
}
