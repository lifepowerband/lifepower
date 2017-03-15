package br.com.code85.lifepower.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("idade")
    @Expose
    private Integer idade;

    @SerializedName("rg")
    @Expose
    private String rg;

    @SerializedName("telefone")
    @Expose
    private String telefone;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("rua")
    @Expose
    private String rua;

    @SerializedName("numero")
    @Expose
    private Integer numero;

    @SerializedName("complemento")
    @Expose
    private String complemento;

    @SerializedName("bairro")
    @Expose
    private String bairro;

    @SerializedName("tipo_sangue")
    @Expose
    private String tipoSangue;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("senha")
    @Expose
    private String senha;

    @SerializedName("nome1")
    @Expose
    private String nome1;

    @SerializedName("numero1")
    @Expose
    private String numero1;

    @SerializedName("numero2")
    @Expose
    private String numero2;

    @SerializedName("nome2")
    @Expose
    private String nome2;

    @SerializedName("doencas")
    @Expose
    private List<String> doencas = null;

    @SerializedName("alergias")
    @Expose
    private List<String> alergias = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Usuario() {
    }

    public Usuario(Integer id, String nome, Integer idade, String rg, String telefone, String sexo, String rua, Integer numero, String complemento, String bairro, String tipoSangue, String email, String senha,String nome1, String numero1, String numero2, String nome2, List<String> doencas, List<String> alergias) {
        super();
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.rg = rg;
        this.telefone = telefone;
        this.sexo = sexo;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.tipoSangue = tipoSangue;
        this.email = email;
        this.senha = senha;
        this.nome1 = nome1;
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.nome2 = nome2;
        this.doencas = doencas;
        this.alergias = alergias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTipoSangue() {
        return tipoSangue;
    }

    public void setTipoSangue(String tipoSangue) {
        this.tipoSangue = tipoSangue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome1() {
        return nome1;
    }

    public void setNome1(String nome1) {
        this.nome1 = nome1;
    }

    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getNumero2() {
        return numero2;
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }

    public String getNome2() {
        return nome2;
    }

    public void setNome2(String nome2) {
        this.nome2 = nome2;
    }

    public List<String> getDoencas() {
        return doencas;
    }

    public void setDoencas(List<String> doencas) {
        this.doencas = doencas;
    }

    public List<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

}