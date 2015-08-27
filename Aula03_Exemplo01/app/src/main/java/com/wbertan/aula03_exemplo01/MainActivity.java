package com.wbertan.aula03_exemplo01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private static final int RETORNO_RESULTADO_SOMA = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_tela  = (TextView) findViewById(R.id.tv_tela);
        Button   btn_tela = (Button)   findViewById(R.id.btn_tela);

        btn_tela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                                           CalcActivity.class);
                intent.putExtra("numero01", 10);
                intent.putExtra("numero02", 20);
                startActivityForResult(intent, RETORNO_RESULTADO_SOMA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (RETORNO_RESULTADO_SOMA) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Integer resultado = bundle.getInt("resultado");

                    TextView tv_tela  = (TextView) findViewById(R.id.tv_tela);
                    tv_tela.setText(resultado.toString());
                }
                break;
            }
        }
    }
}
