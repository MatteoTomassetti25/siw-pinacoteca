package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long>{
	
	public boolean existsByNome(String nome);
	
	public List<Artista> findAll();
	
	public Optional<Artista> findById(Long id);
	
	public Optional<Artista> findByNome(String nome);
	
//	public Optional<Artista> findByOpera(Opera opera);

	public void deleteById(Long id);
}
