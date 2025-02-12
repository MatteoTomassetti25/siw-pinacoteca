package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.OperaRepository;

@Service
public class OperaService {

	@Autowired
	protected OperaRepository operaRepository;

	public List<Opera> findAll(){
		return this.operaRepository.findAll();
	}

	public Opera findByNome(String titolo) {
		Optional<Opera> opera = this.operaRepository.findByTitolo(titolo);
		return opera.orElse(null);
	}

	public Opera findById(Long id) {
		Optional<Opera> opera = this.operaRepository.findById(id);
		return opera.orElse(null);
	}

	//	public Opera findByArtista(Artista artista) {
	//		Optional<Opera> opera = this.operaRepository.findByArtista(artista);
	//		return opera.orElse(null);
	//	}

	public Opera save(Opera opera) {
		return this.operaRepository.save(opera);
	}

	public void deleteById(Long id) {
		if(this.operaRepository.existsById(id)) {
			this.operaRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Non esistono opere con questo id");
		}
	}

	public List<Integer> findAllAnni() {
        return operaRepository.findDistinctAnni();
    }

    public List<String> findAllTecniche() {
        return operaRepository.findDistinctTecniche();
    }

    public List<Opera> findbyFiltri(Long artistaId, Integer anno, String tecnica) {
        return operaRepository.findOpereByFiltri(artistaId, anno, tecnica);
    }


}
