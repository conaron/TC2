package model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Ton
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Adm.0", query = "SELECT u FROM Adm u")
    ,
    @NamedQuery(name = "Adm.1", query = "SELECT u FROM Adm u WHERE u.nome LIKE '%:nome%'")
    ,
    @NamedQuery(name = "Adm.2", query = "SELECT u FROM Adm u WHERE u.cpf LIKE '%:cpf%'"),})

@ManagedBean

public class Adm extends Usuario implements Serializable {

    @Column
    private String cargo;

    public Adm() {
    }

    public Adm(String cargo, String email, String senha, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(email, senha, id, cpf, nome, fone, uf, cidade, logradouro, numero);
        this.cargo = cargo;
    }

    public Adm(int id, String email, String senha, String nome) {
        super(email, senha, id, nome);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
