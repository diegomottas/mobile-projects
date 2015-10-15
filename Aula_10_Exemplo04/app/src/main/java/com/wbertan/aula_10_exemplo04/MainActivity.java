package com.wbertan.aula_10_exemplo04;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Faz um cast da view para ImageView, que é a View que foi clicada;
        ImageView imageView = (ImageView) view;
        //Recupera a View que irá ser animada
        ImageView imageViewAnimado = (ImageView) findViewById(R.id.animatedImage);

        //Seta o Drawable da View que será animada com o Drawable da View clicada
        imageViewAnimado.setImageDrawable(imageView.getDrawable());
        //Como estava INVISIBLE, temos que passar para VISIBLE
        imageViewAnimado.setVisibility(View.VISIBLE);

        //Carrega a nossa animação
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animacao_escala01);
        //Seta na nossa View a animação
        imageViewAnimado.startAnimation(animation);
    }
}
