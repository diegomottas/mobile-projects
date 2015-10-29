package com.wbertan.aula_12_exemplo01.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wbertan on 18/05/15.
 */
public class HttpServlet {
    private volatile boolean fetchingUrl = true;
    private String data = null;

    public static String call(String urlServlet){
        HttpServlet httpServlet = new HttpServlet();

        httpServlet.fetchUrl(urlServlet);
        while(httpServlet.fetchingUrl);

        return httpServlet.getData();
    }

    public void fetchUrl(final String urlServlet){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlServlet);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    data = convertStreamToString(stream);
                    stream.close();

                    fetchingUrl = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String getData(){
        return data;
    }
}
