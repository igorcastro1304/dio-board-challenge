package br.com.dio.board.service;

import br.com.dio.board.model.Board;

public interface BoardService {
	Iterable<Board> getAll();
	
	Board getById(Long id);
	
	void add(Board board);
	
	void update(Long id, Board board);
	
	void delete(Long id);
}
