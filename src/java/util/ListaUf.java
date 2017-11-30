package util;

import java.io.Serializable;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ListaUf implements Serializable {

    private TreeMap<String, String> lista;

    @PostConstruct
    public void init() {
        lista = new TreeMap<>();
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

    }

    public TreeMap<String, String> getLista() {
        return lista;
    }
}
