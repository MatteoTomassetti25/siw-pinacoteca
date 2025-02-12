package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Curatore;

public interface CuratoreRepository extends CrudRepository<Curatore, Long>{

	public boolean existsByNome(String nome);
	
	public List<Curatore> findAll();
	
	public Optional<Curatore> findById(Long id);
	
//	public Optional<Curatore> findByArea(Area area);
	
	public void deleteById(Long id);

	public boolean existsByCodFiscale(String codFiscale);
}
