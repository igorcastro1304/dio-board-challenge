package br.com.dio.dio_design_pattern_project.service;

import br.com.dio.dio_design_pattern_project.model.Card;

public interface CardService {
	Iterable<Card> getAll();

	Card getById(Long id);

	void add(Card card);

	void update(Long id, Card card);

	void delete(Long id);
}
