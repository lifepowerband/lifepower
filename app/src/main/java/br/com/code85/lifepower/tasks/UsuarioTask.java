package br.com.code85.lifepower.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import br.com.code85.lifepower.model.Usuario;

/**
 * Created by Daniel on 25/01/2017.
 */

public class UsuarioTask extends AsyncTask<String,String,String>{
    private TextView textViewNome;
    private TextView textViewIdade;
    private TextView textViewRg;
    private TextView textViewTelefone;
    private TextView textViewSexo;
    private TextView textViewRua;
    private TextView textViewBairro;
    private TextView textViewTipoSangue;
    private ProgressDialog dialog;
    private Context context;

    public UsuarioTask(Context context, TextView textViewNome, TextView textViewIdade, TextView textViewRg, TextView textViewTelefone,
                       TextView textViewSexo, TextView textViewRua, TextView textViewBairro, TextView textViewTipoSangue){
        this.context = context;
        this.textViewNome = textViewNome;
        this.textViewIdade = textViewIdade;
        this.textViewRg = textViewRg;
        this.textViewTelefone = textViewTelefone;
        this.textViewSexo = textViewSexo;
        this.textViewRua = textViewRua;
        this.textViewBairro = textViewBairro;
        this.textViewTipoSangue = textViewTipoSangue;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Aguarde", "Carregando dados");

    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        Usuario usuario = null;


        return null;
    }


    //Recebe como parâmetro o retorno do doInbackground
    protected void onPostExecute(){

    }
}
