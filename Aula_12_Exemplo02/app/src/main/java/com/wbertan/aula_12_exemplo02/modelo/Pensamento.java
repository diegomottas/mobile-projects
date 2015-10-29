package com.wbertan.aula_12_exemplo02.modelo;

/**
 * Created by wbertan on 19/05/15.
 */
public class Pensamento {
    private String nm_pensador;
    private String ds_pensamento;
    private int    id_foto_pensador;

    public Pensamento(){}

    public Pensamento(String nm_pensador, int id_foto_pensador, String ds_pensamento){
        setNm_pensador(nm_pensador);
        setId_foto_pensador(id_foto_pensador);
        setDs_pensamento(ds_pensamento);
    }

    public String getNm_pensador() {
        return nm_pensador;
    }

    public void setNm_pensador(String nm_pensador) {
        this.nm_pensador = nm_pensador;
    }

    public String getDs_pensamento() {
        return ds_pensamento;
    }

    public void setDs_pensamento(String ds_pensamento) {
        this.ds_pensamento = ds_pensamento;
    }

    public int getId_foto_pensador() {
        return id_foto_pensador;
    }

    public void setId_foto_pensador(int id_foto_pensador) {
        this.id_foto_pensador = id_foto_pensador;
    }
}
