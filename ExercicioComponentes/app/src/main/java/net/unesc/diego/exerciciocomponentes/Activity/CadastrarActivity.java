package net.unesc.diego.exerciciocomponentes.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.terlici.dragndroplist.DragNDropCursorAdapter;
import com.terlici.dragndroplist.DragNDropListView;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;
import net.unesc.diego.exerciciocomponentes.Components.ItemListView;
import net.unesc.diego.exerciciocomponentes.Modelo.UsuarioCadastro;
import net.unesc.diego.exerciciocomponentes.R;

import java.util.ArrayList;

public class CadastrarActivity extends AppCompatActivity {

    private ArrayList<ItemListView> listItens = new ArrayList<ItemListView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);


        final EditText txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        ImageButton btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        DragNDropListView list = (DragNDropListView)findViewById(android.R.id.list);

        Banco banco = new Banco(getApplicationContext());
        banco.open();
        Cursor cursor = banco.query(UsuarioCadastro.TABELA);
        DragNDropCursorAdapter adapter = new DragNDropCursorAdapter(getApplicationContext(),
                R.layout.rowlayout,
                cursor,
                new String[]{UsuarioCadastro.COLUNAS[0], UsuarioCadastro.COLUNAS[1]},
                new int[]{R.id.text},
                R.id.handler);

        list.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = txtDescricao.getText().toString();
                if(str == null || str.isEmpty()){
                    txtDescricao.setError(getString(R.string.msgInsiraDescricao));
                }else{
                    ItemListView itemListView = new ItemListView();
                    itemListView.setTexto(str);
                    listItens.add(itemListView);
                    txtDescricao.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
