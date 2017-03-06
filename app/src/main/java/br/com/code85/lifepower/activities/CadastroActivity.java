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

import br.com.code85.lifepower.R;
//import br.com.code85.lifepower.dao.UsuarioDao;
import br.com.code85.lifepower.model.Usuario;

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
    private SharedPreferences sp;
    private Integer idUsuario;


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


        //Recupera o id do usuário salvo nas preferências
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        idUsuario = sp.getInt("idUsuario", 0);

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


        carregarSpinnerSexo();
        carregarSpinnerTipoSangue();

    }

    /*public void salvarDados(View view){

        try {
            usuario.setNome(editTextNome.getText().toString());
            usuario.setIdade(Integer.parseInt(editTextIdade.getText().toString()));
            usuario.setRg(editTextRg.getText().toString());
            usuario.setTelefone(editTextTelefone.getText().toString());
            usuario.setSexo(sexo);
            usuario.setRua(editTextRua.getText().toString());
            usuario.setNumero(Integer.parseInt(editTextNumero.getText().toString()));
            usuario.setComplemento(editTextComplemento.getText().toString());
            usuario.setBairro(editTextBairro.getText().toString());
            usuario.setTipoSangue(tipoSangue);
            usuarioDao.update(usuario);

            Toast.makeText(this, "Perfil cadastrado com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro!", Toast.LENGTH_LONG).show();
        }

        irParaInformacoes();
    }*/

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

    private void irParaInformacoes(){
        Intent intent = new Intent(this,InformacoesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
