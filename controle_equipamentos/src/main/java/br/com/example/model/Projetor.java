package br.com.example.model;


public class Projetor extends Equipamento {
    
    // Atributo específico da classe Projetor
    private int luminosidade;

    // Construtor que repassa os dados para a classe mãe e define o próprio atributo
    public Projetor(int patrimonio, String descricao, String laboratorio, String situacao, int luminosidade) {
        super(patrimonio, descricao, laboratorio, situacao); // Repassa para Equipamento
        this.luminosidade = luminosidade; // Atribui a luminosidade do projetor
    }

    // Métodos Getter e Setter para a luminosidade
    public int getLuminosidade() { return luminosidade; }
    public void setLuminosidade(int luminosidade) { this.luminosidade = luminosidade; }
}