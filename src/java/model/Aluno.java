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
    @NamedQuery(name = "Aluno.0", query = "SELECT u FROM Aluno u"),
    @NamedQuery(name = "Aluno.1", query = "SELECT u FROM Aluno u WHERE u.nome LIKE '%:nome%'"),
    @NamedQuery(name = "Aluno.2", query = "SELECT u FROM Aluno u WHERE u.rfid = ':rfid'"),
})

@ManagedBean

public class Aluno extends Pessoa implements Serializable {

    @Column
    private String responsavel;

    @Column
    private String nivelParentesco;

    @Column
    private String email;

    @Column
    private String rfid;

    public Aluno() {
    }

    public Aluno(String responsavel, String nivelParentesco, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(id, cpf, nome, fone, uf, cidade, logradouro, numero);
        this.responsavel = responsavel;
        this.nivelParentesco = nivelParentesco;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getNivelParentesco() {
        return nivelParentesco;
    }

    public void setNivelParentesco(String nivelParentesco) {
        this.nivelParentesco = nivelParentesco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

}
