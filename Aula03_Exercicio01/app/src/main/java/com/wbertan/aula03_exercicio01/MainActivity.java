package com.wbertan.aula03_exercicio01;

import android.app.AlertDialog;
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
    private static final int RETORNO_NUMERO_01 = 1001;
    private static final int RETORNO_NUMERO_02 = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_numero01 = (Button) findViewById(R.id.btn_numero01);
        final Button btn_numero02 = (Button) findViewById(R.id.btn_numero02);
              Button btn_calcular = (Button) findViewById(R.id.btn_calcular);

        btn_numero01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga o número 01");
                startActivityForResult(intent, RETORNO_NUMERO_01);
            }
        });

        btn_numero02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga o número 02");
                startActivityForResult(intent, RETORNO_NUMERO_02);
            }
        });

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer numero01 = isNumber(btn_numero01.getText().toString());
                Integer numero02 = isNumber(btn_numero02.getText().toString());

                if(numero01 != null && numero02 != null){
                    Integer soma = numero01 + numero02;

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(soma.toString())
                           .setTitle("Resultado");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETORNO_NUMERO_01 && resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Button btn_numero01 = (Button) findViewById(R.id.btn_numero01);

            Integer numero = null;
            for(String word : result) {
                numero = isNumber(word);
                if(numero != null){
                    break;
                }
                numero = isTextNumber(word);
                if(numero != null){
                    break;
                }
            }
            if(numero != null) {
                btn_numero01.setText(numero.toString());
            } else {
                btn_numero01.setText("Não reconhecido");
            }
        }
        if (requestCode == RETORNO_NUMERO_02 && resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Button btn_numero02 = (Button) findViewById(R.id.btn_numero02);

            Integer numero = null;
            for(String word : result) {
                numero = isNumber(word);
                if(numero != null){
                    break;
                }
                numero = isTextNumber(word);
                if(numero != null){
                    break;
                }
            }
            if(numero != null) {
                btn_numero02.setText(numero.toString());
            } else {
                btn_numero02.setText("Não reconhecido");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Integer isNumber(String word) {
        boolean isNumber = false;
        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer isTextNumber(String word) {
        switch(word) {
            case "one":
                return 1;
            case "too":
            case "two":
                return 2;
            case "tree":
            case "three":
                return 3;
            case "for":
            case "four":
            case "4th":
                return 4;
            default:
                return null;
        }
    }
}
