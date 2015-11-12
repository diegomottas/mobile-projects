package net.unesc.diego.avaliacaodois;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.unesc.diego.avaliacaodois.webservice.cependereco.CepEndereco;

public class MainActivity extends AppCompatActivity {

    private EditText txtCep;
    private Button btnProcurar;
    private TextView tvDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCep = (EditText) findViewById(R.id.txtCep);
        btnProcurar = (Button) findViewById(R.id.btnProcurar);
        tvDescricao = (TextView) findViewById(R.id.tvDescricao);

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = txtCep.getText().toString();
                CepEndereco cepEndereco = CepEndereco.getCepEndereco(cep);
                tvDescricao.setText(cepEndereco.getCidade());
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
