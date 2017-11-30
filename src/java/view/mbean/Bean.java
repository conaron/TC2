package view.mbean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Objeto;
import util.Dao;

import util.MenuItem;
import util.ObjetoDinamico;
import util.Rotas;

@ManagedBean
@ViewScoped

public class Bean implements Serializable {

    private final HashMap<String, MenuItem> areas;
    private static String area = "0_base";
    public static String tela = "inicio";
    private List<MenuItem> barra;
    private String arquivo_corpo;
    private Object alterarRegistro;
    private static Object usuario;
    public static String situacao = "./sistema/login.xhtml";
    private static String teste;

    public String getTeste() {

        teste = FacesContext.getCurrentInstance().getExternalContext().getRealPath("index.xhtml");
        File arquivo = new File(teste);
        arquivo = new File(arquivo.getParent());
        teste = arquivo.getPath();

        Properties properties = System.getProperties();
        Set<Object> sysPropertiesKeys = properties.keySet();
        for (Object key : sysPropertiesKeys) {
            System.out.println(key + " =" + properties.getProperty((String) key));
        }

        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public String getSituacao() {
        return situacao;
    }

    public static Object getUsuario() {
        return usuario;
    }

    public static void setUsuario(Object usuario) {
        Bean.usuario = usuario;
    }

    public Bean() {
        String update = "barra";
        areas = new HashMap();
        areas.put("admin", new MenuItem("Admin", "admin", "adm", update));
        areas.put("professor", new MenuItem("Professor", "professor", "professor", update));
        areas.put("aluno", new MenuItem("Aluno", "aluno", "aluno", update));
        areas.put("objeto", new MenuItem("Objeto", "objeto", "objeto", update));
    }

    public List<MenuItem> doca(String classe) {
        List<MenuItem> doca_x = new ArrayList();
        if (classe.equalsIgnoreCase("adm")) {
            doca_x.add(areas.get("admin"));
            doca_x.add(areas.get("professor"));
        }
        doca_x.add(areas.get("aluno"));
        doca_x.add(areas.get("objeto"));

        return doca_x;
    }

    public List<MenuItem> getBarra() {
        return barra;
    }

    public void setBarra(List<MenuItem> barra) {
        this.barra = barra;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        Bean.tela = tela;
    }

    public void registra(Object objeto) {
        if (objeto.getClass().getSimpleName().toLowerCase().matches(".*(adm|professor).*")) {
            try {
                objeto.getClass().getMethod("setSenha", String.class).invoke(objeto, "12345");
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
        String mensagem = Dao.getInstance().insere(objeto);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro", mensagem));
        tela = "lista";
    }

    public void registraIoT(ObjetoDinamico objeto) throws IOException {
        Objeto principal = new Objeto();
        principal.setNome(objeto.getNome());
        principal.setDescricao(objeto.getDescricao());
        String mensagem = Dao.getInstance().insere(principal);

        objeto.compilar();
        objeto.zeraParametros();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro", mensagem));
        tela = "lista";
    }

    public void detalha(Object objeto) {
        this.alterarRegistro = objeto;
        tela = "altera";
    }

    public void altera(Object objeto) {
        String mensagem = Dao.getInstance().altera(objeto);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Alteração", mensagem));
        tela = "lista";
    }

    public Object lista(Object objeto, int nivel) {
        return Dao.getInstance().busca(objeto, 0);
    }

    public String getArea() {
        return area;
    }

    public Object getAlterarRegistro() {
        return alterarRegistro;
    }

    public void setAlterarRegistro(Object alterarRegistro) {
        this.alterarRegistro = alterarRegistro;
    }

    public void setArea(String area) {
        Bean.area = area;

        String update = "corpo";
        String icon = area;
        if (icon.equalsIgnoreCase("adm")) {
            icon = "admin";
        }

        barra = new ArrayList();
        barra.add(new MenuItem("Listagem", "lista", "lista", update));
        barra.add(new MenuItem("Cadastro", "cadastro", "cadastro", update));
        barra.add(new MenuItem("", icon, "none", "none"));
    }

    public String getArquivo_corpo() {
        return arquivo_corpo;
    }

    public void setArquivo_corpo(String arquivo_corpo) throws URISyntaxException {
        tela = arquivo_corpo;
        String s = Rotas.S;
        String caminho = Rotas.BASE + s + "sistema" + s + area + s + tela + ".xhtml";
        File arquivo = new File(caminho);
        if (!arquivo.isFile()) {
            System.out.println(caminho);
            tela = "inicio";
        }
    }

    public void remove(Object objeto) {
        String mensagem = Dao.getInstance().remove(objeto);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Situação", mensagem));
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        situacao = "sistema/login.xhtml";
    }

}
