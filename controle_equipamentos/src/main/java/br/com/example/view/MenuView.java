package br.com.example.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.GridLayout;
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
        
        while (true) { // Usa loop infinito, controlado pelo System.exit para fechar de vez
            // Monta o menu de botoes usando o JOptionPane
            int escolha = JOptionPane.showOptionDialog(null,
                    "Escolha uma operação:", "Controle de Laboratório",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opcoes, opcoes[0]);

            try {
                switch (escolha) {
                    case 0:
                        cadastrar(true); // Abre painel unico de cadastro de computador
                        break;
                    case 1:
                        cadastrar(false); // Abre painel unico de cadastro de projetor
                        break;
                    case 2:
                        listarEquipamentos(); // Chama a listagem
                        break;
                    case 3:
                        filtrarPorLaboratorio(); // Filtra dados do banco
                        break;
                    case 4:
                        buscarEquipamento(); // Busca um especifico
                        break;
                    case 5:
                        atualizarEquipamento(); // Abre painel unico para edicao
                        break;
                    case 6:
                        excluirEquipamento(); // Exclui com confirmacao
                        break;
                    case 7: // Clicou no botao Sair
                    case JOptionPane.CLOSED_OPTION: // Clicou no X vermelho da janela
                        int confirmacao = JOptionPane.showConfirmDialog(null, 
                            "Deseja realmente encerrar o sistema?", "Sair", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // Confirma a saida
                            
                        if (confirmacao == JOptionPane.YES_OPTION) { // Se clicar em Sim
                            JOptionPane.showMessageDialog(null, "Encerrando o sistema.");
                            System.exit(0); // Fecha o sistema definitivamente cortando o loop
                        }
                        break;
                }
            } catch (Exception e) { // Captura erros nao previstos nas telas
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } // Fim do while
    }

    private void cadastrar(boolean isComputador) {
        JTextField patrimonioField = new JTextField(); // Cria campo para patrimonio
        JTextField descricaoField = new JTextField(); // Cria campo para descricao
        JTextField laboratorioField = new JTextField(); // Cria campo para laboratorio
        
        String[] opcoesSituacao = { "Disponível", "Em uso", "Em manutenção" }; // Grafia exata exigida pelo banco
        JComboBox<String> situacaoBox = new JComboBox<>(opcoesSituacao); // Cria caixa dropdown para nao digitar errado
        
        JTextField extraField = new JTextField(); // Cria campo para RAM ou Luminosidade

        JPanel painelFormulario = new JPanel(new GridLayout(0, 1)); // Cria um painel com 1 coluna para agrupar tudo
        
        painelFormulario.add(new JLabel("Patrimônio (Apenas números, > 0):")); // Adiciona titulo
        painelFormulario.add(patrimonioField); // Adiciona campo no painel
        
        painelFormulario.add(new JLabel("Descrição (Obrigatório):")); // Adiciona titulo
        painelFormulario.add(descricaoField); // Adiciona campo no painel
        
        painelFormulario.add(new JLabel("Laboratório (Obrigatório):")); // Adiciona titulo
        painelFormulario.add(laboratorioField); // Adiciona campo no painel
        
        painelFormulario.add(new JLabel("Situação:")); // Adiciona titulo
        painelFormulario.add(situacaoBox); // Adiciona combo box no painel
        
        if (isComputador) { // Condicional para o tipo do equipamento
            painelFormulario.add(new JLabel("Memória RAM em GB (Apenas números, > 0):")); // Adiciona titulo RAM
        } else {
            painelFormulario.add(new JLabel("Luminosidade em Lumens (Apenas números, > 0):")); // Adiciona titulo Lumens
        }
        painelFormulario.add(extraField); // Adiciona o campo especifico

        // Exibe todos os campos numa unica janela
        int resultado = JOptionPane.showConfirmDialog(null, painelFormulario, 
                isComputador ? "Novo Cadastro: Computador" : "Novo Cadastro: Projetor", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) { // Se o usuario clicou em OK para submeter
            try {
                int patrimonio = Integer.parseInt(patrimonioField.getText().trim()); // Extrai o patrimonio digitado
                String descricao = descricaoField.getText().trim(); // Extrai a descricao
                String laboratorio = laboratorioField.getText().trim(); // Extrai o laboratorio
                String situacao = (String) situacaoBox.getSelectedItem(); // Extrai a situacao da caixa
                int valorExtra = Integer.parseInt(extraField.getText().trim()); // Extrai o valor RAM/Luminosidade

                // Valida regras numericas pedidas pelo professor
                if (patrimonio <= 0 || valorExtra <= 0) { 
                    JOptionPane.showMessageDialog(null, "Patrimônio e campo numérico devem ser maiores que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return; // Aborta
                }
                // Valida regra de campos vazios pedida pelo professor
                if (descricao.isEmpty() || laboratorio.isEmpty()) { 
                    JOptionPane.showMessageDialog(null, "Descrição e Laboratório não podem estar vazios!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return; // Aborta
                }

                Equipamento eq;
                if (isComputador) { // Condicional para instanciar correto
                    eq = new Computador(patrimonio, descricao, laboratorio, situacao, valorExtra); // Instancia a classe Computador
                } else {
                    eq = new Projetor(patrimonio, descricao, laboratorio, situacao, valorExtra); // Instancia a classe Projetor
                }

                if (controller.cadastrarEquipamento(eq)) { // Repassa para o controller salvar
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar. Verifique o banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) { // Se falhar na conversao de texto pra numero printa o erro
                JOptionPane.showMessageDialog(null, "Valores numéricos inválidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
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

        JOptionPane.showMessageDialog(null, sb.toString(), "Lista", JOptionPane.INFORMATION_MESSAGE); // Imprime a lista na tela
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
            int patrimonio = Integer.parseInt(JOptionPane.showInputDialog("Patrimônio do equipamento a atualizar:")); // Pede o patrimonio
            Equipamento eq = controller.buscarEquipamento(patrimonio); // Traz os dados atuais do banco

            if (eq == null) {
                JOptionPane.showMessageDialog(null, "Equipamento não encontrado.");
                return;
            }

            JTextField descricaoField = new JTextField(eq.getDescricao()); // Cria campo preenchido com a descricao atual
            JTextField laboratorioField = new JTextField(eq.getLaboratorio()); // Cria campo preenchido com o lab atual
            
            String[] opcoesSituacao = { "Disponível", "Em uso", "Em manutenção" }; // Grafia exata exigida pelo enum
            JComboBox<String> situacaoBox = new JComboBox<>(opcoesSituacao); // Cria caixa dropdown
            situacaoBox.setSelectedItem(eq.getSituacao()); // Marca na caixa a situacao que ja esta no banco
            
            JTextField extraField = new JTextField(); // Cria campo para RAM ou Luminosidade

            JPanel painelFormulario = new JPanel(new GridLayout(0, 1)); // Cria um painel agrupador
            
            painelFormulario.add(new JLabel("Nova Descrição:")); // Adiciona titulo
            painelFormulario.add(descricaoField); // Adiciona campo
            
            painelFormulario.add(new JLabel("Novo Laboratório:")); // Adiciona titulo
            painelFormulario.add(laboratorioField); // Adiciona campo
            
            painelFormulario.add(new JLabel("Nova Situação:")); // Adiciona titulo
            painelFormulario.add(situacaoBox); // Adiciona caixa dropdown

            if (eq instanceof Computador) { // Condicional para o tipo do equipamento
                extraField.setText(String.valueOf(((Computador) eq).getMemoriaRAM())); // Traz RAM atual
                painelFormulario.add(new JLabel("Nova RAM (GB):")); // Adiciona titulo RAM
            } else if (eq instanceof Projetor) {
                extraField.setText(String.valueOf(((Projetor) eq).getLuminosidade())); // Traz Lumen atual
                painelFormulario.add(new JLabel("Nova Luminosidade:")); // Adiciona titulo Lumens
            }
            painelFormulario.add(extraField); // Adiciona campo especifico

            // Exibe formulario de atualizacao numa unica janela
            int resultado = JOptionPane.showConfirmDialog(null, painelFormulario, "Atualizar Equipamento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) { // Se clicou em OK
                eq.setDescricao(descricaoField.getText().trim()); // Altera a descricao no objeto
                eq.setLaboratorio(laboratorioField.getText().trim()); // Altera o lab no objeto
                eq.setSituacao((String) situacaoBox.getSelectedItem()); // Altera situacao no objeto
                
                int valorExtra = Integer.parseInt(extraField.getText().trim()); // Extrai novo valor
                
                if (valorExtra <= 0) { // Valida valor numerico
                    JOptionPane.showMessageDialog(null, "O valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (eq.getDescricao().isEmpty() || eq.getLaboratorio().isEmpty()) { // Valida valor texto
                    JOptionPane.showMessageDialog(null, "Descrição e Laboratório não podem estar vazios!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (eq instanceof Computador) { // Condicional
                    ((Computador) eq).setMemoriaRAM(valorExtra); // Altera RAM
                } else if (eq instanceof Projetor) {
                    ((Projetor) eq).setLuminosidade(valorExtra); // Altera Lumens
                }

                if (controller.atualizarEquipamento(eq)) { // Manda pro controller salvar as alteracoes
                    JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar.");
                }
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
}