package br.com.dio.board.dto;

import br.com.dio.board.entity.ColumnTypeEnum;

public record ColumnInfoDTO(Long id, int order, ColumnTypeEnum type) {
}
