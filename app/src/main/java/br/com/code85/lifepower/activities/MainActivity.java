package br.com.code85.lifepower.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.code85.lifepower.R;
//import br.com.code85.lifepower.dao.UsuarioDao;
import br.com.code85.lifepower.helper.UsuarioService;
import br.com.code85.lifepower.model.Login;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //private UsuarioDao usuarioDao;
    public static final String PREFS_NAME = "preferencias";
    private EditText editTextEmail;
    private EditText editTextSenha;
    private SharedPreferences sp;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String nomeFacebook;
    private String emailFacebook;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(UsuarioService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Login com o Facebook
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        loginButton.setReadPermissions(Arrays.asList("email" , "public_profile"));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Sucesso no Login
                obterDadosDoFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                //Login cancelado
                Toast.makeText(getApplicationContext(), "Login cancelado!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                //Erro no login
                Toast.makeText(getApplicationContext(), "Erro ao fazer login!", Toast.LENGTH_LONG).show();

            }
        });

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextSenha = (EditText) findViewById(R.id.senha);

        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    //Obtem e salva os alguns dados do facebook no banco
    private void obterDadosDoFacebook(AccessToken accessToken){
        //Para obter o email, precisamos de uma consulta a Graph API
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if(response != null){
                    try {
                        nomeFacebook = object.getString("name");
                        emailFacebook = object.getString("email");
                        fazerLoginPorEmailFacebook(nomeFacebook,emailFacebook);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Bundle parameters = new Bundle();
        // Solicitamos o email e dados
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }


    public void fazerLogin(View view){

        // retorna uma classe que implementa UsuarioService
        UsuarioService usuarioService = retrofit.create(UsuarioService.class);
        //Cria o objeto Login para ser comparado com o do servidor
        Login login = new Login(editTextEmail.getText().toString(),editTextSenha.getText().toString());
        Call<Usuario> requestUsuario = usuarioService.login(login);

        //Chamada assíncrona
        requestUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    //Requisição retornou com sucesso
                    Usuario usuario = response.body();

                    if(usuario != null){
                        //Responsável por guardar a sessão do usuário
                        SharedPreferences.Editor editor = sp.edit();
                        //primeiro parâmetro é o nome da preferência, o segundo é caso venha vazio, é setado o ""
                        editor.putInt("idUsuario", usuario.getId());
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "Seja bem vindo " + usuario.getNome(), Toast.LENGTH_LONG).show();
                        chamarTelaPrincipal();

                    } else{
                        Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                //Erro de rede
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void fazerLoginPorEmailFacebook(final String nome, final String email){

        final UsuarioService usuarioService = retrofit.create(UsuarioService.class);
        Login login = new Login(email);
        Call<Usuario> requestUsuario = usuarioService.loginFacebook(login);

        //Chamada assíncrona
        requestUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    //Requisição retornou com sucesso
                    Usuario usuario = response.body();

                    //Se o usuário já existir, faz login normalmente
                    if(usuario != null){
                        //Responsável por guardar a sessão do usuário
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("idUsuario", usuario.getId());
                        editor.commit();

                        if(nome != null){
                            Toast.makeText(getApplicationContext(), "Seja bem vindo " + nome, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Seja bem vindo", Toast.LENGTH_LONG).show();
                        }

                        chamarTelaPrincipal();

                    } else{
                        //Caso o usuário não exista, é criado um com o nome e email recebido do facebook
                        inserirUsuarioFacebook(email,nome);

                    }

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                //Erro de rede
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void inserirUsuarioFacebook(String email, final String nome){

        UsuarioService usuarioService = retrofit.create(UsuarioService.class);

        List<String> doencas = new ArrayList<>() ;
        List<String> alergias = new ArrayList<>();

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setIdade(0);
        novoUsuario.setRg("");
        novoUsuario.setTelefone("");
        novoUsuario.setSexo("");
        novoUsuario.setRua("");
        novoUsuario.setNumero(0);
        novoUsuario.setComplemento("");
        novoUsuario.setBairro("");
        novoUsuario.setTipoSangue("");
        novoUsuario.setEmail(email);
        novoUsuario.setSenha("");
        novoUsuario.setNome1("");
        novoUsuario.setNumero1("");
        novoUsuario.setNome2("");
        novoUsuario.setNumero2("");
        novoUsuario.setDoencas(doencas);
        novoUsuario.setAlergias(alergias);

        Call<Usuario> requestUsuario = usuarioService.inserirUsuarioFacebook(novoUsuario);

        requestUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    //Requisição retornou com sucesso
                    Usuario usuario = response.body();

                    if(usuario != null){
                        //Responsável por guardar a sessão do usuário
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("idUsuario", usuario.getId());
                        editor.commit();

                        if(nome != null){
                            Toast.makeText(getApplicationContext(), "Seja bem vindo " + nome, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Seja bem vindo", Toast.LENGTH_LONG).show();
                        }

                        chamarTelaPrincipal();

                    } else{
                        //Aqui vai o código pra salvar o email e nome do usuário
                        Toast.makeText(getApplicationContext(), "Erro!", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void chamarTelaPrincipal(){
        Intent intent = new Intent(this,InformacoesActivity.class);
        //Essas flags fazem com que minha tela principal seja a única em execução
        // se acontecer de o usuário apertar o botão voltar, a aplicação não vai retornar pra tela de login
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cadastrarUsuario(View view){
        Intent intent = new Intent(this,CadastroUsuarioActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Limpa as preferências depois que a aplicação é fechada
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

    }

}
