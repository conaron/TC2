package model;

import java.io.Serializable;
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

    public Professor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Professor{" + "id=" + id + ", nome=" + nome + '}';
    }

}
