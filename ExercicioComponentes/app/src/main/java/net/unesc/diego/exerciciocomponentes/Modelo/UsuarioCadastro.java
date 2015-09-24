package net.unesc.diego.exerciciocomponentes.Modelo;

import android.content.ContentValues;
import android.database.Cursor;


import net.unesc.diego.exerciciocomponentes.Banco.Banco;

import java.util.ArrayList;
import java.util.List;

public class UsuarioCadastro {

    public static final String   TABELA  = "usuario_cadastro";
    public static final String[] COLUNAS = new String[]{"_id", "ds_usu_cad", "nr_cpf", "sexo", "ds_status", "nr_idade"};
    public static final String[] PKEY    = new String[]{"_id"};

    public static final String   SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABELA +
            "( _id      INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "  ds_usu_cad      TEXT    NOT NULL, " +
            "  nr_cpf          LONG," +
            "  sexo            LONG," +
            "  ds_status       TEXT," +
            "  nr_idade        LONG);";

    private Integer codigo;
    private String nome;
    private Integer cpf;
    private Integer sexo;
    private String status;
    private Integer idade;

    public UsuarioCadastro(){

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return getNome();
    }

    //Métodos de Repositório

    /*
        Método de inserir um Objeto no banco
     */
    public static long insert(Banco banco, UsuarioCadastro usuarioCadastro){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("ds_usu_cad", usuarioCadastro.getNome());
        contentValues.put("nr_cpf", usuarioCadastro.getCpf());
        contentValues.put("sexo", usuarioCadastro.getSexo());
        contentValues.put("ds_status", usuarioCadastro.getStatus());
        contentValues.put("nr_idade", usuarioCadastro.getIdade());

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insert(TABELA, contentValues);
    }

    /*
        Método de inserir ou atualizar um Objeto no banco
     */
    public static long insertOrUpdate(Banco banco, UsuarioCadastro usuarioCadastro){
        //Cria um ContentValues que é o responsável por levar os dados com coluna e valor para ser inserido no banco
        ContentValues contentValues = new ContentValues();
        contentValues.put("ds_usu_cad", usuarioCadastro.getNome());
        contentValues.put("nr_cpf", usuarioCadastro.getCpf());
        contentValues.put("sexo", usuarioCadastro.getSexo());
        contentValues.put("ds_status", usuarioCadastro.getStatus());
        contentValues.put("nr_idade", usuarioCadastro.getIdade());

        //Chama o método de Insert no banco passando a tabela e os valores
        return banco.insertOrUpdate(TABELA, contentValues);
    }

    /*
        Método de remover um Objeto no banco
     */
    public static long delete(Banco banco, UsuarioCadastro usuarioCadastro){
        ArrayList<Album> lstAlbum = Album.getQuery(banco, "cd_usu_cad = ?", new String[]{usuarioCadastro.getCodigo().toString()});
        if(lstAlbum.isEmpty()){
            //Chama o método de Delete no banco passando a tabela e os valores
            return banco.delete(TABELA, "cd_usu_cad = ?", new String[]{usuarioCadastro.getCodigo().toString()});
        }
        return -1;
    }

    /*
        Método para recuperar uma Lista de Objetos do Banco
     */
    public static List<UsuarioCadastro> getList(Banco banco){
        //Solicita ao banco a query de recuperar todos os objetos
        Cursor cursor = banco.query(TABELA);

        //Cria uma lista de objetos que será retornada
        List<UsuarioCadastro> lista = new ArrayList<>();

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            do{
                //Cria o objeto
                UsuarioCadastro objeto = new UsuarioCadastro();
                //Seta os valores
                objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("cd_usu_cad")));
                objeto.setNome(cursor.getString(cursor.getColumnIndex("ds_usu_cad")));
                objeto.setCpf(cursor.getInt(cursor.getColumnIndex("nr_cpf")));
                objeto.setSexo(cursor.getInt(cursor.getColumnIndex("sexo")));
                objeto.setStatus(cursor.getString(cursor.getColumnIndex("ds_status")));
                objeto.setIdade(cursor.getInt(cursor.getColumnIndex("nr_idade")));

                //Adiciona na lista de retorno
                lista.add(objeto);
            } while(cursor.moveToNext()); //Pega o próximo objeto do cursor
        }
        return lista;
    }

    /*
        Método para recuperar um Objeto do Banco
     */
    public static UsuarioCadastro get(Banco banco, String where, String[] whereArgs){
        //Solicita ao banco a query de recuperar o objeto
        Cursor cursor = banco.query(TABELA, where, whereArgs, null);

        //Verifica se existe algum registro no cursor
        if(cursor.moveToFirst()){
            //Cria o objeto
            UsuarioCadastro objeto = new UsuarioCadastro();
            //Seta os valores
            objeto.setCodigo(cursor.getInt(cursor.getColumnIndex("cd_usu_cad")));
            objeto.setNome(cursor.getString(cursor.getColumnIndex("ds_usu_cad")));
            objeto.setCpf(cursor.getInt(cursor.getColumnIndex("nr_cpf")));
            objeto.setSexo(cursor.getInt(cursor.getColumnIndex("sexo")));
            objeto.setStatus(cursor.getString(cursor.getColumnIndex("ds_status")));
            objeto.setIdade(cursor.getInt(cursor.getColumnIndex("nr_idade")));

            return objeto;
        }
        return null;
    }
}
