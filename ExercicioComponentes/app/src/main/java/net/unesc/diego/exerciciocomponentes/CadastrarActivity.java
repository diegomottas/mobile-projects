package net.unesc.diego.exerciciocomponentes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import net.unesc.diego.exerciciocomponentes.cadastro.AdapterListView;
import net.unesc.diego.exerciciocomponentes.cadastro.ItemListView;

import java.util.ArrayList;

public class CadastrarActivity extends AppCompatActivity {

    private ArrayList<ItemListView> listItens = new ArrayList<ItemListView>();
    private AdapterListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar2);

        Intent startIntent = getIntent();
        String usuario = startIntent.getStringExtra("usuario");
        String senha = startIntent.getStringExtra("senha");

        final EditText txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        ImageButton btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        final ListView listViewItens = (ListView) findViewById(R.id.listViewItens);
        adapter = new AdapterListView(getApplicationContext(), listItens);
        listViewItens.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListView itemListView = new ItemListView();
                itemListView.setTexto(txtDescricao.getText().toString());
                listItens.add(itemListView);
                adapter.notifyDataSetChanged();
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
