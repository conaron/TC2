/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Ton
 */
@Entity
public class Adm extends Usuario implements Serializable {

    @Column
    private String cargo;

    public Adm() {
    }

    public Adm(String cargo, String usuario, String senha) {
        super(usuario, senha);
        this.cargo = cargo;
    }

    public Adm(String cargo, String usuario, String senha, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(usuario, senha, id, cpf, nome, fone, uf, cidade, logradouro, numero);
        this.cargo = cargo;
    }

    public Adm(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String toQuery() {
        if (this.getId() > 0) {
            return "SELECT u FROM " + this.getClass().getName() + " u WHERE u.id = " + this.getId();
        }
        if (this.getNome() == null) {
            this.setNome("");
        }
        return "SELECT u FROM " + this.getClass().getName() + " u"
                + " WHERE u.nome like '%" + this.getNome() + "%'";
    }

}
