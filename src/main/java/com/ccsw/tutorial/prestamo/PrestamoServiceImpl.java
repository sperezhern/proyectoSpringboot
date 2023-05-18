package com.ccsw.tutorial.prestamo;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private GameService gameService;

	@Override
	public Prestamo get(Long id) {

		return this.prestamoRepository.findById(id).orElse(null);
	}

	@Override
	public void save(Long id, PrestamoDto dto) {

		Prestamo prestamo;

		if (id == null) {
			prestamo = new Prestamo();
		} else {
			prestamo = this.prestamoRepository.findById(id).orElse(null);
		}

		BeanUtils.copyProperties(dto, prestamo, "id", "client", "game");

		prestamo.setClient(clientService.get(dto.getClient().getId()));
		prestamo.setGame(gameService.get(dto.getGame().getId()));

		this.prestamoRepository.save(prestamo);
	}

	@Override
	public List<Prestamo> find(String title, Long idClient) {

		PrestamoSpecification titleSpec = new PrestamoSpecification(new SearchCriteria("title", ":", title));
		PrestamoSpecification clientSpec = new PrestamoSpecification(new SearchCriteria("client.id", ":", idClient));

		Specification<Prestamo> spec = Specification.where(titleSpec).and(clientSpec);

		return this.prestamoRepository.findAll(spec);
	}

	@Override
	public void delete(Long id) throws Exception {

		if (this.get(id) == null) {
			throw new Exception("Not exists");
		}

		this.prestamoRepository.deleteById(id);
	}

	@Override
	public Page<Prestamo> findPage(PrestamoSearchDto dto) {

		return this.prestamoRepository.findAll(dto.getPageable().getPageable());
	}
}