package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	protected ArtistaRepository artistaRepository;
	
	
	public List<Artista> findAll(){
		return this.artistaRepository.findAll();
	}
	
	public Artista findById(Long id) {
		Optional<Artista> artista = this.artistaRepository.findById(id);
		return artista.orElse(null);
	}
	
	public Artista findByNome(String nome) {
		Optional<Artista> artista = this.artistaRepository.findByNome(nome);
		return artista.orElse(null);
	}
	
//	public Artista findByOpera(Opera opera) {
//		Optional<Artista> artista = this.artistaRepository.findByOpera(opera);
//		return artista.orElse(null);
//	}
	
	public Artista save(Artista artista) {
		return this.artistaRepository.save(artista);
	}
	
	public void deleteById(Long id) {
		if(this.artistaRepository.existsById(id)) {
			this.artistaRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Non esistono artisti con quesro id");
		}
	}
}
