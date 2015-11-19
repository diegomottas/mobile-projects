package net.unesc.diego.avaliacaodois;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.unesc.diego.avaliacaodois.webservice.cependereco.AsyncConsultaCepTask;
import net.unesc.diego.avaliacaodois.webservice.cependereco.CepEndereco;
import net.unesc.diego.avaliacaodois.webservice.cependereco.OnAsyncTaskCompleted;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements OnAsyncTaskCompleted {

    private EditText txtCep;
    private Button btnProcurar;
    private TextView tvDescricao;

    private Animation animationAlphaFadeOut;
    private Animation animationAlphaFadeIn;

    private AsyncTask<String, Integer, CepEndereco> asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtCep = (EditText) findViewById(R.id.txtCep);
        btnProcurar = (Button) findViewById(R.id.btnProcurar);
        tvDescricao = (TextView) findViewById(R.id.tvDescricao);

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = txtCep.getText().toString();
                asyncTask = new AsyncConsultaCepTask(MainActivity.this).execute(cep);
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

    @Override
    public void onTaskCompleted() {
        try {
            CepEndereco cepEndereco = asyncTask.get();

            if(cepEndereco != null){
                StringBuilder endereco = new StringBuilder();
                if(cepEndereco.getTipoDeLogradouro() != null){
                    endereco.append(cepEndereco.getTipoDeLogradouro());
                    endereco.append(" ");
                }
                endereco.append(cepEndereco.getLogradouro());
                endereco.append(", ");
                endereco.append("Bairro ");
                endereco.append(cepEndereco.getBairro());
                endereco.append(", ");
                endereco.append(cepEndereco.getCidade());
                endereco.append(", ");
                endereco.append(cepEndereco.getEstado());
                tvDescricao.setText(endereco.toString());

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacao_escala01);
                tvDescricao.startAnimation(animation);

                animationAlphaFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacao_fade_out);
                animationAlphaFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacao_fade_in);

                animationAlphaFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        btnProcurar.startAnimation(animationAlphaFadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                btnProcurar.startAnimation(animationAlphaFadeOut);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
