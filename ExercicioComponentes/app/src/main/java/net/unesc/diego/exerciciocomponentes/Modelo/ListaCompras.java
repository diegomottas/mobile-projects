package net.unesc.diego.exerciciocomponentes.Modelo;

import android.content.ContentValues;
import android.database.Cursor;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;

import java.util.ArrayList;
import java.util.List;

public class ListaCompras {

    public static final String   TABELA  = "lista_compras";
    public static final String[] COLUNAS = new String[]{"_id", "descricao"};
    public static final String[] PKEY    = new String[]{"_id"};

    public static final String   SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABELA +
            "( _id      INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "  descricao        TEXT);";

    private Integer codigo;
    private String descricao;

    public ListaCompras(){

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    //Métodos de Repositório

    /*
        Método de inserir um Objeto no banco
     */
    public static long insert(Banco banco, ListaCompras listaCompras){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", listaCompras.getDescricao());

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insert(TABELA, contentValues);
    }

    /*
        Método de inserir ou atualizar um Objeto no banco
     */
    public static long insertOrUpdate(Banco banco, ListaCompras listaCompras){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", listaCompras.getDescricao());

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insertOrUpdate(TABELA, contentValues);
    }

    /*
        Método de remover um Objeto no banco
     */
    public static long delete(Banco banco, ListaCompras listaCompras){
        //Chama o método de Delete no banco passando a tabela e os valores
        return banco.delete(TABELA, "_id = ?", new String[]{listaCompras.getCodigo().toString()});
    }

    /*
        Método para recuperar uma Lista de Objetos do Banco
     */
    public static List<ListaCompras> getList(Banco banco){
        //Solicita ao banco a query de recuperar todos os objetos
        Cursor cursor = banco.query(TABELA);

        //Cria uma lista de objetos que será retornada
        List<ListaCompras> lista = new ArrayList<>();

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            do{
                //Cria o objeto
                ListaCompras objeto = new ListaCompras();
                //Seta os valores
                objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("_id")));
                objeto.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));

                //Adiciona na lista de retorno
                lista.add(objeto);
            } while(cursor.moveToNext()); //Pega o próximo objeto do cursor
        }
        return lista;
    }

    /*
        Método para recuperar um Objeto do Banco
     */
    public static ListaCompras get(Banco banco, String where, String[] whereArgs){
        //Solicita ao banco a query de recuperar o objeto
        Cursor cursor = banco.query(TABELA, where, whereArgs, null);

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            //Cria o objeto
            ListaCompras objeto = new ListaCompras();
            //Seta os valores
            objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("_id")));
            objeto.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));

            return objeto;
        }
        return null;
    }
}
