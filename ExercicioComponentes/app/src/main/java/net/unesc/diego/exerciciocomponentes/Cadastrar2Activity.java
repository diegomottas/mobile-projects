package net.unesc.diego.exerciciocomponentes;

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
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.unesc.diego.exerciciocomponentes.cadastro.AdapterListView;
import net.unesc.diego.exerciciocomponentes.cadastro.ItemListView;

import java.util.ArrayList;

public class Cadastrar2Activity extends AppCompatActivity {

    private TextView seekBarValue;
    private Notification notification;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar2);

        final EditText txtNome = (EditText) findViewById(R.id.txtNome);
        final EditText txtNumeroCpf = (EditText) findViewById(R.id.txtNumeroCpf);
        final Spinner spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
        final ToggleButton btnAtivo = (ToggleButton) findViewById(R.id.btnAtivo);
        seekBarValue = (TextView) findViewById(R.id.seekBarValue);
        SeekBar seekIdade = (SeekBar) findViewById(R.id.seekIdade);
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

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                builder.setContentText("Nome: " + txtNome.getText().toString());
                notification = builder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, notification);
                Intent intent = new Intent(Cadastrar2Activity.this, Consultar2Activity.class);
                intent.putExtra("nome", txtNome.getText().toString());
                intent.putExtra("cpf", txtNumeroCpf.getText().toString());
                intent.putExtra("sexo", spinnerSexo.getSelectedItem().toString());
                intent.putExtra("status", btnAtivo.isChecked() ? btnAtivo.getTextOn() : btnAtivo.getTextOff());
                intent.putExtra("idade", seekBarValue.getText().toString());
                Cadastrar2Activity.this.startActivity(intent);
            }
        });

        Intent notificationIntent = new Intent(Cadastrar2Activity.this, Consultar2Activity.class);
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
