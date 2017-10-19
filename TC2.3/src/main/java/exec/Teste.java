/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import model.Adm;
import util.CrudUtil;

/**
 *
 * @author Ton
 */
public class Teste {

    public static void main(String[] args) {
        String saida;
        CrudUtil crud = new CrudUtil();

        Adm teste = new Adm();
        teste.setNome("Airton");
        teste.setUsuario("airton");
        teste.setSenha("senha");
        teste.setCpf("99999999999");

//        for (int i = 1; i < 20; i++) {
//            System.out.println("\n");
//        }
        System.out.println(crud.insere(teste));
//        for (int i = 1; i < 20; i++) {
//            System.out.println("\n");
//        }

//        for (Object r : crud.lista(teste)) {
//
//            System.out.println(""
//                    + r.toString()
//                    + ""
//            );
//        }

//        ConexaoDao conexao = new ConexaoDao();
//        conexao.encerra_sessao();
    }

}
