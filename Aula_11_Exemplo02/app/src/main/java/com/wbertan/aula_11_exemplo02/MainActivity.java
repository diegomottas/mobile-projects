package com.wbertan.aula_11_exemplo02;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Seta o título na ActionBar
        getSupportActionBar().setTitle("Título");
        getSupportActionBar().setSubtitle("SubTítulo");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_inserir:
                Toast.makeText(MainActivity.this, "Clicou em Inserir", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_deletar:
                Toast.makeText(MainActivity.this, "Clicou em Deletar", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
