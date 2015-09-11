package net.unesc.diego.exerciciocomponentes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Consultar2Activity extends AppCompatActivity {

    private TextView viewNome;
    private TextView viewNumeroCpf;
    private TextView viewSexo;
    private TextView viewStatus;
    private TextView viewIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar2);

        Intent startIntent = getIntent();
        String nome = startIntent.getStringExtra("nome");
        String cpf = startIntent.getStringExtra("cpf");
        String sexo = startIntent.getStringExtra("sexo");
        String status = startIntent.getStringExtra("status");
        String idade = startIntent.getStringExtra("idade");

        viewNome = (TextView) findViewById(R.id.viewNome);
        viewNumeroCpf = (TextView) findViewById(R.id.viewNumeroCpf);
        viewSexo = (TextView) findViewById(R.id.viewSexo);
        viewStatus = (TextView) findViewById(R.id.viewStatus);
        viewIdade = (TextView) findViewById(R.id.viewIdade);

        viewNome.setText("Nome:  " + nome);
        viewNumeroCpf.setText("CPF: " + cpf);
        viewSexo.setText("Sexo: " + sexo);
        viewStatus.setText("Status: " + status);
        viewIdade.setText(idade);


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
