/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mbean;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Adm;
import model.Professor;
import org.primefaces.context.RequestContext;
import util.Dao;
import util.Rotas;

/**
 *
 * @author Ton
 */
@ManagedBean

public class Login {

    private final String url = "sistema/sistema.xhtml";

    public void login() throws NoSuchAlgorithmException {
        FacesMessage message;
        Dao.getInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext request = RequestContext.getCurrentInstance();

//        Definição da raiz do sistema
        Rotas.BASE = context.getExternalContext().getRealPath("index.xhtml");
        File arquivo = new File(Rotas.BASE);
        arquivo = new File(arquivo.getParent());
        Rotas.BASE = arquivo.getPath();
        Rotas.JAVA = Rotas.BASE + Rotas.S + "java";
        Rotas.CLASSES = Rotas.BASE + Rotas.S + "WEB-INF" + Rotas.S + "classes";
        Rotas.JARS = Rotas.BASE + Rotas.S + "WEB-INF" + Rotas.S + "lib";
        File java = new File(Rotas.JAVA);
        java.mkdir();

//        Definição da raiz do sistema
        Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
        String hash = requestParamMap.get("hash");

        if (hash.equals(geraHash("admin", "admin"))) {
            Adm usuario = new Adm(0, "admin", "admin", "Usuário Administrativo");
            request.addCallbackParam("logado", true);
            Bean.setUsuario(usuario);
            Bean.situacao = url;
//            context.getApplication().getNavigationHandler().handleNavigation(context, null, url);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo", usuario.getNome());
            context.addMessage(null, message);
            return;
        }
        for (Object objeto : Dao.getInstance().busca(new Adm(), 0)) {
            Adm usuario = (Adm) objeto;
            if (hash.equals(geraHash(usuario.getEmail(), usuario.getSenha()))) {
                request.addCallbackParam("logado", true);
                Bean.setUsuario(usuario);
                Bean.situacao = url;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo", usuario.getNome());
                context.addMessage(null, message);
                return;
            }
        }

        for (Object objeto : Dao.getInstance().busca(new Professor(), 0)) {
            Professor usuario = (Professor) objeto;
            if (hash.equals(geraHash(usuario.getEmail(), usuario.getSenha()))) {
                request.addCallbackParam("logado", true);
                Bean.setUsuario(usuario);
                Bean.situacao = url;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo", usuario.getNome());
                context.addMessage(null, message);
                return;
            }
        }

        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro em login", "Dados inválidos");
        request.addCallbackParam("logado", false);
        context.addMessage(null, message);
//                RequestContext.getCurrentInstance().addCallbackParam("destino", url);
//        RequestContext.getCurrentInstance().update("body");
    }

    private String objetoHash(Object objeto) throws
            NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException,
            NoSuchAlgorithmException {

        String teste
                = (String) objeto.getClass().getMethod("getEmail").invoke(objeto)
                + (String) objeto.getClass().getMethod("getSenha").invoke(objeto);

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(teste.getBytes(), 0, teste.length());

        return new BigInteger(1, md.digest()).toString(16);
    }

    private String geraHash(String email, String senha) throws NoSuchAlgorithmException {
        String teste = email + senha;

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(teste.getBytes(), 0, teste.length());
        return new BigInteger(1, md.digest()).toString(16);
    }

}
