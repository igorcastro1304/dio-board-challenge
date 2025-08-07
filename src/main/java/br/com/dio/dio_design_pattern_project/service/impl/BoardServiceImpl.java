package br.com.dio.dio_design_pattern_project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dio.dio_design_pattern_project.model.Board;
import br.com.dio.dio_design_pattern_project.repository.IBoardRepository;
import br.com.dio.dio_design_pattern_project.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private IBoardRepository boardRepository;
	
	@Override
	public Iterable<Board> getAll() {
		return boardRepository.findAll();
	}

	@Override
	public Board getById(Long id) {
		Optional<Board> board = boardRepository.findById(id);
		
		return board.get();
	}

	@Override
	public void add(Board board) {
		boardRepository.save(board);
	}

	@Override
	public void update(Long id, Board board) {
		Optional<Board> boardDb = boardRepository.findById(id);
		if (boardDb.isPresent()) {
			add(board);
		}
	}

	@Override
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}
}
