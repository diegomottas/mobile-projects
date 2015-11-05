package com.wbertan.aula_13_exemplo01.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by wbertan on 18/05/15.
 */
//Classe responsável por chamar Servlet no Android
public class HttpServlet {
    //Variável volatile diz que o seu valor pode ser modificado por diferentes Threads
    // - Todas as leituras da variável nunca ocorrerá por cache de thread, sempre direto da memória principal;
    private volatile boolean fetchingUrl = true;
    //Variável que guardará o valor retornado do Servlet
    private String data = null;

    //Método estático chamado passando a URL e os parametros para o Servlet
    public static String call(String urlServlet, String...params){
        //Cria um objeto desta classe
        HttpServlet httpServlet = new HttpServlet();

        //Chama o método fetchUrl com os parametros, este método é que irá acessar o Servlet
        httpServlet.fetchUrl(urlServlet, params);
        //Fica aguardando que a Thread de requisição acabe
        while(httpServlet.fetchingUrl);

        //Retorna o valor recebido do Servlet
        return httpServlet.getData();
    }

    //Método que irá lançar uma nova Thread para requisitar o Servlet passando os parametros
    public void fetchUrl(final String urlServlet, final String[] params){
        //Cria uma nova Thread, pois não é boa prática requisitar recursos externos na Thread Principal
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
            try {
                //Recupera uma URL a partir da String que foi passada
                URL url = new URL(urlServlet);
                //Abri uma conexão com a URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //Seta os Timeout de Leitura da Resposta e de Conexão
                conn.setReadTimeout(10000); //milliseconds
                conn.setConnectTimeout(15000); //milliseconds
                //Seta o método da requisição (GET ou POST)
                conn.setRequestMethod("POST");
                //Informa que irá receber/enviar algo
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //Chama o método que irá inserir na requisição os parametros
                setParams(conn, params);

                //Faz a requisição
                conn.connect();
                //Recupera o InputStream do Retorno
                InputStream stream = conn.getInputStream();
                //Converte o retorno para uma String
                data = convertStreamToString(stream);
                //Fecha o Stream
                stream.close();
            } catch (Exception e) {
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

    //Método para converter o Stream de retorno em uma String
    public String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String getData(){
        return data;
    }

    //Método que seta na conexão os parametros do POST
    private void setParams(HttpURLConnection conn, String[] params) throws IOException {
        //Verifica se realmente foi passado parametros, caso contrario não faz nada
        if(params.length == 0){
            return;
        }
        //Verifica se foi passado quantidade de valores que satisfazem o requisito 'chave'='valor'
        // - Se a quantidade for ímpar está faltando ou uma chave ou um valor
        if(params.length % 2 == 0){
            //Cria um StringBuilder para gerar a String que será injetada no POST
            StringBuilder stringParam = new StringBuilder();
            //Apenas para verificar se é o primeiro parametro ou não
            boolean verifica_primeiro = true;
            //Contador que irá passar as iterações
            int contador = 0;
            //Executa enquanto não alcançar o final dos parametros
            while(contador < params.length){
                //Verifica se é o primeiro parametro, se não for adiciona um '&'
                if (verifica_primeiro) {
                    verifica_primeiro = false;
                } else {
                    stringParam.append("&");
                }
                //Concatena a 'chave' com '=' com 'valor'
                stringParam.append(URLEncoder.encode(params[contador++], "UTF-8"));
                stringParam.append("=");
                stringParam.append(URLEncoder.encode(params[contador++], "UTF-8"));
            }
            //Pega o Stream de escrita da requisição para adicionar a String com os parametros na requisicao
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //Escreve os parametros
            writer.write(stringParam.toString());
            //Limpa e fecha o Writer e o Stream
            writer.flush();
            writer.close();
            os.close();
        } else {
            //Caso não seja quantidade par, lança uma exceção.
            throw new IOException("Número errado de parâmetros.");
        }
    }
}
