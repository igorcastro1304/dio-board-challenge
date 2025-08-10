package br.com.dio.board.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class ColumnEntity {
	private Long id;
	private String name;
	private int order;
	private ColumnTypeEnum columnType;
	private BoardEntity board = new BoardEntity();
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<CardEntity> cards = new ArrayList<>();
	
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
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public BoardEntity getBoard() {
		return board;
	}
	
	public void setBoard(BoardEntity board) {
		this.board = board;
	}
	
	public ColumnTypeEnum getColumnType() {
		return columnType;
	}
	
	public void setColumnType(ColumnTypeEnum columnType) {
		this.columnType = columnType;
	}
	
	public List<CardEntity> getCards() {
		return cards;
	}
	
	public void setCards(List<CardEntity> cards) {
		this.cards = cards;
	}
}
