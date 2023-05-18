package com.ccsw.tutorial.prestamo;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

public interface PrestamoService {

	Prestamo get(Long id);

	void save(Long id, PrestamoDto dto);

	List<Prestamo> find(String title, Long idClient);

	void delete(Long id) throws Exception;

	Page<Prestamo> findPage(PrestamoSearchDto dto);

}