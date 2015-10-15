package com.wbertan.aula_10_exemplo03.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbertan.aula_10_exemplo03.R;

/**
 * Created by wbertan on 05/05/15.
 */
public class PlacarView extends LinearLayout {

    //Parametros do layout
    private String   time1 = "";
    private String   time2 = "";
    private Drawable logo_time1;
    private Drawable logo_time2;

    //Componentes do nosso CustomView
    private TextView tv_time1;
    private TextView tv_time2;
    private ImageView iv_time1;
    private ImageView iv_time2;

    //Construtores
    public PlacarView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_placar, this);
    }

    public PlacarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public PlacarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    //Método que inicia e monta o nosso CustomView
    private void initViews(Context context, AttributeSet attrs) {
        //Recupera os parametros do layout
        TypedArray parametrosLayout = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PlacarView, 0, 0);

        try {
            // Pega os parametros
            time1      = parametrosLayout.getString(R.styleable.PlacarView_time1);
            time2      = parametrosLayout.getString(R.styleable.PlacarView_time2);
            logo_time1 = parametrosLayout.getDrawable(R.styleable.PlacarView_logo_time1);
            logo_time2 = parametrosLayout.getDrawable(R.styleable.PlacarView_logo_time2);
        } finally {
            //Como estes métodos consomem muito recurso, o quanto antes temos que liberá-lo
            parametrosLayout.recycle();
        }

        //Infla o layout que criamos para este componente
        LayoutInflater.from(context).inflate(R.layout.widget_placar, this);

        //Recupera e seta os valores nos componentes
        tv_time1 = (TextView) this.findViewById(R.id.tv_time1);
        tv_time1.setText(time1);

        tv_time2 = (TextView) this.findViewById(R.id.tv_time2);
        tv_time2.setText(time2);

        iv_time1 = (ImageView) this.findViewById(R.id.iv_time1);
        iv_time1.setImageDrawable(logo_time1);

        iv_time2 = (ImageView) this.findViewById(R.id.iv_time2);
        iv_time2.setImageDrawable(logo_time2);
    }

    //Getter's e Setter's dos componentes que estão dentro do nosso CustomView
    public String getTime1(){
        return time1;
    }

    public void setTime1(String time1){
        this.time1 = time1;
        //Também temos que atualizar no componente
        if(tv_time1 != null){
            tv_time1.setText(time1);
        }
    }

    public String getTime2(){
        return time2;
    }

    public void setTime2(String time2){
        this.time2 = time2;
        //Também temos que atualizar no componente
        if(tv_time2 != null){
            tv_time2.setText(time2);
        }
    }

    public Drawable getLogo_time1(){
        return logo_time1;
    }

    public void setLogo_time1(int id_logo_time1){
        //Como recebeu apenas o id do logo, temos que recuperar o Drawable
        this.logo_time1 = getResources().getDrawable(id_logo_time1);
        //Também temos que atualizar no componente
        if(iv_time1 != null){
            iv_time1.setImageDrawable(logo_time1);
        }
    }

    public Drawable getLogo_time2(){
        return logo_time1;
    }

    public void setLogo_time2(int id_logo_time2){
        //Como recebeu apenas o id do logo, temos que recuperar o Drawable
        this.logo_time2 = getResources().getDrawable(id_logo_time2);
        //Também temos que atualizar no componente
        if(iv_time2 != null){
            iv_time2.setImageDrawable(logo_time2);
        }
    }
}
