package br.com.dio.board.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dio.board.model.Card;

@Repository
public interface ICardRepository extends CrudRepository<Card, Long> {

}
