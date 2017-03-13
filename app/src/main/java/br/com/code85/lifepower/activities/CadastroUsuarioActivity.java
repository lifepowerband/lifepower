package br.com.code85.lifepower.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.code85.lifepower.R;
import br.com.code85.lifepower.helper.UsuarioService;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroUsuarioActivity extends AppCompatActivity {
    Usuario usuario;
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

    public void loginPost(View view){

        if(editTextSenha.getText().toString().equals(editTextRepitaSenha.getText().toString())){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UsuarioService.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UsuarioService usuarioService = retrofit.create(UsuarioService.class);

            Call<Boolean> requestUsuario = usuarioService.loginPost("login",editTextEmail.getText().toString(),editTextSenha.getText().toString());

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
        Intent intent = new Intent(this,InformacoesActivity.class);
        //Essas flags fazem com que minha tela principal seja a única em execução
        // se acontecer de o usuário apertar o botão voltar, a aplicação não vai retornar pra tela de login
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*public void salvarUsuario(View view){

        if(editTextSenha.getText().toString().equals(editTextRepitaSenha.getText().toString())){
            try {
                usuario.setEmail(editTextEmail.getText().toString());
                //usuario.setSenha(editTextSenha.getText().toString());
                usuarioDao.create(usuario);

                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{

            Toast.makeText(this, "As duas senhas estão diferentes!", Toast.LENGTH_LONG).show();

        }
    }*/

    private void irParaInformacoes(){
        Intent intent = new Intent(this,InformacoesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}
