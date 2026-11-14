package br.com.example.controller;

import java.util.ArrayList;

import br.com.example.DAO.EquipamentoDAO;
import br.com.example.model.Equipamento;

public class EquipamentoController {

    private EquipamentoDAO equipamentoDAO;

    public EquipamentoController() {
        this.equipamentoDAO = new EquipamentoDAO();
    }

    public boolean cadastrarEquipamento(Equipamento equipamento) {
        // Validações básicas antes de mandar para o DAO (opcional, conforme
        // necessidade)
        if (equipamento == null) {
            System.err.println("Erro: O equipamento não pode ser nulo.");
            return false;
        }
        return equipamentoDAO.cadastrar(equipamento);
    }

    public ArrayList<Equipamento> listarEquipamentos() {
        return equipamentoDAO.listar();
    }

    public boolean excluirEquipamento(int patrimonio) {
        if (patrimonio <= 0) {
            System.err.println("Erro: Patrimônio inválido para exclusão.");
            return false;
        }
        return equipamentoDAO.excluir(patrimonio);
    }

    public Equipamento buscarEquipamento(int patrimonio) {
        if (patrimonio <= 0) { // Valida se o patrimonio e positivo
            System.err.println("Erro: Patrimônio inválido para busca.");
            return null; // Retorna nulo para patrimonio invalido
        }
        return equipamentoDAO.buscar(patrimonio); // Solicita a busca ao DAO
    }

    
    public ArrayList<Equipamento> listarPorLaboratorio(String laboratorio) {
        if (laboratorio == null || laboratorio.trim().isEmpty()) {
            return new ArrayList<>(); // Retorna vazio se a busca for inválida
        }
        return equipamentoDAO.listarPorLaboratorio(laboratorio);
    }

    public boolean atualizarEquipamento(Equipamento equipamento) {
        if (equipamento == null || equipamento.getPatrimonio() <= 0) { // Valida se o objeto e patrimonio sao validos
            System.err.println("Erro: Equipamento ou patrimônio inválido para atualização.");
            return false; // Retorna falso se invalido
        }
        return equipamentoDAO.atualizar(equipamento); // Repassa a regra para atualizacao no DAO
    }
}