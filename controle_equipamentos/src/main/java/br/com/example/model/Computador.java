package br.com.example.model;

public class Computador extends Equipamento { // Herança: Computador é um Equipamento
    private int memoriaRAM; // Atributo exclusivo da subclasse Computador

    public Computador(int patrimonio, String descricao, String laboratorio, String situacao, int memoriaRAM) {
        super(patrimonio, descricao, laboratorio, situacao); // Executa o construtor da classe mae (Equipamento)
        this.memoriaRAM = memoriaRAM; // Atribui a quantidade de memoria RAM em GB
    }

    public int getMemoriaRAM() { return memoriaRAM; } // Retorna a memoria RAM
    public void setMemoriaRAM(int memoriaRAM) { this.memoriaRAM = memoriaRAM; } // Altera a memoria RAM
}
