package com.wbertan.aula_12_exemplo01.modelo;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by wbertan on 18/05/15.
 */
public class Clima {
    private String pais;
    private Double temperatura;
    private Date   hora_amanhecer;
    private Date   hora_anoitecer;

    public Clima(){}

    //Extrai as informações do JSON para o objeto
    public Clima(String json){
        try {
            //Lê o Objeto começando pela primeira chave
            JSONObject jsonReader = new JSONObject(json);

            //Recupera o Objeto da chave 'sys'
            JSONObject sys  = jsonReader.getJSONObject("sys");
            //Recupera os valores conforme as chaves
            pais = sys.getString("country");
            hora_amanhecer = new Date(sys.getLong("sunrise") * 1000);
            hora_anoitecer = new Date(sys.getLong("sunset") * 1000);

            //Recupera o Objeto da chave 'main'
            JSONObject main  = jsonReader.getJSONObject("main");
            //Recupera os valores conforme as chaves
            temperatura = main.getDouble("temp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Date getHora_amanhecer() {
        return hora_amanhecer;
    }

    public void setHora_amanhecer(Date hora_amanhecer) {
        this.hora_amanhecer = hora_amanhecer;
    }

    public Date getHora_anoitecer() {
        return hora_anoitecer;
    }

    public void setHora_anoitecer(Date hora_anoitecer) {
        this.hora_anoitecer = hora_anoitecer;
    }
}
