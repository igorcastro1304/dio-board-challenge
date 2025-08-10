package br.com.dio.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import br.com.dio.board.dao.BoardDAO;
import br.com.dio.board.dao.ColumnDAO;
import br.com.dio.board.dto.BoardDetailDTO;
import br.com.dio.board.dto.ColumnDTO;
import br.com.dio.board.entity.BoardEntity;
import br.com.dio.board.entity.ColumnEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardService {
	private final Connection connection;
	
    public BoardEntity create(final BoardEntity entity) throws SQLException {
        BoardDAO dao = new BoardDAO(connection);
        ColumnDAO columnDAO = new ColumnDAO(connection);
        
        try{
            dao.create(entity);
            
            List<ColumnEntity> columns = entity.getBoardColumns().stream().map(c -> {
                c.setBoard(entity);
                return c;
            }).toList();
            
            for (ColumnEntity column : columns){
                columnDAO.create(column);
            }
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return entity;
    }
	
    public Optional<BoardEntity> getById(final Long id) throws SQLException {
        BoardDAO dao = new BoardDAO(connection);
        
        ColumnDAO boardColumnDAO = new ColumnDAO(connection);
        Optional<BoardEntity> optional = dao.getById(id);
        
        if (optional.isPresent()){
            BoardEntity entity = optional.get();
            
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
            
            return Optional.of(entity);
        }
        
        return Optional.empty();
    }

    public Optional<BoardDetailDTO> showBoardDetails(final Long id) throws SQLException {
        BoardDAO dao = new BoardDAO(connection);
        ColumnDAO columnDAO = new ColumnDAO(connection);
        
        Optional<BoardEntity> optional = dao.getById(id);
        
        if (optional.isPresent()){
            BoardEntity entity = optional.get();
            
            List<ColumnDTO> columns = columnDAO.findByBoardIdWithDetails(entity.getId()); 
            BoardDetailDTO dto = new BoardDetailDTO(entity.getId(), entity.getName(), columns);
            
            return Optional.of(dto);
        }
        
        return Optional.empty();
    }

	public boolean delete(final Long id) throws SQLException {
		BoardDAO dao = new BoardDAO(connection);
		try{
            if (!dao.exists(id)) {
                return false;
            }
            
            dao.deleteById(id);
            connection.commit();

            return true;
        } catch (SQLException e) {
            connection.rollback();
            
            throw e;
        }		
	}
}
