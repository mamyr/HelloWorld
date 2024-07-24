-- Database: my_ticket_service_db

DROP DATABASE IF EXISTS my_ticket_service_db;

CREATE DATABASE my_ticket_service_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-07-13 16:23:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4807 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 846 (class 1247 OID 16410)
-- Name: ticket_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.ticket_type AS ENUM (
    'DAY',
    'WEEK',
    'MONTH',
    'YEAR'
);


ALTER TYPE public.ticket_type OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 24600)
-- Name: public.ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."public.ticket" (
    id integer NOT NULL,
    user_id integer,
    ticket_type character varying(255),
    creation_date timestamp(6) without time zone
);


ALTER TABLE public."public.ticket" OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24605)
-- Name: public.user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."public.user" (
    id integer NOT NULL,
    name character varying(255),
    creation_date timestamp(6) without time zone DEFAULT now()
);


ALTER TABLE public."public.user" OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16399)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 4808 (class 0 OID 0)
-- Dependencies: 215
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."public.user".id;


--
-- TOC entry 4800 (class 0 OID 24600)
-- Dependencies: 217
-- Data for Name: public.ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."public.ticket" (id, user_id, ticket_type, creation_date) FROM stdin;
\.


--
-- TOC entry 4801 (class 0 OID 24605)
-- Dependencies: 218
-- Data for Name: public.user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."public.user" (id, name, creation_date) FROM stdin;
\.



--
-- TOC entry 4809 (class 0 OID 0)
-- Dependencies: 215
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 1, true);


--
-- TOC entry 4651 (class 2606 OID 24604)
-- Name: public.ticket public.ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."public.ticket"
    ADD CONSTRAINT "public.ticket_pkey" PRIMARY KEY (id);


--
-- TOC entry 4653 (class 2606 OID 24609)
-- Name: public.user public.user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."public.user"
    ADD CONSTRAINT "public.user_pkey" PRIMARY KEY (id);


--
-- TOC entry 4654 (class 2606 OID 24610)
-- Name: public.ticket fkl9fonxt0topj9twjciotlj5or; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."public.ticket"
    ADD CONSTRAINT fkl9fonxt0topj9twjciotlj5or FOREIGN KEY (user_id) REFERENCES public."public.user"(id);


-- Completed on 2024-07-13 16:23:44

--
-- PostgreSQL database dump complete
--

