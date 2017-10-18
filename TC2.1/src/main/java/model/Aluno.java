/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ton
 */
public class Aluno extends Pessoa {

    private String responsavel;
    private String nivelParentesco;

    public Aluno() {
    }

    public Aluno(String responsavel, String nivelParentesco, int id, long cpf, String nome, long fone, String uf, String cidade, String logradouro, int numero) {
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

}
