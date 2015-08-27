package com.wbertan.aula03_exemplo02;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final int EXEMPLO = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_textoApresentacao = (TextView) findViewById(R.id.tv_apresentacao);
        Button btn_apresentacao     = (Button)   findViewById(R.id.btn_apresentacao);

        tv_textoApresentacao.setText("Meu novo texto");
        btn_apresentacao    .setText("Meu novo Botão");

        btn_apresentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Diga uma palavra ou frase\ne será mostrado.");
                startActivityForResult(intent, EXEMPLO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXEMPLO && resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            TextView tv_textoApresentacao = (TextView) findViewById(R.id.tv_apresentacao);
            tv_textoApresentacao.setText(result.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
