package br.com.dio.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.dio.board.dao.ColumnDAO;
import br.com.dio.board.entity.ColumnEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ColumnService {

    private final Connection connection;

    public Optional<ColumnEntity> getById(final Long id) throws SQLException {
        ColumnDAO dao = new ColumnDAO(connection);
        
        return dao.findById(id);
    }
}
