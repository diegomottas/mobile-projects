package com.wbertan.aula_10_exemplo01;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtEndereco = (EditText) findViewById(R.id.endereco);
        Button btnIr = (Button) findViewById(R.id.ir);
        btnIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(txtEndereco.getText().toString());
            }
        });

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new InternalBrowser());
        webView.loadUrl("http://www.google.com.br");
    }

    public class InternalBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
