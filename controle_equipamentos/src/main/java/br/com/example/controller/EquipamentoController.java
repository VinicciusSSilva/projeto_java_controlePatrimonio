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
        // Validações básicas antes de mandar para o DAO (opcional, conforme necessidade)
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
}