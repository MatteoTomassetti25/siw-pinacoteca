package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Area;

public interface AreaRepository extends CrudRepository<Area, Long> {

	public boolean existsByNome(String nome);
	
	public List<Area> findAll();
	
	public Optional<Area> findById(Long id);
	
	public Optional<Area> findByNome(String nome);
	
//	public Optional<Area> findByCuratore(Curatore curatore);
	
	public void deleteById(Long id);
}
