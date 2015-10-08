package net.unesc.diego.exerciciocomponentes.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.terlici.dragndroplist.DragNDropCursorAdapter;
import com.terlici.dragndroplist.DragNDropListView;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;
import net.unesc.diego.exerciciocomponentes.Modelo.ListaCompras;
import net.unesc.diego.exerciciocomponentes.R;

import java.util.ArrayList;

public class CadastrarActivity extends AppCompatActivity {
    private Banco banco;

    private ArrayList<ListaCompras> listItens = new ArrayList<ListaCompras>();
    private EditText txtDescricao;
    private ImageButton btnAdicionar;
    private DragNDropListView listView;
    private DragNDropCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);


        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        listView = (DragNDropListView)findViewById(android.R.id.list);

        banco = new Banco(getApplicationContext());
        banco.open();
        cursor = banco.query(ListaCompras.TABELA);
        adapter = new DragNDropCursorAdapter(getApplicationContext(),
                R.layout.rowlayout,
                cursor,
                new String[]{ListaCompras.COLUNAS[1]},
                new int[]{R.id.text},
                R.id.handler);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Pega o Objeto na posição
                ListaCompras listaCompras = (ListaCompras) listView.getItemAtPosition(position);

                long resultado = ListaCompras.delete(banco, listaCompras);

                //Verifica se foi removido com sucesso
                if (resultado > 0) {
                    Toast.makeText(CadastrarActivity.this, "Removido com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastrarActivity.this, "Problema ao remover.", Toast.LENGTH_SHORT).show();
                }

                //Recria a Activity
                recreate();

                return true;
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = txtDescricao.getText().toString();
                if (str == null || str.isEmpty()) {
                    txtDescricao.setError(getString(R.string.msgInsiraDescricao));
                } else {
                    ListaCompras listaCompras = new ListaCompras();
                    listaCompras.setDescricao(str);
                    listItens.add(listaCompras);
                    txtDescricao.setText("");
                    ListaCompras.insertOrUpdate(banco, listaCompras);
//                    cursor = banco.query(ListaCompras.TABELA);
                    CadastrarActivity.this.recreate();
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
