package br.com.example.model;


public class Equipamento {
    
    // Atributos privados encapsulados da classe
    private int patrimonio;
    private String descricao;
    private String laboratorio;
    private String situacao;

    // Construtor vazio (permite instanciar o objeto sem passar valores iniciais)
    public Equipamento() {}

    // Construtor cheio (inicializa o objeto já com todos os atributos preenchidos)
    public Equipamento(int patrimonio, String descricao, String laboratorio, String situacao) {
        this.patrimonio = patrimonio;
        this.descricao = descricao;
        this.laboratorio = laboratorio;
        this.situacao = situacao;
    }

    // Métodos Getter e Setter para acessar e modificar o patrimônio
    public int getPatrimonio() { return patrimonio; }
    public void setPatrimonio(int patrimonio) { this.patrimonio = patrimonio; }

    // Métodos Getter e Setter para acessar e modificar a descrição
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // Métodos Getter e Setter para acessar e modificar o laboratório
    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    // Métodos Getter e Setter para acessar e modificar a situação
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}