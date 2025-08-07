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

import br.com.dio.dio_design_pattern_project.model.Column;
import br.com.dio.dio_design_pattern_project.service.ColumnService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("columns")
public class ColumnRestController {
	@Autowired
	private ColumnService columnService;
	
	@GetMapping
	public ResponseEntity<Iterable<Column>> getAll() {
		return ResponseEntity.ok(columnService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Column> getById(@PathVariable Long id) {
		return ResponseEntity.ok(columnService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<Column> add(@RequestBody Column column) {
		columnService.add(column);
		return ResponseEntity.ok(column);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Column> update(@PathVariable Long id, @RequestBody Column column) {
		columnService.update(id, column);
		return ResponseEntity.ok(column);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		columnService.delete(id);
		return ResponseEntity.ok().build();
	}
}
