package br.com.dio.dio_design_pattern_project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dio.dio_design_pattern_project.model.Card;

@Repository
public interface ICardRepository extends CrudRepository<Card, Long> {

}
