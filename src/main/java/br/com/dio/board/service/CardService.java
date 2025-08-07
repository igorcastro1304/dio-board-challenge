package br.com.dio.board.service;

import br.com.dio.board.model.Card;

public interface CardService {
	Iterable<Card> getAll();

	Card getById(Long id);

	void add(Card card);

	void update(Long id, Card card);

	void delete(Long id);
}
