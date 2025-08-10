package br.com.dio.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import br.com.dio.board.dto.ColumnDTO;
import br.com.dio.board.entity.CardEntity;
import br.com.dio.board.entity.ColumnEntity;
import lombok.RequiredArgsConstructor;

import static br.com.dio.board.entity.ColumnTypeEnum.findByName;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class ColumnDAO {

	private final Connection connection;

	public ColumnEntity create(final ColumnEntity entity) throws SQLException {
		String sql = "INSERT INTO BOARDS_COLUMNS (name, `order`, column_type, board_id) VALUES (?, ?, ?, ?);";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			statement.setString(i++, entity.getName());
			statement.setInt(i++, entity.getOrder());
			statement.setString(i++, entity.getColumnType().name());
			statement.setLong(i, entity.getBoard().getId());
			statement.executeUpdate();

			if (statement instanceof StatementImpl impl) {
				entity.setId(impl.getLastInsertID());
			}

			return entity;
		}
	}

	public List<ColumnEntity> findByBoardId(final Long boardId) throws SQLException {
		List<ColumnEntity> entities = new ArrayList<>();
		String sql = "SELECT id, name, `order`, column_type FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY `order`";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, boardId);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();

			while (resultSet.next()) {
				var entity = new ColumnEntity();
				entity.setId(resultSet.getLong("id"));
				entity.setName(resultSet.getString("name"));
				entity.setOrder(resultSet.getInt("order"));
				entity.setColumnType(findByName(resultSet.getString("column_type")));
				entities.add(entity);
			}

			return entities;
		}
	}

	public List<ColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException {
		List<ColumnDTO> dtos = new ArrayList<>();

		String sql = """
				SELECT bc.id,
				       bc.name,
				       bc.column_type,
				       (SELECT COUNT(c.id)
				               FROM CARDS c
				              WHERE c.board_column_id = bc.id) cards_amount
				  FROM BOARDS_COLUMNS bc
				 WHERE board_id = ?
				 ORDER BY `order`;
				""";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, boardId);
			statement.executeQuery();
			
			ResultSet resultSet = statement.getResultSet();
			
			while (resultSet.next()) {
				ColumnDTO dto = new ColumnDTO(resultSet.getLong("bc.id"), resultSet.getString("bc.name"),
						findByName(resultSet.getString("bc.column_type")), resultSet.getInt("cards_amount"));
				
				dtos.add(dto);
			}
			
			return dtos;
		}
	}

	public Optional<ColumnEntity> findById(final Long boardId) throws SQLException {
		String sql = """
				SELECT bc.name,
				       bc.column_type,
				       c.id,
				       c.title,
				       c.description
				  FROM BOARDS_COLUMNS bc
				  LEFT JOIN CARDS c
				    ON c.board_column_id = bc.id
				 WHERE bc.id = ?;
				""";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, boardId);
			statement.executeQuery();
			
			ResultSet resultSet = statement.getResultSet();
			
			if (resultSet.next()) {
				ColumnEntity entity = new ColumnEntity();
				
				entity.setName(resultSet.getString("bc.name"));
				entity.setColumnType(findByName(resultSet.getString("bc.column_type")));
				
				do {
					CardEntity card = new CardEntity();
					
					if (isNull(resultSet.getString("c.title"))) {
						break;
					}
					
					card.setId(resultSet.getLong("c.id"));
					card.setTitle(resultSet.getString("c.title"));
					card.setDescription(resultSet.getString("c.description"));
					
					entity.getCards().add(card);
				} while (resultSet.next());
				
				return Optional.of(entity);
			}
			
			return Optional.empty();
		}
	}

}
