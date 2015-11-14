package net.unesc.diego.avaliacaodois.webservice.cependereco;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


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

    public static CepEndereco getCepEndereco(String cep) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://api.postmon.com.br/v1/cep/" + cep);
        String text = null;
        try {

            HttpResponse response = httpClient.execute(httpGet, localContext);

            HttpEntity entity = response.getEntity();

            text = getASCIIContentFromEntity(entity);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(text, CepEndereco.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {

        InputStream in = entity.getContent();

        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];

            n =  in.read(b);

            if (n>0) out.append(new String(b, 0, n));

        }

        return out.toString();

    }


}
