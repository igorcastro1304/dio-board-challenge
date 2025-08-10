package br.com.dio.board.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class BoardEntity {
	private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ColumnEntity> boardColumns = new ArrayList<>();
    
    public ColumnEntity getInitialColumn(){
        return getFilteredColumn(bc -> bc.getColumnType().equals(ColumnTypeEnum.INITIAL));
    }

    public ColumnEntity getCancelColumn(){
        return getFilteredColumn(bc -> bc.getColumnType().equals(ColumnTypeEnum.CANCEL));
    }

    private ColumnEntity getFilteredColumn(Predicate<ColumnEntity> filter){
        return boardColumns.stream()
                .filter(filter)
                .findFirst().orElseThrow();
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public List<ColumnEntity> getBoardColumns() {
    	return boardColumns;	
    } 
}
