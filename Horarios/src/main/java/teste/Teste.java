package teste;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Professor;

public class Teste {
    
    public static void main(String[] args) {
        int id = 14;
        String nome = "André Peres";
        
        Object professor = new Professor(id, nome);
        
        try {
            Field campo = professor.getClass().getDeclaredField("id");
            campo.setAccessible(true);
            System.out.println(""
                    + campo.get(professor)
                    + "");
        } catch (NoSuchFieldException | SecurityException e) {
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ProfessorControl pc = new ProfessorControl();
//        String insert = pc.insert(professor);
//        System.out.println(insert);
//        professor = pc.getProfessor(24);
//        for (Object r : pc.getLista(professor)) {
//            System.out.println(""
//                    + r.toString()
//                    + ""
//            );
//        }
//        ProfessorControl pc = new ProfessorControl();
//        String insert = pc.insert(professor);
//        System.out.println(insert);
//        professor = pc.getProfessor(24);
//        for (Object r : pc.getLista(professor)) {
//            System.out.println(""
//                    + r.toString()
//                    + ""
//            );
//        }

//        ProfessorControl pc = new ProfessorControl();
//        String insert = pc.insert(professor);
//        System.out.println(insert);
//        professor = pc.getProfessor(24);
//        for (Object r : pc.getLista(professor)) {
//            System.out.println(""
//                    + r.toString()
//                    + ""
//            );
//        }
//        ProfessorControl pc = new ProfessorControl();
//        String insert = pc.insert(professor);
//        System.out.println(insert);
//        professor = pc.getProfessor(24);
//        for (Object r : pc.getLista(professor)) {
//            System.out.println(""
//                    + r.toString()
//                    + ""
//            );
//        }
    }
}