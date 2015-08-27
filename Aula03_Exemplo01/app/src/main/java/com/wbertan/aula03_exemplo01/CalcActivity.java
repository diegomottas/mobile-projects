package com.wbertan.aula03_exemplo01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wbertan on 18/03/15.
 */
public class CalcActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        TextView tv_numero01  = (TextView) findViewById(R.id.tv_numero01);
        TextView tv_numero02  = (TextView) findViewById(R.id.tv_numero02);
        Button   btn_resultado = (Button)   findViewById(R.id.btn_resultado);

        Bundle bundle = getIntent().getExtras();

        final int numero01 = bundle.getInt("numero01");
        final int numero02 = bundle.getInt("numero02");

        tv_numero01.setText(String.valueOf(numero01));
        tv_numero02.setText(String.valueOf(numero02));

        btn_resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("resultado", numero01 + numero02);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
