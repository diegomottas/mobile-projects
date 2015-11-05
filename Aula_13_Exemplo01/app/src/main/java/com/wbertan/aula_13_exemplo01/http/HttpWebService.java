package com.wbertan.aula_13_exemplo01.http;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created by wbertan on 26/05/15.
 */
//Classe responsável por chamar WebService no Android
public class HttpWebService {
    //Variável volatile diz que o seu valor pode ser modificado por diferentes Threads
    // - Todas as leituras da variável nunca ocorrerá por cache de thread, sempre direto da memória principal;
    private volatile boolean fetchingUrl = true;
    //Variável que guardará o valor retornado do WebService
    private String data = null;

    //Construtor para guardar o contexto
    public HttpWebService(){}

    //Método estático chamado passando a URL, método e os parametros para o WebService
    public static String call(String nameSpaceWS, String urlWS, String metodoWS, String...params){
        //Cria um objeto desta classe
        HttpWebService httpWebService = new HttpWebService();

        //Chama o método fetchWS com os parametros, este método é que irá acessar o WebService
        httpWebService.fetchWS(nameSpaceWS, urlWS, metodoWS, params);
        //Fica aguardando que a Thread de requisição acabe
        while(httpWebService.fetchingUrl);

        //Retorna o valor recebido do WebService
        return httpWebService.getData();
    }

    //Método que irá lançar uma nova Thread para requisitar o WebService passando os parametros
    public void fetchWS(final String nameSpaceWS, final String urlWS, final String metodoWS, final String[] params) {
        //Cria uma nova Thread, pois não é boa prática requisitar recursos externos na Thread Principal
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
            try {
                //Cria um objeto SoapObject do kSoap2 passando o namespace do WS e o método que se quer acessar
                SoapObject request = new SoapObject(nameSpaceWS, metodoWS);

                //Chama o método que irá inserir na requisição os parametros
                setParams(request, params);

                //Cria o envelope SOAP da requisição
                SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

                //Cria o HttpTransport que irá chamar o WS com o método e o envelope gerado
                HttpTransportSE httpTransportSE = getHttpTransportSE(urlWS);
                httpTransportSE.call(urlWS + "/" + metodoWS, envelope);

                //Recupera a resposta do WS
                SoapPrimitive resultsString = (SoapPrimitive)envelope.getResponse();
                data = resultsString.toString();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                data = null;
            } catch (IOException e) {
                e.printStackTrace();
                data = null;
            } finally {
                //Informa a ThreadPrincipal que finalizou a requisição e que o retorno está na variável "data"
                fetchingUrl = false;
            }
            }
        });
        //Inicia a Thread
        thread.start();
    }

    //Cria o HttpTransport e configura ele com o timeout (60000 = 60s), e a URL do WebService
    private final HttpTransportSE getHttpTransportSE(String urlWS) {
        HttpTransportSE httpTransportSE = new HttpTransportSE(Proxy.NO_PROXY, urlWS, 60000);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return httpTransportSE;
    }

    //Gera e configura o envelope SOAP
    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Informa se o WebService é .NET
        envelope.dotNet = false;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    //Método que seta na conexão os parametros
    private void setParams(SoapObject request, String[] params) throws IOException{
        //Verifica se realmente foi passado parametros, caso contrario não faz nada
        if(params.length == 0){
            return;
        }
        //Verifica se foi passado quantidade de valores que satisfazem o requisito 'chave'='valor'
        // - Se a quantidade for ímpar está faltando ou uma chave ou um valor
        if(params.length % 2 == 0){
            //Contador que irá passar as iterações
            int contador = 0;
            //Executa enquanto não alcançar o final dos parametros
            while(contador < params.length){
                //Adiciona as propriedades da requisição no estilo chave/valor
                request.addProperty(params[contador++], params[contador++]);
            }
        } else {
            //Caso não seja quantidade par, lança uma exceção.
            throw new IOException("Número errado de parâmetros.");
        }
    }

    public String getData(){
        return data;
    }
}
