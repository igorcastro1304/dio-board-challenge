package br.com.dio.board.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CardEntity {
	private Long id;
	private String title;
	private String description;
	private int order;
	private LocalDateTime createdAt;
	private LocalDateTime finishedAt;
	private boolean isBlocked;
	private ColumnEntity column = new ColumnEntity();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getFinishedAt() {
		return finishedAt;
	}
	
	public void setFinishedAt(LocalDateTime finishedAt) {
		this.finishedAt = finishedAt;
	}
	
	public boolean isBlocked() {
		return isBlocked;
	}
	
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	public ColumnEntity getColumn() {
		return column;
	}
	
	public void setColumn(ColumnEntity column) {
		this.column = column;
	}
}
