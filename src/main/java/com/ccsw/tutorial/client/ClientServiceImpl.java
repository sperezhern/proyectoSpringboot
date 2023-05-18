package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import jakarta.transaction.Transactional;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Client> findAll() {

		return (List<Client>) this.clientRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@Override
	public void save(Long id, ClientDto dto, String name) throws Exception {

		/*
		 * para eso, cuando tengas que validar esas cosas, lo que se hace siempre es
		 * implementarlo en backend y ahí, antes de hacer ejecutar físicamente el save,
		 * debes lanzar una query a la BBDD buscando si ya existe un cliente con ese
		 * nombre si te devuelve algo, lanzas error si no te devuelve nada, entonces ya
		 * ejecutas el save
		 * 
		 */

		Client client;

		if (id == null) {
			client = new Client();
		} else {
			client = this.get(id);
		}

		if (this.getName(name) != null) {
			throw new Exception("El nombre ya existe");
		} else {
			client.setName(dto.getName());

			this.clientRepository.save(client);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) throws Exception {

		if (this.get(id) == null) {
			throw new Exception("Not exists");
		}

		this.clientRepository.deleteById(id);
	}

	@Override
	public Client getName(String name) {

		return this.clientRepository.findByName(name).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client get(Long id) {

		return this.clientRepository.findById(id).orElse(null);
	}

}