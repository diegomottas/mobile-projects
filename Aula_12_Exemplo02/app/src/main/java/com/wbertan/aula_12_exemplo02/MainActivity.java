package com.wbertan.aula_12_exemplo02;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            Criado dois layouts activity_main, um para landscape e outro para portrait
         */
        setContentView(R.layout.activity_main);
    }
}
