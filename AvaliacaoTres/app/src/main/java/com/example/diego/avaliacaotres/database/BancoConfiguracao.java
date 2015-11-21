package com.example.diego.avaliacaotres.database;


import com.example.diego.avaliacaotres.model.ItemListaCompras;

/**
 * Created by wbertan on 12/04/15.
 */
public class BancoConfiguracao {
    /*
        Classe para armazenar as configurações do Banco, como nome, versão, tabelas...
    */
    public static final String  BANCO_NOME   = "bancoExemplo";
    public static final int     BANCO_VERSAO = 1;
    public static final Class[] TABELAS      = new Class[]{ItemListaCompras.class};
}
