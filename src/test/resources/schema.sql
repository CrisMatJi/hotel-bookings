-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by cmateos at 24-03-2023 20:14.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.


-- BEGIN TABLE public.availabilities

BEGIN;

CREATE TABLE IF NOT EXISTS public.hotels (
	id integer NOT NULL AUTO_INCREMENT,
	category integer NOT NULL,
	name character varying(255) NOT NULL,
	PRIMARY KEY(id)
);

COMMIT;

BEGIN;

CREATE TABLE IF NOT EXISTS public.availabilities (
	id integer NOT NULL AUTO_INCREMENT,
	date date NOT NULL,
	id_hotel integer NOT NULL,
	rooms integer NOT NULL,
	PRIMARY KEY(id)
);

COMMIT;

-- END TABLE public.availabilities

-- BEGIN TABLE public.bookings
BEGIN;

CREATE TABLE IF NOT EXISTS public.bookings (
	id integer NOT NULL AUTO_INCREMENT,
	date_from date NOT NULL,
	date_to date NOT NULL,
	email character varying(255) NOT NULL,
	id_hotel integer NOT NULL,
	PRIMARY KEY(id)
);

COMMIT;

-- END TABLE public.bookings

-- BEGIN TABLE public.hotels


-- END TABLE public.hotels

ALTER TABLE IF EXISTS public.availabilities
	ADD CONSTRAINT fk35xd977yrjwcl835n1sciwpva
	FOREIGN KEY (id_hotel)
	REFERENCES public.hotels (id);

ALTER TABLE IF EXISTS public.bookings
	ADD CONSTRAINT fkmk4gdaafea48u3mrymrd96ckf
	FOREIGN KEY (id_hotel)
	REFERENCES public.hotels (id);

