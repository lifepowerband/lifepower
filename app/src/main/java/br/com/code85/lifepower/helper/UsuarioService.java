package br.com.code85.lifepower.helper;

import java.util.List;

import br.com.code85.lifepower.model.Login;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Daniel on 25/01/2017.
 */

public interface UsuarioService {
    public static final String URL_BASE = "http://ec2-52-67-230-226.sa-east-1.compute.amazonaws.com:8080/";

    //é delclarada a assinatura do método que terá como retorno um usuário
    //A busca é feita pelo id
    @GET("usuario/get/{id}")
    Call<Usuario> buscarUsuarioPorId(@Path("id") Integer id);

    @POST("usuario/entrar")
    Call<Usuario> fazerLogin(@Body Login login);
}
