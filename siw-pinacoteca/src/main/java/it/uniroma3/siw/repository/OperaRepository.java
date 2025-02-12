package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{

	public boolean existsByTitolo(String titolo);

	public List<Opera> findAll();

	public Optional<Opera> findByTitolo(String titolo);

	public Optional<Opera> findById(Long id);

	//	public Optional<Opera> findByArtista(Artista autore);

	public void deleteById(Long id);


	@Query("SELECT DISTINCT o.anno FROM Opera o WHERE o.anno IS NOT NULL")
	List<Integer> findDistinctAnni();

	@Query("SELECT DISTINCT o.tecnica FROM Opera o WHERE o.tecnica IS NOT NULL")
	List<String> findDistinctTecniche();

	@Query("SELECT o FROM Opera o WHERE " +
			"(:artistaId IS NULL OR o.artista.id = :artistaId) " +
			"AND (:anno IS NULL OR o.anno = :anno) " +
			"AND (:tecnica IS NULL OR o.tecnica = '' OR o.tecnica = :tecnica)")
	List<Opera> findOpereByFiltri(@Param("artistaId") Long artistaId,
			@Param("anno") Integer anno,
			@Param("tecnica") String tecnica);

	



}
