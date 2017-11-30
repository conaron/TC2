/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Ton
 */
public class Professor extends Usuario implements Serializable {

    private int aluno;

    public Professor() {
    }

    public Professor(int aluno, String usuario, String senha) {
        super(usuario, senha);
        this.aluno = aluno;
    }

    public Professor(int aluno, String usuario, String senha, int id, String cpf, String nome, String fone, String uf, String cidade, String logradouro, int numero) {
        super(usuario, senha, id, cpf, nome, fone, uf, cidade, logradouro, numero);
        this.aluno = aluno;
    }

    public Professor(int aluno) {
        this.aluno = aluno;
    }

    public int getAluno() {
        return aluno;
    }

    public void setAluno(int aluno) {
        this.aluno = aluno;
    }

    public String toQuery() {
        if (this.getId() > 0) {
            return "SELECT u FROM Professor u WHERE u.id = " + this.getId();
        }
        if (this.getNome() == null) {
            this.setNome("");
        }
        return "SELECT u FROM Professor u"
                + " WHERE u.nome like '%" + this.getNome() + "%'";
    }

}
