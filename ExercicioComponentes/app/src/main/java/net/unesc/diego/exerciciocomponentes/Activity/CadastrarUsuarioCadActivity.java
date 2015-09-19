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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import net.unesc.diego.exerciciocomponentes.Banco.Banco;
import net.unesc.diego.exerciciocomponentes.Modelo.UsuarioCadastro;
import net.unesc.diego.exerciciocomponentes.R;

public class CadastrarUsuarioCadActivity extends AppCompatActivity {

    private Banco banco;

    private EditText txtNome;
    private EditText txtNumeroCpf;
    private Spinner spinnerSexo;
    private ToggleButton btnAtivo;
    private TextView seekBarValue;
    private SeekBar seekIdade;

    private Notification notification;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usu_cad);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtNumeroCpf = (EditText) findViewById(R.id.txtNumeroCpf);
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
        btnAtivo = (ToggleButton) findViewById(R.id.btnAtivo);
        seekBarValue = (TextView) findViewById(R.id.seekBarValue);
        seekIdade = (SeekBar) findViewById(R.id.seekIdade);
        ImageButton btnSalvar = (ImageButton) findViewById(R.id.btnSalvar);

        SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText("Idade:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setSecondaryProgress(seekBar.getProgress());
            }
        };
        seekIdade.setOnSeekBarChangeListener(seekListener);

        banco = new Banco(getApplicationContext());
        banco.open();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("cd_usu_cad")){
            UsuarioCadastro usuarioCadastro = UsuarioCadastro.get(banco, "cd_usu_cad = ?", new String[]{String.valueOf(bundle.getInt("cd_usu_cad"))});

            if(usuarioCadastro != null){
                txtNome.setText(usuarioCadastro.getNome());
                txtNumeroCpf.setText(usuarioCadastro.getCpf().toString());
                spinnerSexo.setSelection(usuarioCadastro.getSexo());
                if(getString(R.string.ativo).equals(usuarioCadastro.getStatus())){
                    btnAtivo.setChecked(true);
                } else{
                    btnAtivo.setChecked(false);
                }
                seekIdade.setProgress(usuarioCadastro.getIdade());
            }
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                builder.setContentText("Nome: " + txtNome.getText().toString());
                notification = builder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, notification);

                UsuarioCadastro usuarioCadastro = new UsuarioCadastro();

                usuarioCadastro.setNome(txtNome.getText().toString());
                if(txtNumeroCpf.getText() != null && !txtNumeroCpf.getText().toString().isEmpty()){
                    usuarioCadastro.setCpf(Integer.valueOf(txtNumeroCpf.getText().toString()));
                }
                usuarioCadastro.setSexo(spinnerSexo.getSelectedItemPosition());
                usuarioCadastro.setStatus(btnAtivo.isChecked() ? btnAtivo.getTextOn().toString() : btnAtivo.getTextOff().toString());
                usuarioCadastro.setIdade(Integer.valueOf(seekIdade.getProgress()));

                long retorno = UsuarioCadastro.insertOrUpdate(banco, usuarioCadastro);

                if(retorno > 0) {
                    Toast.makeText(CadastrarUsuarioCadActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    //Vai para a tela de consulta
                    Intent intent = new Intent(CadastrarUsuarioCadActivity.this, ConsultarUsuarioCadActivity.class);
                    CadastrarUsuarioCadActivity.this.startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CadastrarUsuarioCadActivity.this, "Problema ao inserir.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Intent notificationIntent = new Intent(CadastrarUsuarioCadActivity.this, ConsultarUsuarioCadActivity.class);
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
