package net.unesc.diego.exerciciocomponentes.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by wbertan on 12/04/15.
 */
public class Banco {
    private       SQLiteDatabase banco;       //Instância do Banco para ser manipulada
    private final Context        context;     //Contexto que está sendo utilizado o banco
    private final BancoHelper    bancoHelper; //Helper para acesso ao banco

    public Banco(Context context){
        //Inicia o Helper com as configurações do banco
        this.context = context;
        bancoHelper = new BancoHelper(context, BancoConfiguracao.BANCO_NOME, null,
                                               BancoConfiguracao.BANCO_VERSAO);
    }

    public void close(){
        //Fecha a conexão com o banco
        banco.close();
    }

    public void open() throws SQLiteException{
        //Abre a conexão com o banco
        try {
            //Pega uma conexão de escrita com  banco
            banco = bancoHelper.getWritableDatabase();
        } catch(SQLiteException e) {
            Log.v("Banco", "[ERRO] Problema ao abrir banco: " + e.getMessage());
            //Pega uma conexão de leitura com o banco
            banco = bancoHelper.getReadableDatabase();
        }
    }

    public long insert(String tabela, ContentValues contentValues){
        //Executa um insert em uma tabela com os valores passados no ContentValues
        try{
            return banco.insert(tabela, null, contentValues);
        } catch(SQLiteException e) {
            Log.v("Banco", "[ERRO] Problema ao inserir no banco: " + e.getMessage());
            return -1;
        }
    }

    public long insertOrUpdate(String tabela, ContentValues contentValues){
        //Executa um insert ou update em uma tabela com os valores passados no ContentValues
        try{
            return banco.replace(tabela, null, contentValues);
        } catch(SQLiteException e) {
            Log.v("Banco", "[ERRO] Problema ao inserir ou atualizar no banco: " + e.getMessage());
            return -1;
        }
    }

    public long delete(String tabela, String where, String[] whereValues){
        //Executa um delete em uma tabela com o where
        try{
            return banco.delete(tabela, where, whereValues);
        } catch(SQLiteException e) {
            Log.v("Banco", "[ERRO] Problema ao remover no banco: " + e.getMessage());
            return -1;
        }
    }

    public Cursor query(String tabela){
        //Recupera uma consulta no banco, sem parametros
        return banco.query(tabela, null, null, null, null, null, null);
    }

    public Cursor query(String tabela, String where, String[] whereValues, String orderBy){
        //Recupera uma consulta no banco, com parametros
        return banco.query(tabela, null, where, whereValues, null, null, orderBy);
    }
}
