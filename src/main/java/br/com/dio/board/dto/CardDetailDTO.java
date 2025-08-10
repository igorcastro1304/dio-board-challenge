package br.com.dio.board.dto;

import java.time.OffsetDateTime;

public record CardDetailDTO(Long id, String title, String description, int order, boolean isBlocked, Long columnId, String columnName) {
}
