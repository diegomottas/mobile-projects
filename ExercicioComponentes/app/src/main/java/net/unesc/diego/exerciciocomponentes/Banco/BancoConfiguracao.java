package net.unesc.diego.exerciciocomponentes.Banco;


import net.unesc.diego.exerciciocomponentes.Modelo.Album;
import net.unesc.diego.exerciciocomponentes.Modelo.ListaCompras;
import net.unesc.diego.exerciciocomponentes.Modelo.UsuarioCadastro;

/**
 * Created by wbertan on 12/04/15.
 */
public class BancoConfiguracao {
    /*
        Classe para armazenar as configurações do Banco, como nome, versão, tabelas...
    */
    public static final String  BANCO_NOME   = "bancoExemplo";
    public static final int     BANCO_VERSAO = 1;
    public static final Class[] TABELAS      = new Class[]{UsuarioCadastro.class, Album.class, ListaCompras.class};
}
