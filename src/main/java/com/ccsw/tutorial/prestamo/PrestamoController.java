package com.ccsw.tutorial.prestamo;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Prestamo", description = "API of Prestamo")
@RequestMapping(value = "/prestamo")
@RestController
@CrossOrigin(origins = "*")
public class PrestamoController {

	@Autowired
	PrestamoService prestamoService;

	@Autowired
	DozerBeanMapper mapper;

	/**
	 * Método para recuperar todas las {@link Client}
	 *
	 * @return {@link List} de {@link ClientDto}
	 */
	@Operation(summary = "Find", description = "Method that return a filtered list of Prestamos")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<PrestamoDto> find(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "idClient", required = false) Long idClient) {

		List<Prestamo> prestamos = prestamoService.find(title, idClient);

		return prestamos.stream().map(e -> mapper.map(e, PrestamoDto.class)).collect(Collectors.toList());

		/*
		 * Page<Author> page = this.authorService.findPage(dto);
		 * 
		 * return new PageImpl<>( page.getContent().stream().map(e -> mapper.map(e,
		 * AuthorDto.class)).collect(Collectors.toList()), page.getPageable(),
		 * page.getTotalElements());
		 */
	}

	@Operation(summary = "Save or Update", description = "Method that saves or updates a Prestamo")
	@RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
	public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody PrestamoDto dto) {

		prestamoService.save(id, dto);
	}

	@Operation(summary = "Delete", description = "Method that deletes a Prestamo")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) throws Exception {

		this.prestamoService.delete(id);
	}

	@Operation(summary = "Find Page", description = "Method that return a page of Authors")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Page<PrestamoDto> findPage(@RequestBody PrestamoSearchDto dto) {

		Page<Prestamo> page = this.prestamoService.findPage(dto);

		return new PageImpl<>(
				page.getContent().stream().map(e -> mapper.map(e, PrestamoDto.class)).collect(Collectors.toList()),
				page.getPageable(), page.getTotalElements());
	}

}