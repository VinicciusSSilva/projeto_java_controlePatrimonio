package br.com.example.model;

public class Projetor extends Equipamento { // Herança: Projetor é um Equipamento
    private int luminosidade; // Atributo exclusivo da subclasse Projetor (em Lumens)

    public Projetor(int patrimonio, String descricao, String laboratorio, String situacao, int luminosidade) {
        super(patrimonio, descricao, laboratorio, situacao); // Executa o construtor da classe mae (Equipamento)
        this.luminosidade = luminosidade; // Atribui o valor de luminosidade
    }

    public int getLuminosidade() { return luminosidade; } // Retorna a luminosidade
    public void setLuminosidade(int luminosidade) { this.luminosidade = luminosidade; } // Altera a luminosidade
}
