--liquibase formatted sql
--changeset igor:202508072214
--comment: create board_column table

CREATE TABLE BOARDS_COLUMNS(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	`order` INT NOT NULL,
	column_type VARCHAR(7) NOT NULL,
	board_id BIGINT NOT NULL,
	CONSTRAINT boards__boards_columns_fk FOREIGN KEY (board_id) REFERENCES BOARDS(id) ON DELETE CASCADE,
	CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board_id, `order`)
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS_COLUMNS