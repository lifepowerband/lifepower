package br.com.code85.lifepower.helper;
import java.util.ArrayList;

import br.com.code85.lifepower.model.Login;
import br.com.code85.lifepower.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
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

    //Método para login
    @POST("usuario/entrar")
    Call<Usuario> login(@Body Login login);

    //Método para o pré-cadastro somente com email e senha
    @POST("usuario/inserir")
    Call<Boolean> inserirUsuario(@Body Usuario usuario);

    //Método para o pré-cadastro somente com email e senha
    /*@FormUrlEncoded
    @POST("usuario/inserir")
    Call<Boolean> loginPost(
            @Field("nome") String nome,
            @Field("idade") Integer idade,
            @Field("rg") String rg,
            @Field("telefone") String telefone,
            @Field("sexo") String sexo,
            @Field("rua") String rua,
            @Field("numero") Integer numero,
            @Field("complemento") String complemento,
            @Field("bairro") String bairro,
            @Field("tipo_sangue") String tipoSague,
            @Field("email") String email,
            @Field("senha") String senha,
            @Field("nome1") String nome1,
            @Field("numero1") String numero1,
            @Field("nome2") String nome2,
            @Field("numero2") String numero2,
            @Field("doencas[]") ArrayList<String> doencas,
            @Field("alergias[]") ArrayList<String> alergias
            );*/


    //Método para logar usando apenas o email do Facebook
    @POST("usuario/entrarfacebook")
    Call<Usuario> loginFacebook(@Body Login login);

}
