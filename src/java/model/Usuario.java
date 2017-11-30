package model;

import java.util.Objects;
import javax.persistence.*;

/**
 * @author Ton
 */
@Entity
abstract class Usuario extends Pessoa {

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(id, cpf, nome, fone, uf, cidade, logradouro, numero);
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha, int id, String nome) {
        super(id, nome);
        this.email = email;
        this.senha = senha;
    }


    public Usuario() {
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

}
