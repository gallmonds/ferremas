USE ferremas_db;

SHOW VARIABLES LIKE 'default_storage_engine';

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    parent_id INT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE brands (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    product_code VARCHAR(20) UNIQUE NOT NULL,
    brand_code VARCHAR(20),
    name VARCHAR(150) NOT NULL,
    description TEXT,
    category_id INT DEFAULT NULL,
    brand_id INT NOT NULL,
    stock INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE RESTRICT
);

CREATE TABLE prices (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    product_id INT NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(256) NOT NULL,
    salt VARCHAR(64) NOT NULL,
    role ENUM('client','vendor','admin') DEFAULT 'client',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO categories (name, parent_id) VALUES ('Herramientas Manuales', NULL);
INSERT INTO categories (name, parent_id) VALUES('Materiales Básicos', NULL);
INSERT INTO categories (name, parent_id) VALUES('Equipos de Seguridad', NULL);
INSERT INTO categories (name, parent_id) VALUES('Tornillos y Anclajes', NULL);
INSERT INTO categories (name, parent_id) VALUES ('Fijaciones y Adhesivos', NULL);
INSERT INTO categories (name, parent_id) VALUES('Equipos de Medición', NULL);
INSERT INTO categories (name, parent_id) VALUES ('Martillos', 1);
INSERT INTO categories (name, parent_id) VALUES ('Destornilladores', 1);
INSERT INTO categories (name, parent_id) VALUES ('Llaves', 1);
INSERT INTO categories (name, parent_id) VALUES ('Herramientas Eléctricas', 1);
INSERT INTO categories (name, parent_id) VALUES ('Taladros', 1);
INSERT INTO categories (name, parent_id) VALUES ('Sierras', 1);
INSERT INTO categories (name, parent_id) VALUES ('Lijadoras', 1);
INSERT INTO categories (name, parent_id) VALUES ('Materiales de Construccion', 1);
INSERT INTO categories (name, parent_id) VALUES ('Cemento', 2);
INSERT INTO categories (name, parent_id) VALUES ('Arena', 2);
INSERT INTO categories (name, parent_id) VALUES ('Ladrillos', 2);
INSERT INTO categories (name, parent_id) VALUES ('Acabados', 2);
INSERT INTO categories (name, parent_id) VALUES ('Pinturas', 2);
INSERT INTO categories (name, parent_id) VALUES ('Barnices', 2);
INSERT INTO categories (name, parent_id) VALUES ('Cerámicos', 2);
INSERT INTO categories (name, parent_id) VALUES ('Cascos', 3);
INSERT INTO categories (name, parent_id) VALUES ('Guantes', 3);
INSERT INTO categories (name, parent_id) VALUES ('Lentes de Seguridad', 3);
INSERT INTO categories (name, parent_id) VALUES ('Accesorios Varios', 3);

INSERT INTO brands (name) VALUES ('DeWalt');
    INSERT INTO brands (name) VALUES ('Makita');
   INSERT INTO brands (name) VALUES  ('Bosch');
   INSERT INTO brands (name) VALUES  ('Hilti');
 INSERT INTO brands (name) VALUES    ('Milwaukee');
 INSERT INTO brands (name) VALUES    ('Black+Decker');
 INSERT INTO brands (name) VALUES    ('Stanley');
 INSERT INTO brands (name) VALUES    ('RIDGID');
 INSERT INTO brands (name) VALUES    ('Ryobi');
 INSERT INTO brands (name) VALUES    ('Metabo');
  INSERT INTO brands (name) VALUES   ('Würth');
  INSERT INTO brands (name) VALUES   ('Senco');
  INSERT INTO brands (name) VALUES   ('Simpson Strong-Tie');
   INSERT INTO brands (name) VALUES  ('ITW');
  INSERT INTO brands (name) VALUES   ('Fastenal');
 INSERT INTO brands (name) VALUES    ('Powers Fasteners');
 INSERT INTO brands (name) VALUES    ('Bostitch');
  INSERT INTO brands (name) VALUES   ('Fischer Fixing Systems');
 INSERT INTO brands (name) VALUES    ('Ramset');
 INSERT INTO brands (name) VALUES    ('Heco');
  INSERT INTO brands (name) VALUES   ('3M');
   INSERT INTO brands (name) VALUES  ('Honeywell');
  INSERT INTO brands (name) VALUES   ('MSA Safety');
 INSERT INTO brands (name) VALUES    ('Delta Plus');
 INSERT INTO brands (name) VALUES    ('JSP Safety');
 INSERT INTO brands (name) VALUES    ('Uvex');
  INSERT INTO brands (name) VALUES   ('Peltor');
  INSERT INTO brands (name) VALUES   ('Ansell');
 INSERT INTO brands (name) VALUES    ('Dickies');
  INSERT INTO brands (name) VALUES   ('Caterpillar');
  INSERT INTO brands (name) VALUES   ('Cemex');
 INSERT INTO brands (name) VALUES    ('Holcim');
 INSERT INTO brands (name) VALUES    ('Lafarge');
 INSERT INTO brands (name) VALUES    ('Knauf');
  INSERT INTO brands (name) VALUES   ('Saint-Gobain');
  INSERT INTO brands (name) VALUES   ('USG Boral');
  INSERT INTO brands (name) VALUES   ('Rockwool');
  INSERT INTO brands (name) VALUES   ('Owens Corning');
   INSERT INTO brands (name) VALUES  ('James Hardie');
  INSERT INTO brands (name) VALUES   ('Kingspan');
   
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00001', 'BR001', 'Martillos DeWalt Avanzado', 'Martillos DeWalt Avanzado de alta calidad para aplicaciones profesionales.', 7, 1, 43, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00002', 'BR002', 'Destornilladores Makita Avanzado', 'Destornilladores Makita Avanzado de alta calidad para aplicaciones profesionales.', 8, 2, 40, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00003', 'BR003', 'Llaves Bosch Avanzado', 'Llaves Bosch Avanzado de alta calidad para aplicaciones profesionales.', 9, 3, 20, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00004', 'BR004', 'Herramientas Eléctricas 3M Avanzado', 'Herramientas Eléctricas 3M Avanzado de alta calidad para aplicaciones profesionales.', 10, 4, 37, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00005', 'BR005', 'Taladros Honeywell Avanzado', 'Taladros Honeywell Avanzado de alta calidad para aplicaciones profesionales.', 11, 5, 19, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00006', 'BR006', 'Sierras Würth Avanzado', 'Sierras Würth Avanzado de alta calidad para aplicaciones profesionales.', 12, 6, 19, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00007', 'BR007', 'Lijadoras Simpson Strong-Tie Avanzado', 'Lijadoras Simpson Strong-Tie Avanzado de alta calidad para aplicaciones profesionales.', 13, 7, 21, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00008', 'BR008', 'Materiales de Construccion Knauf Avanzado', 'Materiales de Construccion Knauf Avanzado de alta calidad para aplicaciones profesionales.', 14, 8, 46, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00009', 'BR009', 'Cemento Cemex Avanzado', 'Cemento Cemex Avanzado de alta calidad para aplicaciones profesionales.', 15, 9, 39, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00010', 'BR010', 'Arena Delta Plus Avanzado', 'Arena Delta Plus Avanzado de alta calidad para aplicaciones profesionales.', 16, 10, 40, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00011', 'BR001', 'Ladrillos DeWalt Avanzado', 'Ladrillos DeWalt Avanzado de alta calidad para aplicaciones profesionales.', 17, 1, 42, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00012', 'BR002', 'Acabados Makita Avanzado', 'Acabados Makita Avanzado de alta calidad para aplicaciones profesionales.', 18, 2, 12, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00013', 'BR003', 'Pinturas Bosch Avanzado', 'Pinturas Bosch Avanzado de alta calidad para aplicaciones profesionales.', 19, 3, 32, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00014', 'BR004', 'Barnices 3M Avanzado', 'Barnices 3M Avanzado de alta calidad para aplicaciones profesionales.', 20, 4, 15, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00015', 'BR005', 'Cerámicos Honeywell Avanzado', 'Cerámicos Honeywell Avanzado de alta calidad para aplicaciones profesionales.', 21, 5, 43, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00016', 'BR006', 'Cascos Würth Avanzado', 'Cascos Würth Avanzado de alta calidad para aplicaciones profesionales.', 22, 6, 35, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00017', 'BR007', 'Guantes Simpson Strong-Tie Avanzado', 'Guantes Simpson Strong-Tie Avanzado de alta calidad para aplicaciones profesionales.', 23, 7, 36, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00018', 'BR008', 'Lentes de Seguridad Knauf Avanzado', 'Lentes de Seguridad Knauf Avanzado de alta calidad para aplicaciones profesionales.', 24, 8, 12, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO products (product_code, brand_code, name, description, category_id, brand_id, stock, created_at, updated_at) VALUES ('P00019', 'BR009', 'Accesorios Varios Cemex Avanzado', 'Accesorios Varios Cemex Avanzado de alta calidad para aplicaciones profesionales.', 25, 9, 46, '2025-05-11 22:01:46', '2025-05-11 22:01:46');

INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (1, 93.41, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (2, 142.7, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (3, 179.35, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (4, 107.63, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (5, 54.37, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (6, 236.07, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (7, 288.91, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (8, 174.19, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (9, 205.81, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (10, 281.86, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (11, 250.19, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (12, 13.3, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (13, 94.75, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (14, 48.76, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (15, 187.34, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (16, 192.22, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (17, 87.01, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (18, 90.36, '2025-05-11 22:01:46', '2025-05-11 22:01:46');
INSERT INTO prices (product_id, amount, created_at, updated_at) VALUES (19, 46.79, '2025-05-11 22:01:46', '2025-05-11 22:01:46');

