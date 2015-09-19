package net.unesc.diego.exerciciocomponentes.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;
import net.unesc.diego.exerciciocomponentes.Modelo.Album;
import net.unesc.diego.exerciciocomponentes.R;

import java.util.List;

public class ConsultarAlbumActivity extends AppCompatActivity {

    private Banco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        //Cria e abre o banco
        banco = new Banco(getApplicationContext());
        banco.open();

        //Recupera a listagem de objetos
        List<Album> listaAlbum = Album.getList(banco);

        ImageButton btnNovo = (ImageButton) findViewById(R.id.btnNovo);
        btnNovo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultarAlbumActivity.this, CadastrarAlbumActivity.class);
                ConsultarAlbumActivity.this.startActivity(intent);
            }
        });

        //Recupera o ListView onde irá mostrar a consulta
        final ListView lvConsulta = (ListView) findViewById(R.id.lv_consulta);

        //Recupera o TextView que informa que não houveram resultados
        TextView tvSemResultado = (TextView) findViewById(R.id.tv_sem_resultado);

        if(listaAlbum != null && listaAlbum.size() > 0){
            //Possui resultados, então esconde o Texto e Mostra a ListView
            lvConsulta.setVisibility(View.VISIBLE);
            tvSemResultado.setVisibility(View.GONE);

            //Cria o ListAdapter que irá mostrar corretamente nosso Card com as informações
            final ListAdapter customAdapter = new ListAdapter(this, R.layout.card, listaAlbum);

            //Seta o ListAdapter na nossa ListView
            lvConsulta.setAdapter(customAdapter);

            lvConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Pega o Objeto na posição
                    Album album = (Album) lvConsulta.getItemAtPosition(position);

                    Intent intent = new Intent(ConsultarAlbumActivity.this, CadastrarAlbumActivity.class);
                    intent.putExtra(Album.PKEY[0], album.getCodigo());

                    startActivity(intent);
                    finish();
                }
            });

            lvConsulta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //Pega o Objeto na posição
                    Album autor = (Album) lvConsulta.getItemAtPosition(position);

                    long resultado = Album.delete(banco, autor);

                    //Verifica se foi removido com sucesso
                    if(resultado > 0) {
                        Toast.makeText(ConsultarAlbumActivity.this, "Removido com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ConsultarAlbumActivity.this, "Problema ao remover.", Toast.LENGTH_SHORT).show();
                    }

                    //Recria a Activity
                    recreate();

                    return true;
                }
            });
        } else {
            //Não possui resultados, então esconde a ListView e Mostra somente o Texto
            lvConsulta.setVisibility(View.GONE);
            tvSemResultado.setVisibility(View.VISIBLE);
        }

    }

    private class ListAdapter extends ArrayAdapter<Album> {

        public ListAdapter(Context context, int resource, List<Album> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflater;
                layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(R.layout.card, null);
            }

            final Album album = getItem(position);

            if (album != null) {
                TextView tvCdAutor = (TextView) view.findViewById(R.id.tv_codigo);
                TextView tvDsAutor = (TextView) view.findViewById(R.id.tv_descricao);

                tvCdAutor.setText(album.getCodigo().toString());
                tvDsAutor.setText(album.getDescricao());
            }
            return view;

        }
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
