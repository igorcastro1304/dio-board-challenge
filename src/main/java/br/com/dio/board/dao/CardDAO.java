package br.com.dio.board.dao;

import br.com.dio.board.dto.CardDetailDTO;
import br.com.dio.board.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

@AllArgsConstructor
public class CardDAO {

    private Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        String sql = "INSERT INTO CARDS (title, description, board_column_id) values (?, ?, ?);";
        
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            int i = 1;
            
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i, entity.getColumn().getId());
            statement.executeUpdate();
            
            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        
        return entity;
    }

    public void moveToColumn(final Long columnId, final Long cardId) throws SQLException{
        String sql = "UPDATE CARDS SET board_column_id = ? WHERE id = ?;";
        
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            int i = 1;
            
            statement.setLong(i++, columnId);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public Optional<CardDetailDTO> getById(final Long id) throws SQLException {
        String sql =
                """
                SELECT c.id,
                       c.title,
                       c.description,
                       c.`order`,
                       c.is_blocked,
                       c.board_column_id,
                       bc.name,
                  FROM CARDS c
                 INNER JOIN BOARDS_COLUMNS bc
                    ON bc.id = c.board_column_id
                  WHERE c.id = ?;
                """;
        
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            
            ResultSet resultSet = statement.getResultSet();
            
            if (resultSet.next()){
                CardDetailDTO dto = new CardDetailDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        resultSet.getInt("c.`order`"),
                        resultSet.getBoolean("c.is_blocked"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.name")
                );
                
                return Optional.of(dto);
            }
        }
        
        return Optional.empty();
    }
    
    public void block(final Long cardId) throws SQLException {
        String sql = "UPDATE CARDS SET is_blocked = TRUE WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, cardId);
            statement.executeUpdate();
        }
    }

    public void unblock(final Long cardId) throws SQLException {
        String sql = "UPDATE CARDS SET is_blocked = FALSE WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, cardId);
            statement.executeUpdate();
        }
    }
}