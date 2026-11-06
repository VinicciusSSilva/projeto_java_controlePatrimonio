package br.com.example.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/laboratorio_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        try {
            // Carregamento explícito do driver (excelente para documentar no relatório
            // ABNT)
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro: Driver MySQL Connector/J não encontrado no Build Path.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar à base de dados laboratorio_db.", e);
        }
    }
}