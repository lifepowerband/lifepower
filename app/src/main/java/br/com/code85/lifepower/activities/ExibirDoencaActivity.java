package br.com.code85.lifepower.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.code85.lifepower.R;
import br.com.code85.lifepower.adapter.ExpandableAdapter;
import br.com.code85.lifepower.helper.UsuarioService;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExibirDoencaActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "preferencias";
    private SharedPreferences sp;
    private Integer idUsuario;
    //private UsuarioDao usuarioDao;
    private List<String> listGroup;
    private HashMap<String, List<String>> listData;
    private ProgressView progressView;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_doenca);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaInformacoes();
            }
        });

        progressView  = (ProgressView)findViewById(R.id.circular_progress);
        progressView.setVisibility(View.GONE);

        //Recupera o id do usuário salvo nas preferências
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        idUsuario = sp.getInt("idUsuario", 0);

        inicializarRetrofit();

        expandableListView = (ExpandableListView)findViewById(R.id.expandablelistview);
    }

    private void inicializarRetrofit(){
        progressView.setVisibility(View.VISIBLE);
        progressView.start();
        //Montando um obejto retrofit passando a url base e o converson de Json
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsuarioService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // retorna uma classe que implementa UsuarioService
        UsuarioService usuarioService = retrofit.create(UsuarioService.class);
        Call<Usuario> requestUsuario = usuarioService.buscarUsuarioPorId(1);

        //Chamada assíncrona
        requestUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    //Requisição retornou com sucesso
                    Usuario usuario = response.body();
                    inicializarLista(usuario);
                    progressView.stop();
                    progressView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                //Erro de rede
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    private void inicializarLista(Usuario usuario){
        listGroup = new ArrayList<String>();
        listData = new HashMap<String,List<String>>();

        //Grupos
        listGroup.add("Doenças");
        listGroup.add("Alergias");

        //Filhos
        //Logo abaixo, eu estou adicionando as listas aos seus devidos grupos
        List<String> listaDoencas = usuario.getDoencas();
        listData.put(listGroup.get(0),listaDoencas);

        List<String> listaAlergias = usuario.getAlergias();
        listData.put(listGroup.get(1),listaAlergias);

        expandableListView.setAdapter(new ExpandableAdapter(this,listGroup,listData));

        //Mantém os grupos sempre abertos
        expandableListView.expandGroup(0);
        expandableListView.expandGroup(1);

        //Clique nos filhos
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),"Grupo: " + groupPosition + "| Item: " + childPosition , Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void irParaInformacoes(){
        Intent intent = new Intent(this,InformacoesActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

    }

}
