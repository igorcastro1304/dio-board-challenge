package br.com.dio.board.ui;

import br.com.dio.board.entity.ColumnEntity;
import br.com.dio.board.entity.ColumnTypeEnum;
import br.com.dio.board.entity.BoardEntity;
import br.com.dio.board.service.BoardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static br.com.dio.board.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.board.entity.ColumnTypeEnum.CANCEL;
import static br.com.dio.board.entity.ColumnTypeEnum.FINAL;
import static br.com.dio.board.entity.ColumnTypeEnum.INITIAL;
import static br.com.dio.board.entity.ColumnTypeEnum.PENDING;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada");
        
        int option = -1;
        
        while (option != 4){
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            option = scanner.nextInt();
            
            switch (option){
                case 1:
                	createBoard();
                	break;
                case 2: 
                	selectBoard();
                	break;
                case 3:
                	deleteBoard();
                	break;
                case 4:
                	System.exit(0);
                	break;
                default:
                	System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {
        BoardEntity entity = new BoardEntity();
        
        System.out.println("Informe o nome do seu board");
        entity.setName(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim informe quantas, senão digite '0'");
        int additionalColumns = scanner.nextInt();

        List<ColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        String initialColumnName = scanner.next();
        
        ColumnEntity initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
        
            String pendingColumnName = scanner.next();
            ColumnEntity pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final");
        String finalColumnName = scanner.next();
        
        ColumnEntity finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do baord");
        String cancelColumnName = scanner.next();
        
        ColumnEntity cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        
        try(Connection connection = getConnection()){
            BoardService service = new BoardService(connection);
          
            service.create(entity);
        }

    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o id do board que deseja selecionar");
        Long id = scanner.nextLong();
        
        try(Connection connection = getConnection()){
            BoardService boardService = new BoardService(connection);
            
            Optional<BoardEntity> optional = boardService.getById(id);
            
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um board com id %s\n", id)
            );
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o id do board que será excluido");
        
        Long id = scanner.nextLong();
        
        try(Connection connection = getConnection()){
            BoardService service = new BoardService(connection);
            
            if (service.delete(id)){
                System.out.printf("O board %s foi excluido\n", id);
            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }
        }
    }

    private ColumnEntity createColumn(final String name, final ColumnTypeEnum type, final int order){
        ColumnEntity boardColumn = new ColumnEntity();
        
        boardColumn.setName(name);
        boardColumn.setColumnType(type);
        boardColumn.setOrder(order);
        
        return boardColumn;
    }
}