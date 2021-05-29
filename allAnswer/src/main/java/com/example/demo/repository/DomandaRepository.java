package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Domanda;

public interface DomandaRepository extends CrudRepository<Domanda, Long>{

	@Query(nativeQuery=true, value="SELECT *  FROM domanda ORDER BY random() LIMIT 5")
	public List<Domanda> getRandomDomanda();
}
