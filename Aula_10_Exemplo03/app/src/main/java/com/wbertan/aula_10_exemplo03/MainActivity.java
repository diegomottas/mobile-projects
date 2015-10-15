package com.wbertan.aula_10_exemplo03;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.wbertan.aula_10_exemplo03.widget.PlacarView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera nosso componente e altera valores nele
        PlacarView placar1 = (PlacarView) findViewById(R.id.placar1);
        //placar1.setLogo_time1(R.drawable.flag_inter);
        //placar1.setTime1("Inter!");
    }
}
