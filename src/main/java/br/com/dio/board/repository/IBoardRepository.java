package br.com.dio.board.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dio.board.model.Board;

@Repository
public interface IBoardRepository extends CrudRepository<Board, Long> {

}
