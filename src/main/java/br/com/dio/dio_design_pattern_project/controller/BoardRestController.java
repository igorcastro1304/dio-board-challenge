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

import br.com.dio.dio_design_pattern_project.model.Board;
import br.com.dio.dio_design_pattern_project.service.BoardService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("boards")
public class BoardRestController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping
	public ResponseEntity<Iterable<Board>> getAll() {
		return ResponseEntity.ok(boardService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Board> getById(@PathVariable Long id) {
		return ResponseEntity.ok(boardService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<Board> add(@RequestBody Board board) {
		boardService.add(board);
		return ResponseEntity.ok(board);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Board> update(@PathVariable Long id, @RequestBody Board board) {
		boardService.update(id, board);
		return ResponseEntity.ok(board);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		boardService.delete(id);
		return ResponseEntity.ok().build();
	}
}
