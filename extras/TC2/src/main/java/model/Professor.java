package model;

import java.io.Serializable;
import java.text.Normalizer;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity

public class Professor implements Serializable {

    @Id
    int id;

    @NotNull
    @Column(nullable = false, unique = true)
    String nome;

    public Professor() {
    }

    public Professor(int id) {
        this.id = id;
    }

    public Professor(int id, String nome) {
        this.id = id;
        this.nome = Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }

    @Override
    public String toString() {
        return "Professor{" + "id=" + id + ", nome=" + nome + '}';
    }

    public String toQuery() {
        if (id > 0) {
            return "SELECT u FROM Professor u WHERE u.id = " + id;
        }
        if (nome == null) {
            nome = "";
        }
        return "SELECT u FROM Professor u"
                + " WHERE u.nome like '%" + nome + "%'";
    }

}
