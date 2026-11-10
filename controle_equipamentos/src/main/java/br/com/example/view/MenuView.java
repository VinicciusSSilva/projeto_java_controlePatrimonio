package br.com.example.view;

import java.util.ArrayList;
import java.util.Scanner;

import br.com.example.DAO.EquipamentoDAO;
import br.com.example.model.Computador;
import br.com.example.model.Equipamento;
import br.com.example.model.Projetor;

public class MenuView {
    private final Scanner scanner = new Scanner(System.in);
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarComputador();
                        break;
                    case 2:
                        cadastrarProjetor();
                        break;
                    case 3:
                        listarEquipamentos();
                        break;
                    case 4:
                        excluirEquipamento();
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.err.println("Não foi possível concluir a operação: " + e.getMessage());
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n=== Controle de Equipamentos ===");
        System.out.println("1. Cadastrar computador");
        System.out.println("2. Cadastrar projetor");
        System.out.println("3. Listar equipamentos");
        System.out.println("4. Excluir equipamento");
        System.out.println("0. Sair");
    }

    private void cadastrarComputador() {
        int patrimonio = lerInteiroPositivo("Patrimônio: ");
        String descricao = lerTexto("Descrição: ", 500);
        String laboratorio = lerTexto("Laboratório: ", 45);
        String situacao = lerSituacao();
        int memoriaRAM = lerInteiroPositivo("Memória RAM (GB): ");

        Computador computador = new Computador(patrimonio, descricao, laboratorio, situacao, memoriaRAM);
        if (equipamentoDAO.cadastrar(computador)) {
            System.out.println("Computador cadastrado com sucesso.");
        } else {
            System.out.println("Não foi possível cadastrar o computador. Verifique os dados e o banco de dados.");
        }
    }

    private void cadastrarProjetor() {
        int patrimonio = lerInteiroPositivo("Patrimônio: ");
        String descricao = lerTexto("Descrição: ", 500);
        String laboratorio = lerTexto("Laboratório: ", 45);
        String situacao = lerSituacao();
        int luminosidade = lerInteiroPositivo("Luminosidade: ");

        Projetor projetor = new Projetor(patrimonio, descricao, laboratorio, situacao, luminosidade);
        if (equipamentoDAO.cadastrar(projetor)) {
            System.out.println("Projetor cadastrado com sucesso.");
        } else {
            System.out.println("Não foi possível cadastrar o projetor. Verifique os dados e o banco de dados.");
        }
    }

    private void listarEquipamentos() {
        ArrayList<Equipamento> equipamentos = equipamentoDAO.listar();
        if (equipamentos.isEmpty()) {
            System.out.println("Nenhum equipamento encontrado.");
            return;
        }

        System.out.println("\n=== Equipamentos cadastrados ===");
        for (Equipamento equipamento : equipamentos) {
            System.out.println("------------------------------");
            System.out.println("Tipo: " + (equipamento instanceof Computador ? "Computador" : "Projetor"));
            System.out.println("Patrimônio: " + equipamento.getPatrimonio());
            System.out.println("Descrição: " + equipamento.getDescricao());
            System.out.println("Laboratório: " + equipamento.getLaboratorio());
            System.out.println("Situação: " + equipamento.getSituacao());

            if (equipamento instanceof Computador) {
                System.out.println("Memória RAM: " + ((Computador) equipamento).getMemoriaRAM() + " GB");
            } else if (equipamento instanceof Projetor) {
                System.out.println("Luminosidade: " + ((Projetor) equipamento).getLuminosidade());
            }
        }
        System.out.println("------------------------------");
    }

    private void excluirEquipamento() {
        int patrimonio = lerInteiroPositivo("Patrimônio do equipamento a excluir: ");
        if (equipamentoDAO.excluir(patrimonio)) {
            System.out.println("Equipamento excluído com sucesso.");
        } else {
            System.out.println("Nenhum equipamento foi excluído. Confira o patrimônio informado.");
        }
    }

    private String lerSituacao() {
        while (true) {
            System.out.println("Situação:");
            System.out.println("1. Disponível");
            System.out.println("2. Em Uso");
            System.out.println("3. Em Manutenção");
            int opcao = lerInteiro("Escolha a situação: ");

            switch (opcao) {
                case 1:
                    return "Disponível";
                case 2:
                    return "Em Uso";
                case 3:
                    return "Em Manutenção";
                default:
                    System.out.println("Opção inválida. Escolha 1, 2 ou 3.");
            }
        }
    }

    private int lerInteiroPositivo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("Informe um número maior que zero.");
        }
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private String lerTexto(String mensagem, int tamanhoMaximo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty() && entrada.length() <= tamanhoMaximo) {
                return entrada;
            }
            System.out.println("O texto é obrigatório e deve ter no máximo " + tamanhoMaximo + " caracteres.");
        }
    }
}
