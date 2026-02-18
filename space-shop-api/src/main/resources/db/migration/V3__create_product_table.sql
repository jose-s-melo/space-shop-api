CREATE TABLE products (
    id BIGSERIAL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    stock INTEGER NOT NULL,
    sku VARCHAR(150) NOT NULL,

    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT chk_price CHECK (price >= 0),
    CONSTRAINT chk_stock CHECK (stock >= 0),
    CONSTRAINT uk_sku UNIQUE (sku)
);