/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.*;

/**
 *
 * @author Ton
 */
@Entity
abstract class Usuario extends Pessoa {

    @Column(nullable = false, unique = true)
    private String usuario;
    @Column(nullable = false)
    private String senha;

    public Usuario(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public Usuario(String usuario, String senha, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(id, cpf, nome, fone, uf, cidade, logradouro, numero);
        this.usuario = usuario;
        this.senha = senha;
    }

    public Usuario() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
