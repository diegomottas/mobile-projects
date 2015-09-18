package net.unesc.diego.exerciciocomponentes.Modelo;

import android.content.ContentValues;
import android.database.Cursor;

import com.wbertan.aula_07_exemplo01.dados.Banco;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wbertan on 12/04/15.
 */
public class Livro {

    public static final String   TABELA  = "livro";
    public static final String[] COLUNAS = new String[]{"cd_livro", "ds_livro", "cd_autor", "dt_manutencao"};
    public static final String[] PKEY    = new String[]{"cd_livro"};

    public static final String   SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABELA
                                            + "( cd_livro      INTEGER PRIMARY KEY AUTOINCREMENT, "
                                            + "  ds_livro      TEXT    NOT NULL, "
                                            + "  cd_autor      INTEGER NOT NULL, "
                                            + "  dt_manutencao LONG);";

    private Integer cdLivro;
    private String  dsLivro;
    private Integer cdAutor;
    private Date    dtManutencao;

    public Livro(){}

    public Integer getCdLivro() {
        return cdLivro;
    }

    public void setCdLivro(Integer cdLivro) {
        this.cdLivro = cdLivro;
    }

    public String getDsLivro() {
        return dsLivro;
    }

    public void setDsLivro(String dsLivro) {
        this.dsLivro = dsLivro;
    }

    public Integer getCdAutor() {
        return cdAutor;
    }

    public void setCdAutor(Integer cdAutor) {
        this.cdAutor = cdAutor;
    }

    public Date getDtManutencao() {
        return dtManutencao;
    }

    public void setDtManutencao(Date dtManutencao) {
        this.dtManutencao = dtManutencao;
    }

    @Override
    public String toString() {
        return "("+getCdLivro()+") - "+getDsLivro();
    }

    //Métodos de Repositório

    /*
        Método de inserir um Objeto no banco
     */
    public static long insert(Banco banco, Livro livro){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("cd_livro",      livro.getCdLivro());
        contentValues.put("ds_livro",      livro.getDsLivro());
        contentValues.put("cd_autor",      livro.getCdAutor());
        contentValues.put("dt_manutencao", (livro.getDtManutencao() == null ? System.currentTimeMillis() : livro.getDtManutencao().getTime()));

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insert(TABELA, contentValues);
    }

    /*
        Método de inserir ou atualizar um Objeto no banco
     */
    public static long insertOrUpdate(Banco banco, Livro livro){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("cd_livro",      livro.getCdLivro());
        contentValues.put("ds_livro",      livro.getDsLivro());
        contentValues.put("cd_autor",      livro.getCdAutor());
        contentValues.put("dt_manutencao", (livro.getDtManutencao() == null ? System.currentTimeMillis() : livro.getDtManutencao().getTime()));

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insertOrUpdate(TABELA, contentValues);
    }

    /*
        Método de remover um Objeto no banco
     */
    public static long delete(Banco banco, Livro livro){
        //Chama o método de Delete no banco passando a tabela e os valores
        return banco.delete(TABELA, "cd_livro = ?", new String[]{livro.getCdLivro().toString()});
    }

    /*
        Método para recuperar uma Lista de Objetos do Banco
     */
    public static List<Livro> getList(Banco banco){
        //Solicita ao banco a query de recuperar todos os objetos
        Cursor cursor = banco.query(TABELA);

        //Cria uma lista de objetos que será retornada
        List<Livro> lista = new ArrayList<>();

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            do{
                //Cria o objeto
                Livro objeto = new Livro();
                //Seta os valores
                objeto.setCdLivro(cursor.getInt(cursor.getColumnIndex("cd_livro")));
                objeto.setDsLivro(cursor.getString(cursor.getColumnIndex("ds_livro")));
                objeto.setCdAutor(cursor.getInt(cursor.getColumnIndex("cd_autor")));
                objeto.setDtManutencao(new Date(cursor.getLong(cursor.getColumnIndex("dt_manutencao"))));

                //Adiciona na lista de retorno
                lista.add(objeto);
            } while(cursor.moveToNext()); //Pega o próximo objeto do cursor
        }
        return lista;
    }

    /*
        Método para recuperar um Objeto do Banco
     */
    public static Livro get(Banco banco, String where, String[] whereArgs){
        //Solicita ao banco a query de recuperar o objeto
        Cursor cursor = banco.query(TABELA, where, whereArgs, null);

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            //Cria o objeto
            Livro objeto = new Livro();
            //Seta os valores
            objeto.setCdLivro(cursor.getInt(cursor.getColumnIndex("cd_livro")));
            objeto.setDsLivro(cursor.getString(cursor.getColumnIndex("ds_livro")));
            objeto.setCdAutor(cursor.getInt(cursor.getColumnIndex("cd_autor")));
            objeto.setDtManutencao(new Date(cursor.getLong(cursor.getColumnIndex("dt_manutencao"))));

            return objeto;
        }
        return null;
    }
}
