package br.com.example.view;

import javax.swing.JOptionPane;
import java.util.ArrayList;

import br.com.example.controller.EquipamentoController;
import br.com.example.model.Computador;
import br.com.example.model.Equipamento;
import br.com.example.model.Projetor;

public class MenuView {

    private final EquipamentoController controller = new EquipamentoController(); // Instancia a camada de controle

    public void iniciar() {
        String[] opcoes = { "Cadastrar Computador", "Cadastrar Projetor", "Listar Todos", "Filtrar por Laboratório",
                "Buscar por Patrimônio", "Atualizar Equipamento", "Excluir Equipamento", "Sair" };
        int escolha;

        do {
            // Monta o menu de botoes usando o JOptionPane
            escolha = JOptionPane.showOptionDialog(null,
                    "Escolha uma operação:", "Controle de Laboratório",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opcoes, opcoes[0]);

            try {
                switch (escolha) {
                    case 0:
                        cadastrar(true);
                        break;
                    case 1:
                        cadastrar(false);
                        break;
                    case 2:
                        listarEquipamentos();
                        break;
                    case 3:
                        filtrarPorLaboratorio();
                        break; // Nova opção RF06
                    case 4:
                        buscarEquipamento();
                        break;
                    case 5:
                        atualizarEquipamento();
                        break;
                    case 6:
                        excluirEquipamento();
                        break; // Ajustado para exigir confirmação
                    case 7:
                    case JOptionPane.CLOSED_OPTION:
                        JOptionPane.showMessageDialog(null, "Encerrando o sistema.");
                        escolha = 7;
                        break;
                }
            } catch (Exception e) { // Captura erros nao previstos nas telas
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (escolha != 6); // Mantem em loop ate o usuario pedir pra sair
    }

    private void cadastrar(boolean isComputador) {
        try {
            int patrimonio = Integer.parseInt(JOptionPane.showInputDialog("Patrimônio:")); // Pede o patrimonio
            String descricao = JOptionPane.showInputDialog("Descrição:"); // Pede a descricao
            String laboratorio = JOptionPane.showInputDialog("Laboratório:"); // Pede o laboratorio
            String situacao = escolherSituacao(); // Abre janela de situacao

            if (situacao == null)
                return; // Aborta se o usuario fechar a janela

            Equipamento eq;
            if (isComputador) { // Condicional para o tipo do equipamento
                int ram = Integer.parseInt(JOptionPane.showInputDialog("Memória RAM (GB):"));
                eq = new Computador(patrimonio, descricao, laboratorio, situacao, ram); // Instancia a classe filha
            } else {
                int lum = Integer.parseInt(JOptionPane.showInputDialog("Luminosidade (Lumens):"));
                eq = new Projetor(patrimonio, descricao, laboratorio, situacao, lum); // Instancia a classe filha
            }

            if (controller.cadastrarEquipamento(eq)) { // Repassa para o controller
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar. Verifique o banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) { // Se falhar na conversao de texto pra numero printa o erro
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos.", "Erro de Entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarEquipamentos() {
        ArrayList<Equipamento> lista = controller.listarEquipamentos(); // Pede a lista ao controller
        if (lista.isEmpty()) { // Verifica se a lista esta vazia
            JOptionPane.showMessageDialog(null, "Nenhum equipamento encontrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("Equipamentos:\n\n");
        for (Equipamento eq : lista) { // Percorre cada objeto do resultado
            sb.append("Patrimônio: ").append(eq.getPatrimonio())
                    .append(" | ").append(eq.getDescricao())
                    .append(" (").append(eq.getSituacao()).append(")\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Lista", JOptionPane.INFORMATION_MESSAGE); // Imprime a lista
                                                                                                      // na tela
    }

    private void buscarEquipamento() {
        try {
            int patrimonio = Integer.parseInt(JOptionPane.showInputDialog("Informe o Patrimônio:"));
            Equipamento eq = controller.buscarEquipamento(patrimonio); // Pede a busca ao controller

            if (eq != null) { // Verifica se o banco retornou algo
                String detalhes = "Tipo: " + (eq instanceof Computador ? "Computador" : "Projetor") + "\n"
                        + "Patrimônio: " + eq.getPatrimonio() + "\n"
                        + "Descrição: " + eq.getDescricao() + "\n"
                        + "Laboratório: " + eq.getLaboratorio() + "\n"
                        + "Situação: " + eq.getSituacao() + "\n";

                if (eq instanceof Computador)
                    detalhes += "RAM: " + ((Computador) eq).getMemoriaRAM() + "GB";
                else
                    detalhes += "Luminosidade: " + ((Projetor) eq).getLuminosidade() + " Lumens";

                JOptionPane.showMessageDialog(null, detalhes, "Detalhes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Equipamento não encontrado.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Patrimônio inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarPorLaboratorio() {
        String lab = JOptionPane.showInputDialog("Informe o nome do laboratório:");
        if (lab == null || lab.trim().isEmpty())
            return;

        ArrayList<Equipamento> lista = controller.listarPorLaboratorio(lab);
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum equipamento encontrado no laboratório: " + lab);
            return;
        }

        StringBuilder sb = new StringBuilder("Equipamentos no laboratório " + lab + ":\n\n");
        for (Equipamento eq : lista) {
            sb.append("Patrimônio: ").append(eq.getPatrimonio())
                    .append(" | ").append(eq.getDescricao())
                    .append(" (").append(eq.getSituacao()).append(")\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Filtro por Laboratório", JOptionPane.INFORMATION_MESSAGE);
    }

    private void atualizarEquipamento() {
        try {
            int patrimonio = Integer.parseInt(JOptionPane.showInputDialog("Patrimônio do equipamento a atualizar:"));
            Equipamento eq = controller.buscarEquipamento(patrimonio); // Traz os dados atuais do banco

            if (eq == null) {
                JOptionPane.showMessageDialog(null, "Equipamento não encontrado.");
                return;
            }

            // Mostra os valores antigos e pede os novos
            eq.setDescricao(JOptionPane.showInputDialog("Nova Descrição:", eq.getDescricao()));
            eq.setLaboratorio(JOptionPane.showInputDialog("Novo Laboratório:", eq.getLaboratorio()));
            eq.setSituacao(escolherSituacao());

            if (eq instanceof Computador) { // Condicional para o tipo do equipamento
                String ram = JOptionPane.showInputDialog("Nova RAM (GB):", ((Computador) eq).getMemoriaRAM());
                ((Computador) eq).setMemoriaRAM(Integer.parseInt(ram));
            } else if (eq instanceof Projetor) {
                String lum = JOptionPane.showInputDialog("Nova Luminosidade:", ((Projetor) eq).getLuminosidade());
                ((Projetor) eq).setLuminosidade(Integer.parseInt(lum));
            }

            if (controller.atualizarEquipamento(eq)) { // Manda pro controller salvar as alteracoes
                JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirEquipamento() {
        try {
            int patrimonio = Integer.parseInt(JOptionPane.showInputDialog("Patrimônio para excluir:"));
            Equipamento eq = controller.buscarEquipamento(patrimonio);

            if (eq == null) {
                JOptionPane.showMessageDialog(null, "Equipamento não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(null,
                    "Tem certeza que deseja excluir o patrimônio " + patrimonio + "?\n" + eq.getDescricao(),
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmacao == JOptionPane.YES_OPTION) {
                if (controller.excluirEquipamento(patrimonio)) {
                    JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Falha na exclusão.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Patrimônio inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String escolherSituacao() {
        String[] opcoes = { "Disponível", "Em uso", "Em manutenção" }; // Grafia exata exigida pelo enum do banco
        int escolha = JOptionPane.showOptionDialog(null,
                "Selecione a situação:", "Situação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        return (escolha >= 0) ? opcoes[escolha] : null; // Retorna a string escolhida
    }
}