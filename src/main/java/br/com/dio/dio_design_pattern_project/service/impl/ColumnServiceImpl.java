package br.com.dio.dio_design_pattern_project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dio.dio_design_pattern_project.model.Board;
import br.com.dio.dio_design_pattern_project.model.Column;
import br.com.dio.dio_design_pattern_project.repository.IBoardRepository;
import br.com.dio.dio_design_pattern_project.repository.IColumnRepository;
import br.com.dio.dio_design_pattern_project.service.ColumnService;

@Service
public class ColumnServiceImpl implements ColumnService {
	@Autowired
	private IColumnRepository columnRepository;
	@Autowired
	private IBoardRepository boardRepository;
	
	@Override
	public Iterable<Column> getAll() {
		return columnRepository.findAll();
	}

	@Override
	public Column getById(Long id) {
		Optional<Column> column = columnRepository.findById(id);
		return column.get();
	}

	@Override
	public void add(Column column) {
		Board columnBoard = column.getBoard();
		
		Optional <Board> board = boardRepository.findById(columnBoard.getId());
		if(!board.isPresent()) {
			boardRepository.save(columnBoard);
		}
		
		columnRepository.save(column);
	}

	@Override
	public void update(Long id, Column column) {
		Optional<Column> columnDb = columnRepository.findById(id);
		if (columnDb.isPresent()) {
			add(column);
		}		
	}

	@Override
	public void delete(Long id) {
		columnRepository.deleteById(id);
	}
}
