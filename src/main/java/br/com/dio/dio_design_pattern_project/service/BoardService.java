package br.com.dio.dio_design_pattern_project.service;

import br.com.dio.dio_design_pattern_project.model.Board;

public interface BoardService {
	Iterable<Board> getAll();
	
	Board getById(Long id);
	
	void add(Board board);
	
	void update(Long id, Board board);
	
	void delete(Long id);
}
