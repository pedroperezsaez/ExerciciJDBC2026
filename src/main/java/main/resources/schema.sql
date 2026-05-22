-- Esquema de base de dades
CREATE DATABASE IF NOT EXISTS biblioteca_exercici;
USE biblioteca_exercici;

-- Eliminar taules en ordre correcte per evitar errors de claus estrangeres
DROP TABLE IF EXISTS prestecs;
DROP TABLE IF EXISTS llibres_autors;
DROP TABLE IF EXISTS llibres;
DROP TABLE IF EXISTS autors;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS socis;

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE autors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    nacionalitat VARCHAR(50)
);

CREATE TABLE llibres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titol VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    categoria_id INT,
    disponible BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoria_id) REFERENCES categories(id)
);

-- Relació molts-a-molts entre llibres i autors
CREATE TABLE llibres_autors (
    llibre_id INT,
    autor_id INT,
    PRIMARY KEY (llibre_id, autor_id),
    FOREIGN KEY (llibre_id) REFERENCES llibres(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES autors(id) ON DELETE CASCADE
);

CREATE TABLE socis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    data_alta DATE DEFAULT (CURDATE())
);

CREATE TABLE prestecs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    llibre_id INT,
    soci_id INT,
    data_prestec DATE NOT NULL,
    data_retorn_prevista DATE NOT NULL,
    data_retorn_real DATE DEFAULT NULL,
    FOREIGN KEY (llibre_id) REFERENCES llibres(id),
    FOREIGN KEY (soci_id) REFERENCES socis(id)
);

-- Dades inicials ampliades
INSERT INTO categories (nom) VALUES 
('Novel·la'), ('Poesia'), ('Assaig'), ('Ciència Ficció'), ('Infantil'), ('Teatre'), ('Història');

INSERT INTO autors (nom, nacionalitat) VALUES 
('Mercè Rodoreda', 'Catalana'), 
('Joan Sales', 'Catalana'), 
('Quim Monzó', 'Catalana'),
('Isaac Asimov', 'Estatunidenca'),
('Victor Català', 'Catalana'),
('Josep Pla', 'Catalana'),
('Montserrat Roig', 'Catalana'),
('J.K. Rowling', 'Britànica'),
('George Orwell', 'Britànica'),
('Salvador Espriu', 'Catalana');

INSERT INTO llibres (titol, isbn, categoria_id) VALUES 
('La plaça del Diamant', '978-8429760798', 1), 
('Mirall trencat', '978-8429761153', 1), 
('Incerta glòria', '978-8429755138', 1), 
('El perquè de tot plegat', '978-8477271031', 1),
('Fundació', '978-0553293357', 4),
('Solitud', '978-8429760330', 1),
('El quadern gris', '978-8423351138', 3),
('L''hora violeta', '978-8439413241', 1),
('Harry Potter i la pedra filosofal', '978-8495951007', 5),
('1984', '978-8499307763', 4),
('Cementiri de Sinera', '978-8429760224', 2),
('Primera història d''Esther', '978-8429760118', 6);

-- Relació llibres-autors (alguns llibres podrien tenir co-autors si volguéssim)
INSERT INTO llibres_autors (llibre_id, autor_id) VALUES 
(1, 1), (2, 1), (3, 2), (4, 3), (5, 4), (6, 5), (7, 6), (8, 7), (9, 8), (10, 9), (11, 10), (12, 10);

INSERT INTO socis (nom, email) VALUES 
('Pere Pi', 'pere@email.com'), 
('Maria Garcia', 'maria@email.com'), 
('Joan Marc', 'joan@email.com'),
('Anna Pou', 'anna@email.com'),
('Lluís Font', 'lluis@email.com'),
('Carme Soler', 'carme@email.com');

-- Alguns préstecs inicials per tenir dades
INSERT INTO prestecs (llibre_id, soci_id, data_prestec, data_retorn_prevista, data_retorn_real) VALUES 
(1, 1, '2024-05-01', '2024-05-15', '2024-05-12'),
(2, 2, '2024-05-10', '2024-05-25', NULL);

-- Marquem el llibre 2 com a no disponible ja que està prestat
UPDATE llibres SET disponible = FALSE WHERE id = 2;
