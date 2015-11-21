package com.example.diego.avaliacaotres;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diego.avaliacaotres.database.Banco;
import com.example.diego.avaliacaotres.model.ItemListaCompras;
import com.terlici.dragndroplist.DragNDropCursorAdapter;
import com.terlici.dragndroplist.DragNDropListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banco banco;
    private EditText txtDescricao;
    private Button btnAdd;
    private DragNDropCursorAdapter cursorAdapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        banco = new Banco(getApplicationContext());
        banco.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursor = banco.query(ItemListaCompras.TABLE_NAME);

        DragNDropListView list = (DragNDropListView)findViewById(android.R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemListaCompras itemListaCompras = ItemListaCompras.get(cursor, position);
                List<ItemListaCompras> list1 = ItemListaCompras.getList(banco);
                if(itemListaCompras != null){
                    if(ItemListaCompras.Status.ABERTO.getValue().equals(itemListaCompras.getStatus())){
                        itemListaCompras.setStatus(ItemListaCompras.Status.FECHADO.getValue());
                    }else if(ItemListaCompras.Status.FECHADO.getValue().equals(itemListaCompras.getStatus())){
                        itemListaCompras.setStatus(ItemListaCompras.Status.ABERTO.getValue());
                    }
                }
                ItemListaCompras.insertOrUpdate(banco, itemListaCompras);
                recarregarCursor();
            }
        });

        cursorAdapter = new DragNDropCursorAdapter(getApplicationContext(),
                R.layout.drag_n_drop_row_layout,
                cursor,
                new String[]{ItemListaCompras.Colunas.DESCRICAO.getDescricao()},
                new int[]{R.id.text},
                R.id.handler){
            @Override
            public View getView(int position, View view, ViewGroup group) {
                view = super.getView(getmPosition(position), view, group);

                ItemListaCompras itemListaCompras = ItemListaCompras.get(cursor, position);

                TextView tvText = (TextView) view.findViewById(R.id.text);
                if(ItemListaCompras.Status.FECHADO.getValue().equals(itemListaCompras.getStatus())){
                    tvText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    tvText.setPaintFlags(tvText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

                return view;
            }
        };

        list.setDragNDropAdapter(cursorAdapter);

        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListaCompras itemListaCompras = new ItemListaCompras();
                itemListaCompras.setDescricao(txtDescricao.getText().toString());

                ItemListaCompras.insert(banco, itemListaCompras);

                recarregarCursor();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_clear:
                limpar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void limpar(){
        List<String> ids = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                int status = cursor.getInt(ItemListaCompras.Colunas.STATUS.getIndex());
                if(ItemListaCompras.Status.FECHADO.getValue().equals(status)){
                    Integer id = cursor.getInt(ItemListaCompras.Colunas.ID.getIndex());
                    ids.add(id.toString());
                }
            }while(cursor.moveToNext());
        }
        if(!ids.isEmpty()){
            ItemListaCompras.delete(banco, ids);
            recarregarCursor();
        }
    }

    private void recarregarCursor(){
        cursor.close();
        cursor = banco.query(ItemListaCompras.TABLE_NAME);
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }
}
