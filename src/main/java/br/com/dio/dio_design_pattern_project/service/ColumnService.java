package br.com.dio.dio_design_pattern_project.service;

import br.com.dio.dio_design_pattern_project.model.Column;

public interface ColumnService {
	Iterable<Column> getAll();

	Column getById(Long id);

	void add(Column column);

	void update(Long id, Column column);

	void delete(Long id);
}
