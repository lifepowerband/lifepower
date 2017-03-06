package br.com.code85.lifepower.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.code85.lifepower.R;
//import br.com.code85.lifepower.dao.UsuarioDao;
import br.com.code85.lifepower.model.Usuario;

public class AdicionarDoencaActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "preferencias";
    private SharedPreferences sp;
    private Integer idUsuario;
    private Usuario usuario;
    private Spinner spDoenca;
    private EditText editTextDoenca;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_doenca);

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

        spDoenca = (Spinner) findViewById(R.id.sp_doenca_alergia);
        editTextDoenca = (EditText) findViewById(R.id.nome_doenca_alergia);


        carregarSpinnerDoencas();
    }

    private void carregarSpinnerDoencas(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.array_doenca_alergia,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDoenca.setAdapter(adapter);

        spDoenca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo = String.valueOf(spDoenca.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    /*public void salvarDados(View view){

        try {
            doenca.setNome(editTextDoenca.getText().toString());
            doenca.setTipo(tipo);
            doenca.setUsuario(usuario);
            doencaDao.create(doenca);

            Toast.makeText(this, "Doença/Alergia cadastrada com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao cadastrar doença!", Toast.LENGTH_LONG).show();
        }

        cadastrarDoenca();

    }*/

    private void cadastrarDoenca(){
        Intent intent = new Intent(this,AdicionarDoencaActivity.class);
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
