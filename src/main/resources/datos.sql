INSERT INTO Hotels (id, name, category) VALUES (1, 'Hotel ABC', 4);
INSERT INTO Hotels (id, name, category) VALUES (2, 'Hotel XYZ', 3);
INSERT INTO Hotels (id, name, category) VALUES (3, 'Hotel 123', 2);

INSERT INTO Availabilities (id, date, id_hotel, rooms) VALUES (1, '2022-01-01', 1, 10);
INSERT INTO Availabilities (id, date, id_hotel, rooms) VALUES (2, '2022-01-01', 2, 5);
INSERT INTO Availabilities (id, date, id_hotel, rooms) VALUES (3, '2022-01-02', 1, 8);
INSERT INTO Availabilities (id, date, id_hotel, rooms) VALUES (4, '2022-01-02', 2, 2);

INSERT INTO Bookings (id, id_hotel, date_from, date_to, email) VALUES (1, 1, '2022-01-01', '2022-01-05', 'cliente1@example.com');
INSERT INTO Bookings (id, id_hotel, date_from, date_to, email) VALUES (2, 2, '2022-01-02', '2022-01-03', 'cliente2@example.com');
INSERT INTO Bookings (id, id_hotel, date_from, date_to, email) VALUES (3, 1, '2022-01-03', '2022-01-05', 'cliente3@example.com');
INSERT INTO Bookings (id, id_hotel, date_from, date_to, email) VALUES (4, 3, '2022-01-02', '2022-01-04', 'cliente4@example.com');

