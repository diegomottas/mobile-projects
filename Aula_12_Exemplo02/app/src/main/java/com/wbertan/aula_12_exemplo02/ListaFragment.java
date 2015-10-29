package com.wbertan.aula_12_exemplo02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wbertan.aula_12_exemplo02.modelo.Pensamento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbertan on 19/05/15.
 */
public class ListaFragment extends Fragment implements AdapterView.OnItemClickListener {

    //Método que é chamado quando este Fragment for solicitado
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Infla o layout deste Fragment
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        //Gera os nossos valores de testes
        List<Pensamento> lista = new ArrayList<>();
        lista.add(new Pensamento("Shakespeare", R.drawable.shakespeare, "É mais fácil obter o que se deseja com um sorriso do que à ponta da espada."));
        lista.add(new Pensamento("Charles Chaplin", R.drawable.charles, "A persistência é o caminho do êxito."));
        lista.add(new Pensamento("Clarice Lispector", R.drawable.clarice, "Que ninguém se engane, só se consegue a simplicidade através de muito trabalho."));

        //Recupera a ListView e cria o nosso CustomAdapter
        ListView lv_resultado = (ListView) view.findViewById(R.id.lv_resultado);
        ListAdapter customAdapter = new ListAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);

        //Seta o ListAdapter na nossa ListView
        lv_resultado.setAdapter(customAdapter);

        //Seta a ação de clicar na nossa ListView
        lv_resultado.setOnItemClickListener(this);
        //Retorna a view que deverá ser desenhada na tela
        return view;
    }

    //Método que sobrescreve o onItemClick através do implements AdapterView.OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Recupera a ListView
        ListView lv_resultado = (ListView) view.findViewById(R.id.lv_resultado);

        //Pega o Objeto na posição
        //Pensamento pensamento = (Pensamento) lv_resultado.getItemAtPosition(position);

        //Tenta pegar o Fragment DetalheFragment através do FragmentManager e pelo id do fragment
        DetalheFragment detalheFragment = (DetalheFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_detalhe);
        //Verifica se encontrou o fragment ou não
        if (detalheFragment != null && detalheFragment.isInLayout()) {
            //Se encontrou, chama o método do fragment que seta o Pensamento
            //detalheFragment.set(pensamento);
        } else {
            //Abre nova Activity para mostrar o Pensamento.
            Intent intent = new Intent(getActivity(), PensamentoActivity.class);
            //intent.putExtra("nm_pensador", pensamento.getNm_pensador());
            //intent.putExtra("ds_pensamento", pensamento.getDs_pensamento());
            //intent.putExtra("id_foto_pensador", pensamento.getId_foto_pensador());
            //startActivity(intent);
        }
    }

    //CustomAdapter para representar a Lista de Objetos do Tipo Pensamento
    private class ListAdapter extends ArrayAdapter<Pensamento> {

        public ListAdapter(Context context, int resource, List<Pensamento> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflater;
                layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
            }

            final Pensamento pensamento = getItem(position);

            if (pensamento != null) {
                TextView tv_generico = (TextView) view.findViewById(android.R.id.text1);
                tv_generico.setText(pensamento.getNm_pensador());
            }
            return view;

        }
    }
}