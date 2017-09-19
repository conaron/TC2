package teste;

import util.CrudUtil;
import model.Professor;

public class Teste {

    public static void main(String[] args) {
        String saida;
        CrudUtil crud = new CrudUtil();

        Professor professor = new Professor();
//        professor.setId(3);
//        professor.setNome("Alex Dias Gonsales");
//        saida = crud.exclui(professor);
//        saida = crud.insere(professor);
//        saida = crud.altera(professor);

//        Listagem de professores
        for (Object r : crud.lista(professor)) {

            System.out.println(""
                    + r.toString()
                    + ""
            );
        }
//        Inserção de dados
//
//        String insert = crud.insere(professor);
//
//        Exclusao de dados
//
//        String retorno = crud.exclui(professor);

    }
}
