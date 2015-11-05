package com.wbertan.aula_13_exemplo01;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wbertan.aula_13_exemplo01.http.HttpServlet;
import com.wbertan.aula_13_exemplo01.http.HttpWebService;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_servlet = (Button) findViewById(R.id.btn_servlet);
        Button btn_webservice = (Button) findViewById(R.id.btn_webservice);

        btn_servlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = "-";
                ToggleButton tb_usuario = (ToggleButton) findViewById(R.id.tb_usuario);
                if(tb_usuario.isChecked()){
                    EditText edt_usuario = (EditText) findViewById(R.id.edt_usuario);
                    //Basta chamar a URL e passar os parametros
                    retorno = HttpServlet.call("http://servlet.wbertan.com:49600/Servidor/Entrar",
                                               "usuario", edt_usuario.getText().toString());
                } else {
                    //Basta chamar a URL e passar os parametros
                    retorno = HttpServlet.call("http://servlet.wbertan.com:49600/Servidor/Entrar");
                }
                if(retorno == null){
                    retorno = "[ERRO]";
                }

                TextView tv_resultado = (TextView) findViewById(R.id.tv_resultado);
                tv_resultado.setText(retorno);
            }
        });

        btn_webservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retorno = "-";
                ToggleButton tb_usuario = (ToggleButton) findViewById(R.id.tb_usuario);
                if(tb_usuario.isChecked()){
                    EditText edt_usuario = (EditText) findViewById(R.id.edt_usuario);
                    //Basta chamar a URL e passar os parametros
                    retorno = HttpWebService.call("http://webservice.wbertan.com/",
                                                  "http://servlet.wbertan.com:49600/Servidor/EntrarWS", "entrar",
                                                  "usuario", edt_usuario.getText().toString());
                } else {
                    //Basta chamar a URL e passar os parametros
                    retorno = HttpWebService.call("http://webservice.wbertan.com/",
                                                  "http://servlet.wbertan.com:49600/Servidor/EntrarWS", "entrar");
                }
                if(retorno == null){
                    retorno = "[ERRO]";
                }

                TextView tv_resultado = (TextView) findViewById(R.id.tv_resultado);
                tv_resultado.setText(retorno);
            }
        });

    }
}
