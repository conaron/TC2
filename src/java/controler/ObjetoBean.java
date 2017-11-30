package controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import model.Objeto;
import util.Dao;
import util.Rotas;
import view.mbean.Bean;

@ManagedBean
@ViewScoped

public class ObjetoBean implements Serializable {

    private Objeto objeto;
    private String nome;
    private String descricao;
    private static HashMap<String, String> parametros;
    private String arquivo_origem;
    private String arquivo_destino;

    public ObjetoBean() {
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
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

    public String getArquivo_origem() {
        return arquivo_origem;
    }

    public void setArquivo_origem(String arquivo_origem) {
        this.arquivo_origem = arquivo_origem;
    }

    public String getArquivo_destino() {
        return arquivo_destino;
    }

    public void setArquivo_destino(String arquivo_destino) {
        this.arquivo_destino = arquivo_destino;
    }

//    Metodos
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

    public HashMap<String, String> getListaTipos() {
        HashMap<String, String> lista = new HashMap();
        lista.put("Número inteiro", "int");
        lista.put("Número decimal", "float");
        lista.put("Letras ou palavras", "String");
        return lista;
    }

    public List<Objeto> getLista() {
        List<Objeto> lista = new ArrayList();
        Dao.getInstance().busca(objeto, 0).forEach((o) -> {
            lista.add((Objeto) o);
        });
        return lista;
    }

    public void registra() {
        File arquivo = null;
        String corpo = getCorpo();

        try {
            String origem = Rotas.JAVA + Rotas.S + nome + ".java";
            PrintWriter writer = new PrintWriter(origem, "UTF-8");
            writer.println(corpo);
            writer.close();
            arquivo = new File(origem);
        } catch (FileNotFoundException | UnsupportedEncodingException x) {
            System.out.println("Erro ao gravar o arquivo java");
        }

        try {
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector();
            System.setProperty("java.home", System.getenv("JAVA_COMPILER"));
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
            List<String> optionList = new ArrayList();
            optionList.add("-classpath");
            optionList.add(
                    Rotas.JARS + Rotas.S + "hibernate-entitymanager-4.3.1.Final.jar"
                    + System.getProperty("path.separator") + Rotas.JARS + Rotas.S + "javax.faces.jar"
                    + System.getProperty("path.separator") + Rotas.JARS + Rotas.S + "hibernate-jpa-2.1-api-1.0.0.Final.jar"
            );
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
                    System.out.println("Erro na linha " + diagnostic.getLineNumber()
                            + " no arquivo " + diagnostic.getSource().toUri()
                            + "\n"
                            + diagnostic.getMessage(Locale.ENGLISH)
                            + "");
                }
            }

            fileManager.close();

            String mensagem = Dao.getInstance().insere(objeto);
            FacesMessage f_mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro", mensagem);
            FacesContext.getCurrentInstance().addMessage(null, f_mensagem);
            Bean.tela = "lista";
        } catch (IOException ex) {
            Logger.getLogger(ObjetoBean.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public String getMensagem(String mensagem) {
        HashMap<String, String> lista = new HashMap();
        lista.put("padrao", "O campo não pode estar vazio");
        lista.put("tamanho3", "Tamanho mínimo: 3 caracteres");
        lista.put("nome", "Nome não pode estar vazio");
        lista.put("descrição", "Descrição não pode estar vazia");
        return lista.get(mensagem);
    }

    public String getTitulo(String operacao) {
        HashMap<String, String> lista = new HashMap();
        lista.put("cadastro", "Cadastro de Objetos IoT");
        lista.put("alteracao", "Alteração de Cadastro");
        return lista.get(operacao);

    }

    private String getCorpo() {
        String campos = "";
        String metodos = "";
        String n = Rotas.N;
        String t = "    ";

        for (String parametro : parametros.keySet()) {
            String tipo = parametros.get(parametro);
            String campoM = parametro.substring(0, 1).toUpperCase() + parametro.substring(1);

            nome = nome.toLowerCase();
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);

            campos += t + "@Column(nullable = false)" + n
                    + t + "private " + tipo + " " + parametro + ";" + n;

            metodos += t + "public " + tipo + " get" + campoM + "() {" + n
                    + t + t + "return " + parametro + ";" + n
                    + t + "}" + n + n
                    + t + "public void set" + campoM + "(" + tipo + " " + parametro + ") {" + n
                    + t + t + "this." + parametro + " = " + parametro + ";" + n
                    + t + "}" + n + n;
        }

        return "package model;" + n + n
                + "import java.io.Serializable;" + n
                + "import java.util.Date;" + n
                + "import javax.faces.bean.ManagedBean;" + n
                + "import javax.persistence.Column;" + n
                + "import javax.persistence.Entity;" + n
                + "import javax.persistence.GeneratedValue;" + n
                + "import javax.persistence.GenerationType;" + n
                + "import javax.persistence.Id;" + n + n
                + "@Entity" + n + n
                + "@ManagedBean" + n + n
                + "public class " + nome + " implements Serializable {" + n + n
                + t + "@Id" + n
                + t + "@GeneratedValue(strategy = GenerationType.SEQUENCE)" + n
                + t + "private int id;" + n + n
                + t + "@Column(nullable = false)" + n
                + t + "private String aluno;" + n + n
                + t + "@Column(nullable = false)" + n
                + t + "private Date data;" + n + n
                + t + "@Column(nullable = false)" + n
                + t + "private Date hora;" + n + n
                + campos + n
                + t + "public " + nome + "() {" + n + t + "}" + n + n
                + t + "public int getId() {" + n
                + t + t + "return id;" + n
                + t + "}" + n + n
                + t + "public void setId(int id) {" + n
                + t + t + "this.id = id;" + n
                + t + "}" + n + n
                + t + "public String getAluno() {" + n
                + t + t + "return aluno;" + n
                + t + "}" + n + n
                + t + "public void setAluno(String aluno) {" + n
                + t + t + "this.aluno = aluno;" + n
                + t + "}" + n + n
                + t + "public Date getData() {" + n
                + t + t + "return data;" + n
                + t + "}" + n + n
                + t + "public void setData(Date data) {" + n
                + t + t + "this.data = data;" + n
                + t + "}" + n + n
                + t + "public Date getHora() {" + n
                + t + t + "return hora;" + n
                + t + "}" + n + n
                + t + "public void setHora(Date hora) {" + n
                + t + t + "this.hora = hora;" + n
                + t + "}" + n + n
                + metodos + n
                + "}" + n;
    }

}
