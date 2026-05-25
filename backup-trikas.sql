--
-- PostgreSQL database dump
--

-- Dumped from database version 13.21
-- Dumped by pg_dump version 13.21

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    nome character varying(150) NOT NULL,
    email character varying(150) NOT NULL,
    cpf character varying(11) NOT NULL,
    matricula character varying(30) NOT NULL,
    senha_hash character varying(255),
    cargo character varying(100) NOT NULL,
    perfil character varying(50) NOT NULL,
    telefone character varying(20),
    data_nascimento date,
    ativo boolean DEFAULT true NOT NULL,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_upd timestamp without time zone,
    CONSTRAINT chk_usuario_perfil CHECK (((perfil IS NOT NULL) AND ((perfil)::text = ANY ((ARRAY['ADMIN'::character varying, 'USER'::character varying, 'SYSTEM'::character varying])::text[])) AND (((perfil)::text <> 'SYSTEM'::text) OR ((COALESCE(cargo, ''::character varying))::text = 'SYSADMIN'::text))))
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, nome, email, cpf, matricula, senha_hash, cargo, perfil, telefone, data_nascimento, ativo, created, last_upd) FROM stdin;
6	Gabriel Celestino	celestino@gmail.com	12345678100	A0148437	\N	DESENVOLVEDOR	ADMIN	11920081095	2005-06-23	t	2026-05-23 22:32:06.380536	2026-05-24 10:59:32.704424
7	Joao Lima	joaolimatech@gmail.com	48638821816	A0172716	$2a$12$9gIYeo0BTlRFJWK99pIFjOv7.tdvTuVpsxpYuMVu6y3qYPoGr2uTK	DESENVOLVEDOR	ADMIN	11930621197	2004-08-09	t	2026-05-23 22:32:12.034467	2026-05-24 11:00:25.092963
11	Bruno Guterres	bru.gutz@gmail.com	75412265841	A0132569	\N	VENDEDOR	ADMIN	11999081027	2004-10-10	t	2026-05-24 11:05:04.818277	2026-05-24 11:05:04.818277
10	SYSADMIN	sysadmin@trikas.com	00000000000	SYS001	\N	SYSADMIN	SYSTEM	\N	\N	t	2026-05-23 22:34:41.015932	\N
13	Ana Martins	ana.martins@gmail.com	12345678901	A0132570	\N	Vendedor	USER	11987654321	1998-03-15	t	2026-05-24 11:32:36.389322	2026-05-24 11:32:36.389322
14	Carlos Henrique	carlos.henrique@gmail.com	23456789012	A0132571	\N	Gerente	USER	11976543210	1992-07-22	t	2026-05-24 11:32:36.389322	2026-05-24 11:32:36.389322
15	Mariana Costa	mariana.costa@gmail.com	34567890123	A0132572	\N	Analista	USER	11965432109	1995-11-09	t	2026-05-24 11:32:36.389322	2026-05-24 11:32:36.389322
16	Rafael Lima	rafael.lima@gmail.com	45678901234	A0132573	\N	Estagiario	USER	11954321098	2003-01-30	t	2026-05-24 11:32:36.389322	2026-05-24 11:32:36.389322
17	Juliana Rocha	juliana.rocha@gmail.com	56789012345	A0132574	\N	Vendedor	USER	11943210987	1999-05-18	t	2026-05-24 11:32:36.389322	2026-05-24 11:32:36.389322
\.


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 17, true);


--
-- Name: usuario usuario_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cpf_key UNIQUE (cpf);


--
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- Name: usuario usuario_matricula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_matricula_key UNIQUE (matricula);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT USAGE ON SCHEMA public TO user_trikas;


--
-- Name: TABLE usuario; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.usuario TO user_trikas;


--
-- Name: SEQUENCE usuario_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.usuario_id_seq TO user_trikas;


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON SEQUENCES  TO user_trikas;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT SELECT,INSERT,DELETE,UPDATE ON TABLES  TO user_trikas;


--
-- PostgreSQL database dump complete
--

