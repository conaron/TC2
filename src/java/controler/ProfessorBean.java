package controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Professor;
import util.Dao;
import view.mbean.Bean;

@ManagedBean
@ViewScoped

public class ProfessorBean implements Serializable {

    private Professor professor;

    public ProfessorBean() {
        professor = new Professor();
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Professor> getLista() {
        List<Professor> lista = new ArrayList();
        Dao.getInstance().busca(professor, 0).forEach((objeto) -> {
            lista.add((Professor) objeto);
        });
        return lista;
    }

    public void detalha(Professor professor) {
        this.professor = professor;
        Bean.tela = "altera";
    }

    public void retorna_detalhe() {
        Bean.tela = "lista";
    }

    public void remove(Professor professor) {
        String mensagem = Dao.getInstance().remove(professor);
        FacesMessage f_mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Situação", mensagem);
        FacesContext.getCurrentInstance().addMessage(null, f_mensagem);
    }

    public void registra(Professor professor) {
        professor.setSenha("12345");
        String mensagem = Dao.getInstance().insere(professor);
        FacesMessage f_mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro", mensagem);
        FacesContext.getCurrentInstance().addMessage(null, f_mensagem);
        Bean.tela = "altera";
    }

    public void altera(Professor professor) {
        String mensagem = Dao.getInstance().altera(professor);
        FacesMessage f_mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alteração", mensagem);
        FacesContext.getCurrentInstance().addMessage(null, f_mensagem);
        Bean.tela = "lista";
    }

    public String getMascara(String chave) {
        HashMap<String, String> lista = new HashMap();
        lista.put("cpf", "999.999.999-99");
        lista.put("fone", "(99) 9999-9999");
        lista.put("numero", "9999999");
        return lista.get(chave);
    }

    public String getMensagem(String mensagem) {
        HashMap<String, String> lista = new HashMap();
        lista.put("padrao", "O campo não pode estar vazio");
        lista.put("tamanho3", "Tamanho mínimo: 3 caracteres");
        lista.put("nome", "Nome não pode estar vazio");
        return lista.get(mensagem);
    }

    public TreeMap<String, String> getUf() {
        TreeMap<String, String> lista = new TreeMap();
        lista.put("Acre", "AC");
        lista.put("Alagoas", "AL");
        lista.put("Amapá", "AP");
        lista.put("Amazonas", "AM");
        lista.put("Bahia", "BA");
        lista.put("Ceará", "CE");
        lista.put("Distrito Federal", "DF");
        lista.put("Espírito Santo", "ES");
        lista.put("Goiás", "GO");
        lista.put("Maranhão", "MA");
        lista.put("Mato Grosso", "MT");
        lista.put("Mato Grosso do Sul", "MS");
        lista.put("Minas Gerais", "MG");
        lista.put("Pará", "PA");
        lista.put("Paraíba", "PB");
        lista.put("Paraná", "PR");
        lista.put("Pernambuco", "PE");
        lista.put("Piauí", "PI");
        lista.put("Rio de Janeiro", "RJ");
        lista.put("Rio Grande do Norte", "RN");
        lista.put("Rondônia", "RO");
        lista.put("Roraima", "RR");
        lista.put("Santa Catarina", "SC");
        lista.put("São Paulo", "SP");
        lista.put("Sergipe", "SE");
        lista.put("Tocantins", "TO");
        return lista;
    }

    public String getTitulo(String operacao) {
        HashMap<String, String> lista = new HashMap();
        lista.put("cadastro", "Cadastro de Professores");
        lista.put("alteracao", "Alteração de Cadastro");
        return lista.get(operacao);

    }

}
