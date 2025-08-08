--liquibase formatted sql
--changeset igor:202508072214
--comment: create cards table

CREATE TABLE CARDS(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	`order` INT NOT NULL,
	board_column_id BIGINT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	finished_at TIMESTAMP NULL,
	is_blocked BOOLEAN DEFAULT FALSE,
	CONSTRAINT boards_columns__cards_fk FOREIGN KEY (board_column_id) REFERENCES BOARDS_COLUMNS(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE CARDS