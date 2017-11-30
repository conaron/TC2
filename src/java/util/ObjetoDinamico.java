package util;

import javax.tools.SimpleJavaFileObject;
import java.io.PrintWriter;
import java.net.URI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

@ManagedBean

public class ObjetoDinamico implements Serializable {

    private String nome;
    private String descricao;
    private static HashMap<String, String> parametros;
    private String arquivo_origem;
    private String arquivo_destino;

    private String corpo;

    public ObjetoDinamico() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public HashMap<String, String> getParametros() {
        return parametros;
    }

    public void addParametros(String tipo, String parametro) {
        if (!(parametros instanceof HashMap)) {
            parametros = new HashMap();
        }
        parametro = parametro.toLowerCase().replaceAll("[^\\da-z]", "");
        parametros.put(parametro, tipo);
    }

    public void zeraParametros() {
        parametros = null;
    }

    private void gravaArquivo() throws MalformedURLException, IOException {

        String catalina_java = System.getenv("CATALINA_JAVA");
        String campos = "";
        String metodos = "";

        for (String parametro : parametros.keySet()) {
            String tipo = parametros.get(parametro);
            String campoM = parametro.substring(0, 1).toUpperCase() + parametro.substring(1);

            nome = nome.toLowerCase();
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);

            campos += "\n"
                    + "    @Column(nullable = false)\n"
                    + "    private " + tipo + " " + parametro + ";\n"
                    + "";

            metodos += "\n"
                    + "    public " + tipo + " get" + campoM + "() {\n"
                    + "        return " + parametro + ";\n"
                    + "    }\n"
                    + "\n"
                    + "    public void set" + campoM + "(" + tipo + " " + parametro + ") {\n"
                    + "        this." + parametro + " = " + parametro + ";\n"
                    + "    }\n"
                    + "\n";
        }

        corpo = ""
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
                + "public class " + nome + " implements Serializable {\n"
                + "\n"
                + "    @Id\n"
                + "    @GeneratedValue(strategy = GenerationType.SEQUENCE)\n"
                + "    private int id;\n"
                + "\n"
                + "    @Column(nullable = false)\n"
                + "    private String aluno;\n"
                + "\n"
                + "    @Column(nullable = false)\n"
                + "    private Date data;\n"
                + "\n"
                + "    @Column(nullable = false)\n"
                + "    private Date hora;\n"
                + "\n"
                + campos
                + "\n"
                + "    public " + nome + "() {\n"
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
                + "    public String getAluno() {\n"
                + "        return aluno;\n"
                + "    }\n"
                + "\n"
                + "    public void setAluno(String aluno) {\n"
                + "        this.aluno = aluno;\n"
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
                + metodos
                + "\n"
                + "}"
                + ""
                + "";

        File arquivo = new File(catalina_java, nome + ".java");
        arquivo_origem = arquivo.getAbsolutePath();

        FileWriter arq = new FileWriter(arquivo_origem);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf(corpo);
        arq.close();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        List<String> optionList = new ArrayList();
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path"));
        Iterable<? extends JavaFileObject> compilationUnit
                = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(arquivo));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostics,
                optionList,
                null,
                compilationUnit);

        if (task.call()) {
            System.out.println("Yipe");
        } else {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.out.format("Error on line %d in %s%n",
                        diagnostic.getLineNumber(),
                        diagnostic.getSource().toUri());
            }
        }
        fileManager.close();

    }

    public HashMap<String, String> getListaTipos() {
        HashMap<String, String> lista = new HashMap();
        lista.put("Número inteiro", "int");
        lista.put("Número decimal", "float");
        lista.put("Letras ou palavras", "String");
        return lista;
    }

    public void compilar() throws IOException {
        gravaArquivo();
//        montaCorpo();

//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector();
//        StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        out.println(corpo);
//        out.close();
//        JavaFileObject file = new JavaSourceFromString(nome, writer.toString());
//        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
//        CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
//        boolean success = task.call();
//
//        String saida = success + "";
//        System.out.println("Success: " + success);
//        System.out.println(saida);
//        System.exit(0);
//
//        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
//            System.out.println(diagnostic.getCode());
//            System.out.println(diagnostic.getKind());
//            System.out.println(diagnostic.getPosition());
//            System.out.println(diagnostic.getStartPosition());
//            System.out.println(diagnostic.getEndPosition());
//            System.out.println(diagnostic.getSource());
//            System.out.println(diagnostic.getMessage(null));
//        }
//
//        if (success) {
//            Logger.getLogger(ObjetoDinamico.class.getName()).log(Level.SEVERE, "Finalmente");
//        }
//        String caminho = this.getClass().getResource("/").getPath()
//        } catch (IOException ex) {
//            mensagem = "\n\n\n\n\n\n\n excecao \n" + ex.toString() + "\n" + "excecao \n*\n*\n*\n*\n*\n*\n*";
//            Logger.getLogger(ObjetoCriacao.class.getName()).log(Level.SEVERE, mensagem);
//        }
    }

}

