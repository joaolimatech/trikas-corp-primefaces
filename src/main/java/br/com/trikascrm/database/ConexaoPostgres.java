package br.com.trikascrm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConexaoPostgres {

    private static final Logger log = LoggerFactory.getLogger(ConexaoPostgres.class);

//    private static final String URL = "jdbc:postgresql://localhost:5432/trikas_db";
//    private static final String USUARIO = "user_trikas";
//    private static final String SENHA = "user_trikas";
    private static final String URL =
        "jdbc:postgresql://db.ripouszamxoylkbssmkt.supabase.co:5432/postgres?sslmode=require";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "blXAec4QhhXgoNTx";


    private ConexaoPostgres() {
    }

    public static Connection getConnection() throws SQLException {
        log.info("Abrindo conexao com PostgreSQL em {}", URL);
        carregarDriverPostgres();
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    private static void carregarDriverPostgres() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL nao encontrado no classpath.", e);
        }
    }
}
