package net.unesc.diego.exerciciocomponentes.Modelo;

import android.content.ContentValues;
import android.database.Cursor;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;

import java.util.ArrayList;
import java.util.List;

public class Album {

    public static final String   TABELA  = "album";
    public static final String[] COLUNAS = new String[]{"cd_album", "ds_album", "ds_banda"};
    public static final String[] PKEY    = new String[]{"cd_album"};

    public static final String   SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABELA +
            "( cd_album      INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "  ds_album      TEXT    NOT NULL, " +
            "  ds_banda      TEXT," +
            "  cd_usu_cad    INTEGER," +
            "  FOREIGN KEY (cd_usu_cad) REFERENCES " + UsuarioCadastro.TABELA + "(" + UsuarioCadastro.PKEY[0] + "));";

    private Integer codigo;
    private String descricao;
    private String banda;
    private UsuarioCadastro usuarioCadastro;

    public Album(){

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

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public UsuarioCadastro getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(UsuarioCadastro usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    //Métodos de Repositório

    /*
        Método de inserir um Objeto no banco
     */
    public static long insert(Banco banco, Album album){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("ds_album", album.getDescricao());
        contentValues.put("ds_banda", album.getBanda());
        if(album.getUsuarioCadastro() != null){
            contentValues.put("cd_usu_cad", album.getUsuarioCadastro().getCodigo());
        }

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insert(TABELA, contentValues);
    }

    /*
        Método de inserir ou atualizar um Objeto no banco
     */
    public static long insertOrUpdate(Banco banco, Album album){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("cd_album", album.getCodigo());
        contentValues.put("ds_album", album.getDescricao());
        contentValues.put("ds_banda", album.getBanda());
        if(album.getUsuarioCadastro() != null){
            contentValues.put("cd_usu_cad", album.getUsuarioCadastro().getCodigo());
        }

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insertOrUpdate(TABELA, contentValues);
    }

    /*
        Método de remover um Objeto no banco
     */
    public static long delete(Banco banco, Album album){
        //Chama o método de Delete no banco passando a tabela e os valores
        return banco.delete(TABELA, "cd_album = ?", new String[]{album.getCodigo().toString()});
    }

    /*
        Método para recuperar uma Lista de Objetos do Banco
     */
    public static List<Album> getList(Banco banco){
        //Solicita ao banco a query de recuperar todos os objetos
        Cursor cursor = banco.query(TABELA);

        //Cria uma lista de objetos que será retornada
        List<Album> lista = new ArrayList<>();

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            do{
                //Cria o objeto
                Album objeto = new Album();
                //Seta os valores
                objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("cd_album")));
                objeto.setDescricao(cursor.getString(cursor.getColumnIndex("ds_album")));
                objeto.setBanda(cursor.getString(cursor.getColumnIndex("ds_banda")));
                Integer cd_usu_cad = cursor.getInt(cursor.getColumnIndex("cd_usu_cad"));
                if(cd_usu_cad != null){
                    UsuarioCadastro usuarioCadastro = UsuarioCadastro.get(banco, UsuarioCadastro.PKEY[0] + " = ?", new String[]{String.valueOf(cd_usu_cad)});
                    objeto.setUsuarioCadastro(usuarioCadastro);
                }

                //Adiciona na lista de retorno
                lista.add(objeto);
            } while(cursor.moveToNext()); //Pega o próximo objeto do cursor
        }
        return lista;
    }

    /*
        Método para recuperar um Objeto do Banco
     */
    public static Album get(Banco banco, String where, String[] whereArgs){
        //Solicita ao banco a query de recuperar o objeto
        Cursor cursor = banco.query(TABELA, where, whereArgs, null);

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            //Cria o objeto
            Album objeto = new Album();
            //Seta os valores
            objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("cd_album")));
            objeto.setDescricao(cursor.getString(cursor.getColumnIndex("ds_album")));
            objeto.setBanda(cursor.getString(cursor.getColumnIndex("ds_banda")));
            Integer cd_usu_cad = cursor.getInt(cursor.getColumnIndex("cd_usu_cad"));
            if(cd_usu_cad != null){
                UsuarioCadastro usuarioCadastro = UsuarioCadastro.get(banco, UsuarioCadastro.PKEY[0] + " = ?", new String[]{String.valueOf(cd_usu_cad)});
                objeto.setUsuarioCadastro(usuarioCadastro);
            }

            return objeto;
        }
        return null;
    }
    /*
        Método para recuperar uma lista de Objetos do Banco
     */
    public static ArrayList<Album> getQuery(Banco banco, String where, String[] whereArgs){
        ArrayList<Album> lstAlbum = new ArrayList<Album>();
        //Solicita ao banco a query de recuperar o objeto
        Cursor cursor = banco.query(TABELA, where, whereArgs, null);

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            do{
                //Cria o objeto
                Album objeto = new Album();
                //Seta os valores
                objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("cd_album")));
                objeto.setDescricao(cursor.getString(cursor.getColumnIndex("ds_album")));
                objeto.setBanda(cursor.getString(cursor.getColumnIndex("ds_banda")));
                Integer cd_usu_cad = cursor.getInt(cursor.getColumnIndex("cd_usu_cad"));
                if(cd_usu_cad != null){
                    UsuarioCadastro usuarioCadastro = UsuarioCadastro.get(banco, UsuarioCadastro.PKEY[0] + " = ?", new String[]{String.valueOf(cd_usu_cad)});
                    objeto.setUsuarioCadastro(usuarioCadastro);
                }

                lstAlbum.add(objeto);
            } while(cursor.moveToNext()); //Pega o próximo objeto do cursor
        }
        return lstAlbum;
    }
}
