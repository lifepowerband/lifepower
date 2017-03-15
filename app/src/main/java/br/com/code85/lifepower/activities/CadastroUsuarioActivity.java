package br.com.code85.lifepower.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class CadastroUsuarioActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextRepitaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaInformacoes();
            }
        });


        editTextEmail = (EditText) findViewById(R.id.email);
        editTextSenha = (EditText) findViewById(R.id.senha);
        editTextRepitaSenha = (EditText) findViewById(R.id.repitaSenha);
    }

    public void cadastrarUsuario(View view){

        List<String> doencas = new ArrayList<>() ;
        List<String> alergias = new ArrayList<>();

        if(editTextSenha.getText().toString().equals(editTextRepitaSenha.getText().toString())){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UsuarioService.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UsuarioService usuarioService = retrofit.create(UsuarioService.class);

            Usuario usuario = new Usuario();
            usuario.setNome("");
            usuario.setIdade(0);
            usuario.setRg("");
            usuario.setTelefone("");
            usuario.setSexo("");
            usuario.setRua("");
            usuario.setNumero(0);
            usuario.setComplemento("");
            usuario.setBairro("");
            usuario.setTipoSangue("");
            usuario.setEmail(editTextEmail.getText().toString());
            usuario.setSenha(editTextSenha.getText().toString());
            usuario.setNome1("");
            usuario.setNumero1("");
            usuario.setNome2("");
            usuario.setNumero2("");
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
                            chamarTelaPrincipal();

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

        }else{
            Toast.makeText(getApplicationContext(), "As senhas não combinam. Por favor, tente novamente!", Toast.LENGTH_LONG).show();
        }

    }

    public void chamarTelaPrincipal(){
        Intent intent = new Intent(this,MainActivity.class);
        //Essas flags fazem com que minha tela principal seja a única em execução
        // se acontecer de o usuário apertar o botão voltar, a aplicação não vai retornar pra tela de login
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
