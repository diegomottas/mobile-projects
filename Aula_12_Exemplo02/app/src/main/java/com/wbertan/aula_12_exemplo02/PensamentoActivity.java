package com.wbertan.aula_12_exemplo02;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbertan.aula_12_exemplo02.modelo.Pensamento;

/**
 * Created by wbertan on 19/05/15.
 */

public class PensamentoActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalhe);

        //Recupera o bundle
        Bundle bundle = getIntent().getExtras();

        //Recupera os valores passados pelo Bundle
        String nm_pensador      = bundle.getString("nm_pensador");
        String ds_pensamento    = bundle.getString("ds_pensamento");
        int    id_foto_pensador = bundle.getInt   ("id_foto_pensador");

        //Cria o objeto através das informações
        Pensamento pensamento = new Pensamento(nm_pensador, id_foto_pensador, ds_pensamento);

        //Recupera os componentes da tela
        ImageView iv_pensador   = (ImageView) findViewById(R.id.iv_pensador);
        TextView  tv_pensador   = (TextView)  findViewById(R.id.tv_pensador);
        TextView  tv_pensamento = (TextView)  findViewById(R.id.tv_pensamento);

        //Seta os valores nos componentes
        tv_pensador.setText(pensamento.getNm_pensador());
        tv_pensamento.setText(pensamento.getDs_pensamento());
        iv_pensador.setImageDrawable(getDrawable(pensamento.getId_foto_pensador()));
    }
}
