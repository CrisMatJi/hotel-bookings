CREATE TABLE IF NOT EXISTS public.hotels (
    id serial PRIMARY KEY,
    category integer NOT NULL,
    name character varying(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS public.availabilities (
    id serial PRIMARY KEY,
    date date NOT NULL,
    id_hotel integer NOT NULL,
    rooms integer NOT NULL,
    FOREIGN KEY (id_hotel) REFERENCES public.hotels (id)
);
CREATE TABLE IF NOT EXISTS public.bookings (
    id serial PRIMARY KEY,
    date_from date NOT NULL,
    date_to date NOT NULL,
    email character varying(255) NOT NULL,
    id_hotel integer NOT NULL,
    FOREIGN KEY (id_hotel) REFERENCES public.hotels (id)
);