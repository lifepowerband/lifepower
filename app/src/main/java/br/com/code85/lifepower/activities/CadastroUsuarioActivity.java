package br.com.code85.lifepower.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import br.com.code85.lifepower.R;
import br.com.code85.lifepower.model.Usuario;

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
