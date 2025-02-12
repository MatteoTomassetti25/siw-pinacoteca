package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credenziali;



public interface CredenzialiRepository extends CrudRepository<Credenziali, Long>{
	
	public Optional<Credenziali>  findById(Long id);
	
	public Credenziali  findByUsername(String username);
	
	public List<Credenziali> findAll();
	
	public void delete(Credenziali credenziali);

}
