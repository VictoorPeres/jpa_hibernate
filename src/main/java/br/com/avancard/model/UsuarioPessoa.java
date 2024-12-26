package br.com.avancard.model;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "UsuarioPessoa.todos", query = "select u from UsuarioPessoa u"), /* Criando um select nomeado com NamedQuery */
        @NamedQuery(name = "UsuarioPessoa.buscaPorNome", query = "select u from UsuarioPessoa u where u.nome = :nome") /* Criando um select nomeado com NamedQuery */
})
public class UsuarioPessoa {
    @Id /* Esta anotação indica que o campo anotado é a chave primária da entidade. A chave primária é um identificador único para cada registro na tabela do banco de dados. */
    @GeneratedValue(strategy = GenerationType.AUTO) /* Esta anotação especifica como o valor da chave primária será gerado. O strategy define a estratégia de geração de IDs. Aqui, GenerationType.AUTO indica que o provedor de persistência deve escolher a estratégia apropriada para gerar os valores da chave primária. Dependendo do banco de dados utilizado, isso pode significar o uso de uma sequência, um gerador de identidade ou outra abordagem. */
    @Column(name = "cd_usuario" ) /* Esta anotação é usada para especificar a coluna do banco de dados que corresponde ao campo da entidade. O atributo name indica o nome da coluna na tabela. */
    private long id;
    @Column(name = "nm_usuario")
    private String nome;
    private String sobrenome;
    private int idade;
    private String email;
    private String login;
    private String password;
    private String sexo;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE)
    private List<TelefonePessoa> telefones;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TelefonePessoa> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefonePessoa> telefones) {
        this.telefones = telefones;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "UsuarioPessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", idade=" + idade +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + password + '\'' +
                '}';
    }
}
