package br.com.dio.board.dto;

import java.util.List;

public record BoardDetailDTO(Long id,
        String name,
        List<ColumnDTO> columns) {
}