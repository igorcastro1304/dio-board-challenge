package br.com.dio.board.ui;

import br.com.dio.board.dto.BoardDetailDTO;
import br.com.dio.board.dto.ColumnInfoDTO;
import br.com.dio.board.entity.ColumnEntity;
import br.com.dio.board.entity.BoardEntity;
import br.com.dio.board.entity.CardEntity;
import br.com.dio.board.service.ColumnService;
import br.com.dio.board.service.BoardService;
import br.com.dio.board.service.CardService;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static br.com.dio.board.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    private final BoardEntity entity;

    public void execute() {
        try {
            System.out.printf("Bem vindo ao board %s, selecione a operação desejada\n", entity.getId());
            
            int option = -1;
            
            while (option != 9) {
                System.out.println("1 - Criar um card");
                System.out.println("2 - Mover um card");
                System.out.println("3 - Bloquear um card");
                System.out.println("4 - Desbloquear um card");
                System.out.println("5 - Cancelar um card");
                System.out.println("6 - Ver board");
                System.out.println("7 - Ver coluna com cards");
                System.out.println("8 - Ver card");
                System.out.println("9 - Voltar para o menu anterior um card");
                System.out.println("10 - Sair");
                
                option = scanner.nextInt();
                
                switch (option) {
                    case 1:
                    	createCard();
                    	break;                    	
                    case 2:
                    	moveCardToNextColumn();
                    	break;
                    case 3:
                    	blockCard();
                    	break;
                    case 4:
                    	unblockCard();
                    	break;
                    case 5:
                    	cancelCard();
                    	break;
                    case 6:
                    	showBoard();
                    	break;
                    case 7:
                    	showColumn();
                    	break;
                    case 8:
                    	showCard();
                    	break;
                    case 9:
                    	System.out.println("Voltando para o menu anterior");
                    	break;
                    case 10:
                    	System.exit(0);
                    default:
                    	System.out.println("Opção inválida, informe uma opção do menu");
                }
            }
            
        }catch (SQLException ex){
            ex.printStackTrace();
            
            System.exit(0);
        }
    }

    private void createCard() throws SQLException{
        CardEntity card = new CardEntity();
        
        System.out.println("Informe o título do card");
        card.setTitle(scanner.next());
        
        System.out.println("Informe a descrição do card");
        card.setDescription(scanner.next());
        
        card.setColumn(entity.getInitialColumn());
        
        try(var connection = getConnection()){
            new CardService(connection).create(card);
        }
    }

    private void moveCardToNextColumn() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a próxima coluna");
        
        Long cardId = scanner.nextLong();
        List<ColumnInfoDTO> boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new ColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getColumnType()))
                .toList();
        
        try(Connection connection = getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void blockCard() throws SQLException {
        System.out.println("Informe o id do card que será bloqueado");
        Long cardId = scanner.nextLong();
        
        List<ColumnInfoDTO> columnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new ColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getColumnType()))
                .toList();
        
        try(Connection connection = getConnection()){
            new CardService(connection).block(cardId, columnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void unblockCard() throws SQLException {
        System.out.println("Informe o id do card que será desbloqueado");
        Long cardId = scanner.nextLong();
        
        System.out.println("Informe o motivo do desbloqueio do card");

        try(var connection = getConnection()){
            new CardService(connection).unblock(cardId);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void cancelCard() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a coluna de cancelamento");
        Long cardId = scanner.nextLong();
        
        ColumnEntity cancelColumn = entity.getCancelColumn();
        List<ColumnInfoDTO> columnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new ColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getColumnType()))
                .toList();
        
        try(Connection connection = getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), columnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void showBoard() throws SQLException {
        try(Connection connection = getConnection()){
            Optional<BoardDetailDTO> optional = new BoardService(connection).showBoardDetails(entity.getId());
            
            optional.ifPresent(b -> {
                System.out.printf("Board [%s,%s]\n", b.id(), b.name());
                b.columns().forEach(c ->
                        System.out.printf("Coluna [%s] tipo: [%s] tem %s cards\n", c.name(), c.type(), c.cardsAmount())
                );
            });
        }
    }

    private void showColumn() throws SQLException {
        List<Long> columnsIds = entity.getBoardColumns().stream().map(ColumnEntity::getId).toList();
        Long selectedColumnId = -1L;
        
        while (!columnsIds.contains(selectedColumnId)){
            System.out.printf("Escolha uma coluna do board %s pelo id\n", entity.getName());
            entity.getBoardColumns().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getName(), c.getColumnType()));
            
            selectedColumnId = scanner.nextLong();
        }
        
        try(Connection connection = getConnection()){
            var column = new ColumnService(connection).getById(selectedColumnId);
            
            column.ifPresent(co -> {
                System.out.printf("Coluna %s tipo %s\n", co.getName(), co.getColumnType());
                co.getCards().forEach(ca -> System.out.printf("Card %s - %s\nDescrição: %s",
                        ca.getId(), ca.getTitle(), ca.getDescription()));
            });
        }
    }

    private void showCard() throws SQLException {
        System.out.println("Informe o id do card que deseja visualizar");
        Long selectedCardId = scanner.nextLong();
        
        try(Connection connection  = getConnection()){
            new CardService(connection).getById(selectedCardId)
                    .ifPresentOrElse(
                            c -> {
                                System.out.printf("Card %s - %s.\n", c.id(), c.title());
                                System.out.printf("Descrição: %s\n", c.description());
                                System.out.println(c.isBlocked() ?
                                        "Está bloqueado." :
                                        "Não está bloqueado");
                                System.out.printf("Está no momento na coluna %s - %s\n", c.columnId(), c.columnName());
                            },
                            () -> System.out.printf("Não existe um card com o id %s\n", selectedCardId));
        }
    }
}