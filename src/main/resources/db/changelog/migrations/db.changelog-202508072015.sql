--liquibase formatted sql
--changeset igor:202508072214
--comment: create boards table

CREATE TABLE BOARDS(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS