package com.ccsw.tutorial.client;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private ClientServiceImpl clientService;

	@Test
	public void findAllShouldReturnAllClients() {

		List<Client> list = new ArrayList<>();
		list.add(mock(Client.class));

		when(clientRepository.findAll()).thenReturn(list);

		List<Client> clients = clientService.findAll();

		assertNotNull(clients);
		assertEquals(1, clients.size());
	}

	public static final String CLIENT_NAME = "CAT1";
	public static final long ID = 1;

	@Test
	public void saveNotExistsClientIdShouldInsert() throws Exception {

		ClientDto clientDto = new ClientDto();
		clientDto.setName(CLIENT_NAME);
		clientDto.setId(ID);

		ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

		clientService.save(null, clientDto, null);

		verify(clientRepository).save(client.capture());

		assertEquals(CLIENT_NAME, client.getValue().getName());
	}

	public static final Long EXISTS_CLIENT_ID = 1L;
	public static final Long EXISTS_ID = 1L;

	@Test
	public void saveExistsClientIdShouldUpdate() {

		ClientDto clientDto = new ClientDto();
		clientDto.setName(CLIENT_NAME);

		Client client = mock(Client.class);
		when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

		// clientService.save(clientDto, EXISTS_CLIENT_ID);

		verify(clientRepository).save(client);
	}

	@Test
	public void deleteExistsClientIdShouldDelete() throws Exception {

		Client client = mock(Client.class);
		when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

		clientService.delete(EXISTS_CLIENT_ID);

		verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
	}

	public static final Long NOT_EXISTS_CLIENT_ID = 0L;

	@Test
	public void getExistsClientIdShouldReturnCategory() {

		Client client = mock(Client.class);
		when(client.getId()).thenReturn(EXISTS_CLIENT_ID);
		when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

		Client clientResponse = clientService.get(EXISTS_CLIENT_ID);

		assertNotNull(clientResponse);
		assertEquals(EXISTS_CLIENT_ID, client.getId());
	}

	@Test
	public void getNotExistsClientIdShouldReturnNull() {

		when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

		Client client = clientService.get(NOT_EXISTS_CLIENT_ID);

		assertNull(client);
	}
}