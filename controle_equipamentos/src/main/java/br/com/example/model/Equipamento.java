package br.com.example.model;

public class Equipamento {
    private int patrimonio; // Identificador unico (chave primaria no banco)
    private String descricao; // Texto descritivo do equipamento
    private String laboratorio; // Nome ou codigo do laboratorio onde se encontra
    private String situacao; // Estado atual: Disponível, Em uso ou Em manutenção

    public Equipamento() {} // Construtor padrao sem argumentos

    public Equipamento(int patrimonio, String descricao, String laboratorio, String situacao) { // Construtor completo da classe mae
        this.patrimonio = patrimonio; // Atribui o patrimonio
        this.descricao = descricao; // Atribui a descricao
        this.laboratorio = laboratorio; // Atribui o laboratorio
        this.situacao = situacao; // Atribui a situacao
    }

    public int getPatrimonio() { return patrimonio; } // Retorna o patrimonio
    public void setPatrimonio(int patrimonio) { this.patrimonio = patrimonio; } // Altera o patrimonio

    public String getDescricao() { return descricao; } // Retorna a descricao
    public void setDescricao(String descricao) { this.descricao = descricao; } // Altera a descricao

    public String getLaboratorio() { return laboratorio; } // Retorna o laboratorio
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; } // Altera o laboratorio

    public String getSituacao() { return situacao; } // Retorna a situacao
    public void setSituacao(String situacao) { this.situacao = situacao; } // Altera a situacao
}


