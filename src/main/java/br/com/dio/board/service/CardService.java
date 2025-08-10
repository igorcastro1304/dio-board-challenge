package br.com.dio.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import br.com.dio.board.dao.CardDAO;
import br.com.dio.board.dto.CardDetailDTO;
import br.com.dio.board.dto.ColumnInfoDTO;
import br.com.dio.board.entity.CardEntity;
import br.com.dio.board.exception.CardBlockedException;
import br.com.dio.board.exception.CardFinishedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import static br.com.dio.board.entity.ColumnTypeEnum.CANCEL;
import static br.com.dio.board.entity.ColumnTypeEnum.FINAL;

@AllArgsConstructor
public class CardService {
	private final Connection connection;

	public CardEntity create(final CardEntity entity) throws SQLException {
		try {
			CardDAO dao = new CardDAO(connection);
			dao.insert(entity);

			connection.commit();

			return entity;
		} catch (SQLException ex) {
			connection.rollback();

			throw ex;
		}
	}

	public void moveToNextColumn(final Long cardId, final List<ColumnInfoDTO> boardColumnsInfo) throws SQLException {
		try {
			CardDAO dao = new CardDAO(connection);
			Optional<CardDetailDTO> optional = dao.getById(cardId);

			CardDetailDTO dto = optional.orElseThrow(
					() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));

			if (dto.isBlocked()) {
				String message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);

				throw new CardBlockedException(message);
			}

			ColumnInfoDTO currentColumn = boardColumnsInfo.stream().filter(bc -> bc.id().equals(dto.columnId()))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));

			if (currentColumn.type().equals(FINAL)) {
				throw new CardFinishedException("O card já foi finalizado");
			}

			ColumnInfoDTO nextColumn = boardColumnsInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1)
					.findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));

			dao.moveToColumn(nextColumn.id(), cardId);
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();

			throw ex;
		}
	}

	public void cancel(final Long cardId, final Long cancelColumnId, final List<ColumnInfoDTO> boardColumnsInfo) throws SQLException {
		try {
			CardDAO dao = new CardDAO(connection);
			
			Optional<CardDetailDTO> optional = dao.getById(cardId);
			CardDetailDTO dto = optional.orElseThrow(
					() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
			
			if (dto.isBlocked()) {
				String message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);
				
				throw new CardBlockedException(message);
			}
			
			ColumnInfoDTO currentColumn = boardColumnsInfo.stream().filter(bc -> bc.id().equals(dto.columnId())).findFirst()
					.orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
			
			if (currentColumn.type().equals(FINAL)) {
				throw new CardFinishedException("O card já foi finalizado");
			}
			
			boardColumnsInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1).findFirst()
					.orElseThrow(() -> new IllegalStateException("O card está cancelado"));
			
			dao.moveToColumn(cancelColumnId, cardId);
			
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			
			throw ex;
		}
	}

	public void block(final Long id, final List<ColumnInfoDTO> boardColumnsInfo) throws SQLException {
		try {
			CardDAO dao = new CardDAO(connection);
			Optional<CardDetailDTO> optional = dao.getById(id);
			
			var dto = optional
					.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id)));
			
			if (dto.isBlocked()) {
				String message = "O card %s já está bloqueado".formatted(id);
				
				throw new CardBlockedException(message);
			}
			
			ColumnInfoDTO currentColumn = boardColumnsInfo.stream().filter(bc -> bc.id().equals(dto.columnId())).findFirst()
					.orElseThrow();
			
			if (currentColumn.type().equals(FINAL) || currentColumn.type().equals(CANCEL)) {
				String message = "O card está em uma coluna do tipo %s e não pode ser bloqueado"
						.formatted(currentColumn.type());
				
				throw new IllegalStateException(message);
			}
			
			dao.block(id);
			
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			throw ex;
		}
	}

	public void unblock(final Long id) throws SQLException {
		try {
			CardDAO dao = new CardDAO(connection);
			Optional<CardDetailDTO> optional = dao.getById(id);
			
			CardDetailDTO dto = optional
					.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id)));
			
			if (!dto.isBlocked()) {
				String message = "O card %s não está bloqueado".formatted(id);
				
				throw new CardBlockedException(message);
			}
						
			dao.unblock(id);
			
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			
			throw ex;
		}
	}

	public Optional<CardDetailDTO> getById(Long selectedCardId) throws SQLException {
	    try {
	        CardDAO dao = new CardDAO(connection);
	        
	        return dao.getById(selectedCardId);
	    } catch (SQLException ex) {
	        connection.rollback();
	        throw ex;
	    }
	}
}