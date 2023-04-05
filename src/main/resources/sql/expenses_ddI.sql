CREATE TABLE IF NOT EXISTS expenses (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sum BIGINT NOT NULL,
    `date` date NOT NULL,
    `comment` VARCHAR(255),
    category_id BIGINT NOT NULL,
    CONSTRAINT expenses_to_category_fk FOREIGN KEY (category_id) REFERENCES category (id)
);