package br.com.example.model;


public class Computador extends Equipamento {
    
    // Atributo específico da classe Computador
    private int memoriaRam;

    // Construtor que recebe os dados da mãe e o atributo próprio da filha
    public Computador(int patrimonio, String descricao, String laboratorio, String situacao, int memoriaRam) {
        // 'super' chama o construtor da classe mãe (Equipamento) para preencher os dados comuns
        super(patrimonio, descricao, laboratorio, situacao);
        this.memoriaRam = memoriaRam; // Inicializa o atributo exclusivo
    }

    // Métodos Getter e Setter para a memória RAM
    public int getMemoriaRAM() { return memoriaRam; }
    public void setMemoriaRAM(int memoriaRam) { this.memoriaRam = memoriaRam; }
}