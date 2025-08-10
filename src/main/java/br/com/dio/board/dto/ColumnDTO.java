package br.com.dio.board.dto;

import br.com.dio.board.entity.ColumnTypeEnum;

public record ColumnDTO(Long id,
        String name,
        ColumnTypeEnum type,
        int cardsAmount) {
}
