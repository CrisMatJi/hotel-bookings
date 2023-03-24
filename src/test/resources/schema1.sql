CREATE TABLE Hotels (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    category INT
);

CREATE TABLE Availabilities (
    id INT PRIMARY KEY,
    date DATE,
    id_hotel INT,
    rooms INT,
    FOREIGN KEY (id_hotel) REFERENCES Hotels(id)
);

CREATE TABLE Bookings (
    id INT PRIMARY KEY,
    id_hotel INT,
    date_from DATE,
    date_to DATE,
    email VARCHAR(255),
    FOREIGN KEY (id_hotel) REFERENCES Hotels(id)
);
