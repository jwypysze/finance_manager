CREATE TABLE IF NOT EXISTS incomes
(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    income_sum DOUBLE NOT NULL,
    income_date DATE NOT NULL,
    comment VARCHAR(255)
);