package net.unesc.diego.exerciciocomponentes.Banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wbertan on 12/04/15.
 */
public class BancoHelper extends SQLiteOpenHelper {

    public BancoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("BancoHelper onCreate", "Criando as tabelas");
        try {
            //Criando o banco, executamos o script de criar as tabelas
            for(Class classe : BancoConfiguracao.TABELAS){
                db.execSQL((String) classe.getField("SQL_CREATE").get(null));
            }
        } catch(SQLiteException e) {
            Log.v("BancoHelper", "[ERRO] Problema ao criar banco: " + e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.v("BancoHelper", "[ERRO] Problema ao recuperar SQL de classe: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.v("BancoHelper", "[ERRO] Problema ao acessar classe: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("BancoHelper", "Atualizando da versão " + oldVersion + " para " + newVersion);

        try {
            switch(newVersion){
                case 2: {
                    //Executar os SQLs de atualização do Banco para a versão 2
                    //db.execSQL("");
                }
                break;
            }
        } catch(SQLiteException ex) {
            Log.v("BancoHelper", "[ERRO] Problema ao atualizar banco: " + ex.getMessage());
        }
        Log.v("BancoHelper", "Banco atualizado com sucesso.");
    }
}
