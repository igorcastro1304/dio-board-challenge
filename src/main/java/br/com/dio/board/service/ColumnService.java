package br.com.dio.board.service;

import br.com.dio.board.model.Column;

public interface ColumnService {
	Iterable<Column> getAll();

	Column getById(Long id);

	void add(Column column);

	void update(Long id, Column column);

	void delete(Long id);
}
