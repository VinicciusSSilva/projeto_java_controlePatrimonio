package br.com.example.model;

public class Equipamento {
    private int patrimonio;
    private String descricao;
    private String laboratorio;
    private String situacao;

    public Equipamento() {
    }

    // Constructor
    public Equipamento(int patrimonio, String descricao, String laboratorio, String situacao) {
        this.patrimonio = patrimonio;
        this.descricao = descricao;
        this.laboratorio = laboratorio;
        this.situacao = situacao;
    }

    // Getters and Setters
    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
