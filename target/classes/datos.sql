-- INSERTS para public.hotels
INSERT INTO public.hotels (category, name) VALUES
(1, 'Hotel Plaza'),
(2, 'Hotel Madrid'),
(3, 'Hotel Barcelona'),
(2, 'Hotel Valencia'),
(1, 'Hotel Marbella'),
(3, 'Hotel Granada'),
(2, 'Hotel Toledo'),
(1, 'Hotel Sevilla'),
(3, 'Hotel Malaga'),
(1, 'Hotel Cordoba');
-- INSERTS para public.availabilities
INSERT INTO public.availabilities (date, id_hotel, rooms) VALUES
('2023-04-01', 1, 10),
('2023-04-02', 1, 8),
('2023-04-03', 1, 12),
('2023-04-04', 3, 9),
('2023-04-05', 3, 11),
('2023-04-06', 5, 7),
('2023-04-07', 7, 5),
('2023-04-08', 8, 6),
('2023-04-09', 6, 9),
('2023-04-10', 6, 10);
-- INSERTS para public.bookings
INSERT INTO public.bookings (date_from, date_to, email, id_hotel) VALUES
('2023-04-01', '2023-04-05', 'usuario1@gmail.com', 1),
('2023-04-03', '2023-04-06', 'usuario2@gmail.com', 1),
('2023-04-05', '2023-04-08', 'usuario3@gmail.com', 1),
('2023-04-07', '2023-04-10', 'usuario4@gmail.com', 1),
('2023-04-01', '2023-04-06', 'usuario5@gmail.com', 2),
('2023-04-03', '2023-04-08', 'usuario6@gmail.com', 2),
('2023-04-05', '2023-04-10', 'usuario7@gmail.com', 2),
('2023-04-02', '2023-04-06', 'usuario8@gmail.com', 3),
('2023-04-04', '2023-04-07', 'usuario9@gmail.com', 3),
('2023-04-06', '2023-04-09', 'usuario10@gmail.com', 3);