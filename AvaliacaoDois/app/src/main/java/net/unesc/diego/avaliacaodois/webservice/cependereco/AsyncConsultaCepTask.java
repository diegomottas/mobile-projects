package net.unesc.diego.avaliacaodois.webservice.cependereco;

import android.os.AsyncTask;

public class AsyncConsultaCepTask extends AsyncTask<String, Integer, CepEndereco> {

    private OnAsyncTaskCompleted listener;

    public AsyncConsultaCepTask(OnAsyncTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected CepEndereco doInBackground(String... params) {
        return CepEndereco.getCepEndereco(params[0]);
    }

    @Override
    protected void onPostExecute(CepEndereco cepEndereco) {
        listener.onTaskCompleted();
    }
}
