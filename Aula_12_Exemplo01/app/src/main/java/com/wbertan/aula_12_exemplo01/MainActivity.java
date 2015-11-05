package com.wbertan.aula_12_exemplo01;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.wbertan.aula_12_exemplo01.http.HttpServlet;
import com.wbertan.aula_12_exemplo01.modelo.Clima;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_pais           = (TextView) findViewById(R.id.tv_pais);
        TextView tv_temperatura    = (TextView) findViewById(R.id.tv_temperatura);
        TextView tv_hora_amanhecer = (TextView) findViewById(R.id.tv_hora_amanhecer);
        TextView tv_hora_anoitecer = (TextView) findViewById(R.id.tv_hora_anoitecer);

        //Recupera o JSON da URL
        String json = HttpServlet.call("http://api.openweathermap.org/data/2.5/weather?q=Criciuma,br&appid=bd82977b86bf27fb59a04b61b657fb6f");

        //Cria o nosso Objeto Clima
        Clima clima = new Clima(json);

        //Seta os valores de clima nos componentes da tela
        tv_pais.setText(clima.getPais());
        tv_temperatura.setText(clima.getTemperatura().toString());
        tv_hora_amanhecer.setText(clima.getHora_amanhecer().toString());
        tv_hora_anoitecer.setText(clima.getHora_anoitecer().toString());
    }
}
