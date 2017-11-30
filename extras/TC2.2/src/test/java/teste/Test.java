/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import model.Adm;
import util.CrudUtil;

/**
 *
 * @author Ton
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String saida;
        CrudUtil crud = new CrudUtil();

        Adm teste = new Adm();
        teste.setNome("Airton");
        teste.setUsuario("airton");
        teste.setSenha("senha");

        System.out.println(crud.insere(teste));

        for (Object r : crud.lista(teste)) {

            System.out.println(""
                    + r.toString()
                    + ""
            );
        }
    }

}
