INSERT INTO Document (DTYPE, titre, estDisponible) VALUES ('Livre', 'Le Petit Prince', true);
INSERT INTO Livre (id, auteur) VALUES ((SELECT id FROM Document WHERE titre='Le Petit Prince'), 'Antoine de Saint-Exupéry');

INSERT INTO Document (DTYPE, titre, estDisponible) VALUES ('Livre', '1984', true);
INSERT INTO Livre (id, auteur) VALUES ((SELECT id FROM Document WHERE titre='1984'), 'George Orwell');

INSERT INTO Document (DTYPE, titre, estDisponible) VALUES ('Livre', 'L''Étranger', true);
INSERT INTO Livre (id, auteur) VALUES ((SELECT id FROM Document WHERE titre='L''Étranger'), 'Albert Camus');
