package teste;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import model.*;
import util.Dao;
import util.ObjetoDinamico;
import view.mbean.Bean;

public class Teste {

    public static void main(String[] args) {

        String corpo = ""
                + "package model;\n"
                + "\n"
                + "import java.io.Serializable;\n"
                + "import java.util.Date;\n"
                + "import javax.faces.bean.ManagedBean;\n"
                + "import javax.persistence.Column;\n"
                + "import javax.persistence.Entity;\n"
                + "import javax.persistence.GeneratedValue;\n"
                + "import javax.persistence.GenerationType;\n"
                + "import javax.persistence.Id;\n"
                + "\n"
                + "@Entity\n"
                + "\n"
                + "@ManagedBean\n"
                + "\n"
                + "public class Carrinho implements Serializable {\n"
                + "\n"
                + "    @Id\n"
                + "    @GeneratedValue(strategy = GenerationType.SEQUENCE)\n"
                + "    private int id;\n"
                + "\n"
                + "    @Column(nullable = false)\n"
                + "    private Date data;\n"
                + "\n"
                + "    @Column(nullable = false)\n"
                + "    private Date hora;\n"
                + "\n"
                + "    public Carrinho() {\n"
                + "    }"
                + "\n"
                + "    public int getId() {\n"
                + "        return id;\n"
                + "    }\n"
                + "\n"
                + "    public void setId(int id) {\n"
                + "        this.id = id;\n"
                + "    }\n"
                + "\n"
                + "    public Date getData() {\n"
                + "        return data;\n"
                + "    }\n"
                + "\n"
                + "    public void setData(Date data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "\n"
                + "    public Date getHora() {\n"
                + "        return hora;\n"
                + "    }\n"
                + "\n"
                + "    public void setHora(Date hora) {\n"
                + "        this.hora = hora;\n"
                + "    }\n"
                + "\n"
                + "}"
                + ""
                + "";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector();
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.println(corpo);
        out.close();
        JavaFileObject file = new JavaSourceFromString("Carrinho", writer.toString());
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
        boolean success = task.call();

        String saida = "Success: " + success + "\n";
//
        if (success) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, "Finalmente");
        }

        //        Adm adm = new Adm();
////        *************** Exclusão
//        adm.setId(20);
//        Object teste = Dao.getInstance().busca(new Adm(), 0);
//        saida = Dao.getInstance().existe(new Adm()) + "";
//        Object teste = new Bean().lista(new Adm(), 0);
//        if (teste != null) {
//            saida = teste.toString();
//        }
////        *************** 
////        *************** Alteração
//        try {
//            adm.setId(20);
//            adm = (Adm) Dao.getInstance().buscaPorId(adm);
//            adm.setSenha("aimeudeus");
//        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
//            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        saida = Dao.getInstance().altera(adm);
////        ***************
////        *************** Busca por nível
//        Dao.getInstance().busca(adm, 0).forEach((r) -> {
//            System.out.println(((Adm) r).getNome());
//        });
////        *************** Busca por nível
////        *************** Busca por id
//        
//        try {
//            novo = (Adm) Dao.getInstance().buscaPorId(adm);
//        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
//            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String saida = "Sem registro";
//        if (novo != null) {
//            saida = novo.getNome();
//        }
//        System.out.println(saida);
//
////        ***************
//        try {
//            Dao.getInstance().buscaPorId(adm);
//        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
//            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, ex.getMessage());
//        }
//        System.out.println(saida);
        System.exit(0);

    }

}

class JavaSourceFromString extends SimpleJavaFileObject {

    final String code;

    JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/')
                + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}
