package br.com.dio.dio_design_pattern_project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dio.dio_design_pattern_project.model.Card;
import br.com.dio.dio_design_pattern_project.model.Column;
import br.com.dio.dio_design_pattern_project.repository.ICardRepository;
import br.com.dio.dio_design_pattern_project.repository.IColumnRepository;
import br.com.dio.dio_design_pattern_project.service.CardService;

@Service
public class CardServiceImpl implements CardService {
	@Autowired
	private ICardRepository cardRepository;
	@Autowired
	private IColumnRepository columnRepository;
	
	@Override
	public Iterable<Card> getAll() {
		return cardRepository.findAll();
	}

	@Override
	public Card getById(Long id) {
		Optional<Card> card = cardRepository.findById(id);
		return card.get();
	}

	@Override
	public void add(Card card) {
		Column cardColumn = card.getColumn();
		
		Optional <Column> column = columnRepository.findById(cardColumn.getId());
		if(!column.isPresent()) {
			columnRepository.save(cardColumn);
		}
		
		cardRepository.save(card);
	}

	@Override
	public void update(Long id, Card card) {
		Optional<Card> cardDb = cardRepository.findById(id);
		if (cardDb.isPresent()) {
			add(card);
		}		
	}

	@Override
	public void delete(Long id) {
		cardRepository.deleteById(id);
	}
}
