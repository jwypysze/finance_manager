create table if not exists expenses
(
	id bigint primary key auto_increment,
    expense_sum DOUBLE not null,
    expense_date date not null,
    comment varchar(255),
    category_id bigint not null,
    CONSTRAINT expenses_to_category_fk FOREIGN KEY (category_id) REFERENCES category (id)
);