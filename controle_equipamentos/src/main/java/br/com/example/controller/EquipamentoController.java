package br.com.example.controller;

import java.util.ArrayList;

import br.com.example.dao.EquipamentoDAO;
import br.com.example.model.Equipamento; // Importa a classe modelo

public class EquipamentoController {
    private EquipamentoDAO equipamentoDAO; // Declara a referencia do DAO

    public EquipamentoController() {
        this.equipamentoDAO = new EquipamentoDAO(); // Instancia a camada de persistencia
    }

    public boolean cadastrarEquipamento(Equipamento equipamento) {
        if (equipamento == null) { // Valida se o objeto recebido e valido
            System.err.println("Erro: O equipamento não pode ser nulo.");
            return false; // Retorna falso se objeto for nulo
        }
        return equipamentoDAO.cadastrar(equipamento); // Repassa a regra para insercao no DAO
    }

    public ArrayList<Equipamento> listarEquipamentos() {
        return equipamentoDAO.listar(); // Solicita a lista de equipamentos cadastrados ao DAO
    }

    public boolean excluirEquipamento(int patrimonio) {
        if (patrimonio <= 0) { // Valida se o patrimonio e positivo
            System.err.println("Erro: Patrimônio inválido para exclusão.");
            return false; // Retorna falso para patrimonio invalido
        }
        return equipamentoDAO.excluir(patrimonio); // Solicita a exclusao ao DAO
    }
}
