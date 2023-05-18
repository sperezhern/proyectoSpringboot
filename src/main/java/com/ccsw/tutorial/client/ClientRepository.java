package com.ccsw.tutorial.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.client.model.Client;

/**
 * @author ccsw
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

	@Query("select name from Client")
	List<Client> findAllByName(String name);

	Optional<Client> findByName(String name);
}