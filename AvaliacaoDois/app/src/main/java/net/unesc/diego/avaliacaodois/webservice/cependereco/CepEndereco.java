package net.unesc.diego.avaliacaodois.webservice.cependereco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author diego
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CepEndereco implements Serializable {

    private String cep;
    private String tipoDeLogradouro;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private CidadeCepEndereco cidade_info;
    private String complemento;
    private String endereco;
    private String numeroLogradouro;
    private String codigo_ibge;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDeLogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipoDeLogradouro(String tipoDeLogradouro) {
        this.tipoDeLogradouro = tipoDeLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public CidadeCepEndereco getCidade_info() {
        return cidade_info;
    }

    public void setCidade_info(CidadeCepEndereco cidade_info) {
        this.cidade_info = cidade_info;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumeroLogradouro() {
        return numeroLogradouro;
    }

    public void setNumeroLogradouro(String numeroLogradouro) {
        this.numeroLogradouro = numeroLogradouro;
    }

    public static CepEndereco getCepEndereco(String cep) throws ProcessingException, NotFoundException {
        ResteasyClient client = (ResteasyClient) new ResteasyClientBuilder()
                .establishConnectionTimeout(10, TimeUnit.SECONDS)
                .socketTimeout(10, TimeUnit.SECONDS)
                .build();
        WebTarget target = client.target("http://api.postmon.com.br/v1/cep/" + cep);
        CepEndereco response = target.request(MediaType.APPLICATION_JSON_TYPE).get(CepEndereco.class);
        return response;
    }

}
