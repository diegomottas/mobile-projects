package net.unesc.diego.avaliacaodois.webservice.cependereco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 *
 * @author diego
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CidadeCepEndereco implements Serializable {

    private String codigo_ibge;

    public String getCodigo_ibge() {
        return codigo_ibge;
    }

    public void setCodigo_ibge(String codigo_ibge) {
        this.codigo_ibge = codigo_ibge;
    }

}
