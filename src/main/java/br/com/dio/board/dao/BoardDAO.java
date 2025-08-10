package br.com.dio.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import br.com.dio.board.entity.BoardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardDAO {
	public final Connection connection;

	public BoardEntity create(final BoardEntity entity) throws SQLException {
		String query = "INSERT INTO BOARDS (name) VALUES (?);";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, entity.getName());
			statement.executeUpdate();
			
			if(statement instanceof StatementImpl impl) {
				entity.setId(impl.getLastInsertID());
			}
		}
		
		return entity;
	}

	public void deleteById(final Long id) throws SQLException {
		String query = "DELETE FROM BOARDS WHERE id = ?;";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		}
	}

	public Optional<BoardEntity> getById(final Long id) throws SQLException {
		String query = "SELECT id, name FROM BOARDS WHERE id = ?;";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();

			if (resultSet.next()) {
				BoardEntity entity = new BoardEntity();

				entity.setId(resultSet.getLong("id"));
				entity.setName(resultSet.getString("name"));

				return Optional.of(entity);
			}

			return Optional.empty();
		}
	}

	public boolean exists(final Long id) throws SQLException {
		String query = "SELECT 1 FROM BOARDS WHERE id = ?;";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			statement.executeQuery();

			return statement.getResultSet().next();
		}
	}
}
