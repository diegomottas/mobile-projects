package net.unesc.diego.exerciciocomponentes.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;
import net.unesc.diego.exerciciocomponentes.Modelo.Album;
import net.unesc.diego.exerciciocomponentes.Modelo.UsuarioCadastro;
import net.unesc.diego.exerciciocomponentes.R;

import java.util.ArrayList;
import java.util.List;

public class CadastrarAlbumActivity extends AppCompatActivity {

    private Banco banco;
    private Album albumEdicao;

    private EditText txtDescricao;
    private EditText txtBanda;
    private Spinner spinnerUsuarioCadastro;

    private Notification notification;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_album);

        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        txtBanda = (EditText) findViewById(R.id.txtBanda);
        spinnerUsuarioCadastro = (Spinner) findViewById(R.id.spinnerUsuarioCadastro);
        ImageButton btnSalvar = (ImageButton) findViewById(R.id.btnSalvar);


        banco = new Banco(getApplicationContext());
        banco.open();

        List<UsuarioCadastro> lstBanco = UsuarioCadastro.getList(banco);
        ArrayList<UsuarioCadastro> lstUsuarioCadastro = new ArrayList<UsuarioCadastro>();
        lstUsuarioCadastro.add(new UsuarioCadastro());
        lstUsuarioCadastro.addAll(lstBanco);
        ArrayAdapter<UsuarioCadastro> arrayAdapter= new ArrayAdapter<UsuarioCadastro>(getApplicationContext(), R.layout.custom_spinner_item, lstUsuarioCadastro);
        spinnerUsuarioCadastro.setAdapter(arrayAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey(Album.PKEY[0])){
            albumEdicao = Album.get(banco, Album.PKEY[0] + " = ?", new String[]{String.valueOf(bundle.getInt(Album.PKEY[0]))});

            if(albumEdicao != null){
                txtDescricao.setText(albumEdicao.getDescricao());
                txtBanda.setText(albumEdicao.getBanda());
                if(albumEdicao.getUsuarioCadastro() != null){
                    Integer position = null;
                    for(int i = 0; i < lstUsuarioCadastro.size(); i++){
                        if(albumEdicao.getUsuarioCadastro().getCodigo().equals(lstUsuarioCadastro.get(i).getCodigo())){
                            position = i;
                            break;
                        }
                    }
                    if(position != null){
                        spinnerUsuarioCadastro.setSelection(position);
                    }
                }
            }
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                builder.setContentText("Descricao: " + txtDescricao.getText().toString());
                notification = builder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, notification);

                Album album = new Album();

                if(albumEdicao != null){
                    album.setCodigo(albumEdicao.getCodigo());
                }
                album.setDescricao(txtDescricao.getText().toString());
                album.setBanda(txtBanda.getText().toString());
                UsuarioCadastro selectedItem = (UsuarioCadastro) spinnerUsuarioCadastro.getSelectedItem();
                album.setUsuarioCadastro(selectedItem);

                long retorno = Album.insertOrUpdate(banco, album);

                if(retorno > 0) {
                    Toast.makeText(CadastrarAlbumActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    //Vai para a tela de consulta
                    Intent intent = new Intent(CadastrarAlbumActivity.this, ConsultarAlbumActivity.class);
                    CadastrarAlbumActivity.this.startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CadastrarAlbumActivity.this, "Problema ao inserir.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Intent notificationIntent = new Intent(CadastrarAlbumActivity.this, ConsultarUsuarioCadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
        builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.android_small)
                .setContentTitle("Item Cadastrado com sucesso")
                .setContentIntent(pendingIntent);


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
