package br.com.dio.board.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dio.board.model.Column;

@Repository
public interface IColumnRepository extends CrudRepository<Column, Long> {

}
