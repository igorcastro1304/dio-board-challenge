package br.com.dio.dio_design_pattern_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dio.dio_design_pattern_project.model.Card;
import br.com.dio.dio_design_pattern_project.service.CardService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("cards")
public class CardRestController {
	@Autowired
	private CardService cardService;
	
	@GetMapping
	public ResponseEntity<Iterable<Card>> getAll() {
		return ResponseEntity.ok(cardService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Card> getById(@PathVariable Long id) {
		return ResponseEntity.ok(cardService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<Card> add(@RequestBody Card card) {
		cardService.add(card);
		return ResponseEntity.ok(card);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Card> update(@PathVariable Long id, @RequestBody Card card) {
		cardService.update(id, card);
		return ResponseEntity.ok(card);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		cardService.delete(id);
		return ResponseEntity.ok().build();
	}
}
