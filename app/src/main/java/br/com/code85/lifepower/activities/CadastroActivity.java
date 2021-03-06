package br.com.code85.lifepower.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import br.com.code85.lifepower.R;
import br.com.code85.lifepower.helper.UsuarioService;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {
    Usuario usuario;
    public static final String PREFS_NAME = "preferencias";
    private EditText editTextNome;
    private EditText editTextIdade;
    private EditText editTextRg;
    private EditText editTextTelefone;
    private Spinner spSexo;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextComplemento;
    private EditText editTextBairro;
    private Spinner spTipoSangue;
    private String sexo;
    private String tipoSangue;
    private EditText editTextFamiliar1;
    private EditText editTextTelefoneFamiliar1;
    private EditText editTextFamiliar2;
    private EditText editTextTelefoneFamiliar2;
    private SharedPreferences sp;
    private Integer idUsuario;
    private ProgressView progressView;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

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

        editTextNome = (EditText) findViewById(R.id.nome);
        editTextIdade = (EditText) findViewById(R.id.idade);
        editTextRg = (EditText) findViewById(R.id.rg);
        editTextTelefone = (EditText) findViewById(R.id.telefones);
        spSexo = (Spinner) findViewById(R.id.sp_sexo);
        editTextRua = (EditText) findViewById(R.id.rua);
        editTextNumero = (EditText) findViewById(R.id.numero);
        editTextComplemento = (EditText) findViewById(R.id.complemento);
        editTextBairro = (EditText) findViewById(R.id.bairro);
        spTipoSangue = (Spinner) findViewById(R.id.sp_tipo_sanguineo);
        editTextFamiliar1 = (EditText) findViewById(R.id.nomeFamiliar1);
        editTextTelefoneFamiliar1 = (EditText) findViewById(R.id.telefoneFamiliar1);
        editTextFamiliar2 = (EditText) findViewById(R.id.nomeFamiliar2);
        editTextTelefoneFamiliar2 = (EditText) findViewById(R.id.telefoneFamiliar2);


        inicializarRetrofit();
        carregarSpinnerSexo();
        carregarSpinnerTipoSangue();

    }

    private void inicializarRetrofit(){
        progressView.setVisibility(View.VISIBLE);
        progressView.start();
        //Montando um obejto retrofit passando a url base e o converson de Json
        retrofit = new Retrofit.Builder()
                .baseUrl(UsuarioService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Pega o id do usuário salvo na sessão
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        idUsuario = sp.getInt("idUsuario", 0);

        // retorna uma classe que implementa UsuarioService
        UsuarioService usuarioService = retrofit.create(UsuarioService.class);
        Call<Usuario> requestUsuario = usuarioService.buscarUsuarioPorId(idUsuario);

        //Chamada assíncrona
        requestUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    //Requisição retornou com sucesso
                    Usuario usuario = response.body();
                    setarInformacoes(usuario);
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

    private void setarInformacoes(Usuario usuario){
        editTextNome.setText(usuario.getNome());
        editTextIdade.setText(usuario.getIdade() + "");
        editTextRg.setText(usuario.getRg());
        editTextTelefone.setText(usuario.getTelefone());
        //textViewSexo.setText(usuario.getSexo());
        editTextRua.setText(usuario.getRua());
        editTextBairro.setText(usuario.getBairro());
        editTextNumero.setText(usuario.getNumero()+"");
        editTextComplemento.setText(usuario.getComplemento());
        //textViewTipoSangue.setText(usuario.getTipoSangue());
        editTextFamiliar1.setText(usuario.getNome1());
        editTextTelefoneFamiliar1.setText(usuario.getNumero1());
        editTextFamiliar2.setText(usuario.getNome2());
        editTextTelefoneFamiliar2.setText(usuario.getNumero2());
    }


    private void carregarSpinnerSexo(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.array_sexo,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapter);

        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo = String.valueOf(spSexo.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void carregarSpinnerTipoSangue(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.array_tipo_sanguineo,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSangue.setAdapter(adapter);

        spTipoSangue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSangue = String.valueOf(spTipoSangue.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void atualizarUsuario(){

        List<String> doencas = new ArrayList<>() ;
        List<String> alergias = new ArrayList<>();

        UsuarioService usuarioService = retrofit.create(UsuarioService.class);

        Usuario usuario = new Usuario();
        usuario.setNome(editTextNome.getText().toString());
        usuario.setIdade(Integer.valueOf(editTextIdade.getText().toString()));
        usuario.setRg(editTextRg.getText().toString());
        usuario.setTelefone(editTextTelefone.getText().toString());
        usuario.setSexo(sexo);
        usuario.setRua(editTextRua.getText().toString());
        usuario.setNumero(Integer.valueOf(editTextNumero.getText().toString()));
        usuario.setComplemento(editTextComplemento.getText().toString());
        usuario.setBairro(editTextBairro.getText().toString());
        usuario.setTipoSangue(tipoSangue);
        //usuario.setEmail(editTextEmail.getText().toString());
        //usuario.setSenha(editTextSenha.getText().toString());
        usuario.setNome1(editTextFamiliar1.getText().toString());
        usuario.setNumero1(editTextTelefoneFamiliar1.getText().toString());
        usuario.setNome2(editTextFamiliar2.getText().toString());
        usuario.setNumero2(editTextTelefoneFamiliar2.getText().toString());
        usuario.setDoencas(doencas);
        usuario.setAlergias(alergias);

        Call<Boolean> requestUsuario = usuarioService.inserirUsuario(usuario);

        requestUsuario.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    if(response.body()){
                        Toast.makeText(getApplicationContext(), "Usuário criado com sucesso", Toast.LENGTH_LONG).show();
                        irParaInformacoes();

                    }else{
                        Toast.makeText(getApplicationContext(), "Erro ao criar usuário", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

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
