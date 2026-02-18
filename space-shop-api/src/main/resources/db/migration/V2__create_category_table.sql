CREATE TABLE categories (
    id BIGSERIAL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(512),

    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uk_name_categories UNIQUE (name)
);