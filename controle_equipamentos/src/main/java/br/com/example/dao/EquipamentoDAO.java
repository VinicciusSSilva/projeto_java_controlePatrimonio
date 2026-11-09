package br.com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.example.conexao.Conexao;
import br.com.example.model.Equipamento; // Importa a classe mãe
import br.com.example.model.Computador; // Importa a classe filha
import br.com.example.model.Projetor; // Importa a classe filha

public class EquipamentoDAO {
    public boolean cadastrar(Equipamento equipamento) {
        String sql = "INSERT INTO equipamento (patrimonio, descricao, laboratorio, situacao) VALUES (?, ?, ?, ?)";
        Connection conexao = null;// Deixa a conexao vazia para o try
        try {
            conexao = Conexao.conectar();// Tenta conectar
            conexao.setAutoCommit(false); // Desativa o auto commit do sql
            try (PreparedStatement comando = conexao.prepareStatement(sql)) { // Prepara pra inserir no banco
                comando.setInt(1, equipamento.getPatrimonio());
                comando.setString(2, equipamento.getDescricao());
                comando.setString(3, equipamento.getLaboratorio());
                comando.setString(4, equipamento.getSituacao());
                comando.executeUpdate();
            }
            if (equipamento instanceof Computador) {// Condicional para o tipo do equipamento
                Computador comp = (Computador) equipamento;
                String sqlComp = "INSERT INTO computador (equipamento_patrimonio, memoriaRam) VALUES (?, ?)";
                try (PreparedStatement stmtComp = conexao.prepareStatement(sqlComp)) {
                    stmtComp.setInt(1, comp.getPatrimonio());
                    stmtComp.setInt(2, comp.getMemoriaRAM());
                    stmtComp.executeUpdate();
                }
            } else if (equipamento instanceof Projetor) {
                Projetor proj = (Projetor) equipamento;
                String sqlProj = "INSERT INTO projetor (equipamento_patrimonio, luminosidade) VALUES (?, ?)";
                try (PreparedStatement stmtProj = conexao.prepareStatement(sqlProj)) {
                    stmtProj.setInt(1, proj.getPatrimonio());
                    stmtProj.setInt(2, proj.getLuminosidade());
                    stmtProj.executeUpdate();
                }
            }
            conexao.commit(); // Confirma as alterações nas duas tabelas
            return true;

        } catch (SQLException e) {// Se não conectar printa o erro
            if (conexao != null) { // Verifica se a conexao existe para desfazer
                try {
                    conexao.rollback(); // Desfaz a inserção principal se a filha falhar
                } catch (SQLException ex) {
                    System.err.println("Erro ao realizar rollback: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao cadastrar equipamento: " + e.getMessage());
            return false;

        } finally {// Fecha a conexão com o banco em segurança.
            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true); // Restaura o padrao original
                    conexao.close(); // Fecha a conexao definitivamente
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }

    public ArrayList<Equipamento> listar() {
        ArrayList<Equipamento> lista = new ArrayList<>(); // Cria a lista vazia
        String sql = "SELECT e.patrimonio, e.descricao, e.laboratorio, e.situacao, " +
                "c.memoriaRam, p.luminosidade " +
                "FROM equipamento e " +
                "LEFT JOIN computador c ON e.patrimonio = c.equipamento_patrimonio " +
                "LEFT JOIN projetor p ON e.patrimonio = p.equipamento_patrimonio";

        try (Connection conexao = Conexao.conectar(); // Tenta conectar
                PreparedStatement comando = conexao.prepareStatement(sql); // Prepara o select
                ResultSet resultado = comando.executeQuery()) { // Executa e guarda o resultado
            while (resultado.next()) { // Percorre cada linha do resultado
                int patrimonio = resultado.getInt("patrimonio");
                String descricao = resultado.getString("descricao");
                String laboratorio = resultado.getString("laboratorio");
                String situacao = resultado.getString("situacao");

                if (resultado.getObject("memoriaRam") != null) { // Verifica se tem memoria
                    int ram = resultado.getInt("memoriaRam");
                    Computador c = new Computador(patrimonio, descricao, laboratorio, situacao, ram);  
                    lista.add(c); // Adiciona computador na lista
                } else if (resultado.getObject("luminosidade") != null) { // Verifica se tem luminosidade
                    int lum = resultado.getInt("luminosidade");
                    Projetor p = new Projetor(patrimonio, descricao, laboratorio, situacao, lum);
                    lista.add(p); // Adiciona projetor na lista
                }
            }
        } catch (SQLException e) { // Se falhar na busca printa o erro
            System.err.println("Erro ao listar equipamentos: " + e.getMessage());
        }

        return lista; // Retorna a lista completa
    }

    public boolean excluir(int patrimonio) {
        String sql = "DELETE FROM equipamento WHERE patrimonio = ?"; // Busca pelo patrimonio correto

        try (Connection conexao = Conexao.conectar(); // Tenta conectar
                PreparedStatement comando = conexao.prepareStatement(sql)) { // Prepara o delete
            comando.setInt(1, patrimonio);
            return comando.executeUpdate() > 0; // Retorna true se apagou algo
        } catch (SQLException e) { // Se falhar na exclusao printa o erro
            System.out.println("Erro ao excluir o equipamento: " + e.getMessage());
            return false;
        }
    }
}