package com.example.diego.avaliacaotres.model;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;

import com.example.diego.avaliacaotres.R;
import com.example.diego.avaliacaotres.database.Banco;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ItemListaCompras {

    public static final String TABLE_NAME = "item_lista_compras";
    public static final String[] PKEY    = new String[]{"_id"};

    public enum Colunas {
        ID(0, "_id"), DESCRICAO(1, "descricao"), STATUS(2, "status")
        ;
        private final Integer index;
        private final String descricao;

        Colunas(Integer index, String descricao) {
            this.index = index;
            this.descricao = descricao;
        }

        public Integer getIndex() {
            return index;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum Status {
        ABERTO(0),
        FECHADO(1)
        ;
        private final Integer value;

        Status(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

    }

    public static final String   SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "( _id      INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " descricao        TEXT NOT NULL," +
            " status           INTEGER NOT NULL);";

    private Integer codigo;
    private String descricao;
    private Integer status;

    public ItemListaCompras(){

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /*
        Método de inserir um Objeto no banco
     */
    public static long insert(Banco banco, ItemListaCompras itemListaCompras){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put(Colunas.DESCRICAO.getDescricao(), itemListaCompras.getDescricao());
        contentValues.put(Colunas.STATUS.getDescricao(), Status.ABERTO.getValue());

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insert(TABLE_NAME, contentValues);
    }

    /*
        Método de inserir ou atualizar um Objeto no banco
     */
    public static long insertOrUpdate(Banco banco, ItemListaCompras itemListaCompras){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put(Colunas.ID.getDescricao(), itemListaCompras.getCodigo());
        contentValues.put(Colunas.DESCRICAO.getDescricao(), itemListaCompras.getDescricao());

        if(itemListaCompras.getStatus() == null) {
            itemListaCompras.setStatus(Status.ABERTO.getValue());
        }
        contentValues.put(Colunas.STATUS.getDescricao(), itemListaCompras.getStatus());

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insertOrUpdate(TABLE_NAME, contentValues);
    }

    /*
        Método de remover um Objeto no banco
     */
    public static long delete(Banco banco, ItemListaCompras itemListaCompras){
        //Chama o método de Delete no banco passando a tabela e os valores
        return banco.delete(TABLE_NAME, "_id = ?", new String[]{itemListaCompras.getCodigo().toString()});
    }

    /*
        Método de remover múltiplos Objetos no banco
     */
    public static void delete(Banco banco, List<String> ids){
        if(ids != null && !ids.isEmpty()){
            ListIterator<String> iterator = ids.listIterator();
            while(iterator.hasNext()){
                String next = iterator.next();
                banco.delete(TABLE_NAME, "_id = ?", new String[]{next});
            }
        }
    }

    /*
        Método para recuperar uma Lista de Objetos do Banco
     */
    public static List<ItemListaCompras> getList(Banco banco){
        //Solicita ao banco a query de recuperar todos os objetos
        Cursor cursor = banco.query(TABLE_NAME);

        //Cria uma lista de objetos que será retornada
        List<ItemListaCompras> lista = new ArrayList<>();

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            do{
                //Cria o objeto
                ItemListaCompras objeto = new ItemListaCompras();
                //Seta os valores
                objeto.setCodigo(cursor.getInt(Colunas.ID.getIndex()));
                objeto.setDescricao(cursor.getString(Colunas.DESCRICAO.getIndex()));
                objeto.setStatus(cursor.getInt(Colunas.STATUS.getIndex()));

                //Adiciona na lista de retorno
                lista.add(objeto);
            } while(cursor.moveToNext()); //Pega o próximo objeto do cursor
        }
        return lista;
    }

    /*
        Método para recuperar um Objeto do Banco
     */
    public static ItemListaCompras get(Banco banco, String where, String[] whereArgs){
        //Solicita ao banco a query de recuperar o objeto
        Cursor cursor = banco.query(TABLE_NAME, where, whereArgs, null);

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            //Cria o objeto
            ItemListaCompras objeto = new ItemListaCompras();
            //Seta os valores
            objeto.setCodigo(cursor.getInt(Colunas.ID.getIndex()));
            objeto.setDescricao(cursor.getString(Colunas.DESCRICAO.getIndex()));
            objeto.setStatus(cursor.getInt(Colunas.STATUS.getIndex()));

            return objeto;
        }
        return null;
    }

    /*
        Método para recuperar um Objeto de um cursor
     */
    public static ItemListaCompras get(Cursor cursor, int position){
        cursor.moveToPosition(position);

        //Cria o objeto
        ItemListaCompras objeto = new ItemListaCompras();
        //Seta os valores
        objeto.setCodigo(cursor.getInt(Colunas.ID.getIndex()));
        objeto.setDescricao(cursor.getString(Colunas.DESCRICAO.getIndex()));
        objeto.setStatus(cursor.getInt(Colunas.STATUS.getIndex()));

        return objeto;
    }
}
