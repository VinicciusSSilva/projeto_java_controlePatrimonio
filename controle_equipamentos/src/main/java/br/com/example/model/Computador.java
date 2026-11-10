package br.com.example.model;

public class Computador extends Equipamento {
     //garante o encapsulamento, impedindo o acesso direto de fora da classe
    private int memoriaRAM;

    public Computador() {
        super();// Chama o construtor sem parâmetros da superclasse
    }

    public Computador(int patrimonio, String descricao, String laboratorio, String situacao, int memoriaRAM) {
        super(patrimonio, descricao, laboratorio, situacao);
        this.memoriaRAM = memoriaRAM;
    }

    public int getMemoriaRAM() {
        return memoriaRAM;
    }

    public void setMemoriaRAM(int memoriaRAM) {
        this.memoriaRAM = memoriaRAM;
    }
}
