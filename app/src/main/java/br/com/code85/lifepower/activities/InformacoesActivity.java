package br.com.code85.lifepower.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.rey.material.widget.ProgressView;

import java.util.List;

import br.com.code85.lifepower.R;
//import br.com.code85.lifepower.dao.UsuarioDao;
import br.com.code85.lifepower.helper.UsuarioService;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InformacoesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewNome;
    private TextView textViewIdade;
    private TextView textViewRg;
    private TextView textViewTelefone;
    private TextView textViewSexo;
    private TextView textViewRua;
    private TextView textViewBairro;
    private TextView textViewTipoSangue;
    public static final String PREFS_NAME = "preferencias";
    private SharedPreferences sp;
    private Integer idUsuario;
    private View view;
    private ProgressView progressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressView  = (ProgressView)findViewById(R.id.circular_progress);
        progressView.setVisibility(View.GONE);

        textViewNome = (TextView) findViewById(R.id.nome);
        textViewIdade = (TextView) findViewById(R.id.idade);
        textViewRg = (TextView) findViewById(R.id.rg);
        textViewTelefone = (TextView) findViewById(R.id.telefones);
        textViewSexo = (TextView) findViewById(R.id.sexo);
        textViewRua = (TextView) findViewById(R.id.rua);
        textViewBairro = (TextView) findViewById(R.id.bairro);
        textViewTipoSangue = (TextView) findViewById(R.id.tipo_sanguineo);

        inicializarRetrofit();
     }

    private void inicializarAccountHeader(Usuario usuario){
        // Criando o AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background)
                .addProfiles(
                        new ProfileDrawerItem().withName(usuario.getNome()).withEmail(usuario.getEmail()).withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Cadastrar dados");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Adicionar alergias/doenças");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Exibir alergias/doenças");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Monitoramento");

        //Criando o navigation drawer e passando o AccountHeader Result
        new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2, item3, item4,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position){
                            case 1:
                                cadastrar(view);
                                break;
                            case 2:
                                adicionarDoencaAlergia(view);
                                break;
                            case 3:
                                exibirDoencaAlergia(view);
                                break;

                        }

                        return false;

                    }
                })
                .build();
    }

    private void inicializarRetrofit(){
        progressView.setVisibility(View.VISIBLE);
        progressView.start();
        //Montando um obejto retrofit passando a url base e o converson de Json
        Retrofit retrofit = new Retrofit.Builder()
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

                    inicializarAccountHeader(usuario);
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
        textViewNome.setText(usuario.getNome());
        textViewIdade.setText(usuario.getIdade() + "");
        textViewRg.setText(usuario.getRg());
        textViewTelefone.setText(usuario.getTelefone());
        textViewSexo.setText(usuario.getSexo());
        textViewRua.setText(usuario.getRua());
        textViewBairro.setText(usuario.getBairro());
        textViewTipoSangue.setText(usuario.getTipoSangue());
    }


    public void cadastrar(View view){
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);
    }

    public void adicionarDoencaAlergia(View view){
        Intent intent = new Intent(this,AdicionarDoencaActivity.class);
        startActivity(intent);
    }

    public void exibirDoencaAlergia(View view){
        Intent intent = new Intent(this,ExibirDoencaActivity.class);
        startActivity(intent);
    }

    //Se o usuário fizer login normalmente
/*    private void buscarUsuarioPeloId(){
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        idUsuario = sp.getInt("idUsuario", 0);
        try {
            usuario = usuarioDao.queryForId((int) (long) idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Se o usuário fizer o login pelo Facebook
    private void buscarUsuarioPeloEmailDoFacebook(){

        //Recupera o email salvo na sessão do app
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String emailFacebook = sp.getString("email", "");
        System.out.println("Email do Facebook: " + emailFacebook);

        try {
            QueryBuilder<Usuario, Integer> queryBuilder = usuarioDao.queryBuilder();
            Where<Usuario, Integer> where = queryBuilder.where();
            where.eq("email", emailFacebook);
            PreparedQuery<Usuario> preparedQuery = queryBuilder.prepare();
            List<Usuario> listaUsuario = usuarioDao.query(preparedQuery);

            if(listaUsuario.size() >0){
                usuario = listaUsuario.get(0);
            }

            if(usuario == null){
                Toast.makeText(this, "Erro ao buscar os dados do usuário!", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    private void chamarTelaDeLogin(){
        Intent intent = new Intent(this,MainActivity.class);
        //Essas flags fazem com que minha tela seja a única em execução
        // se acontecer de o usuário apertar o botão voltar, a aplicação não vai retornar para a tela anterior
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*public void logout(View view){

        //Se uma sessão do facebook estiver ativa, é feito o logout e a tela volta pro login
            LoginManager.getInstance().logOut();
            chamarTelaDeLogin();
        }
        if(AccessToken.getCurrentAccessToken() != null) {

    }*/}
