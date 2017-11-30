package model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Ton
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Professor.0", query = "SELECT u FROM Professor u")
    ,
    @NamedQuery(name = "Professor.1", query = "SELECT u FROM Professor u WHERE u.nome LIKE '%:nome%'"),})

@ManagedBean

public class Professor extends Usuario implements Serializable {

    public Professor() {
    }

    public Professor(String email, String senha) {
        super(email, senha);
    }

    public Professor(String email, String senha, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(email, senha, id, cpf, nome, fone, uf, cidade, logradouro, numero);
    }

}
