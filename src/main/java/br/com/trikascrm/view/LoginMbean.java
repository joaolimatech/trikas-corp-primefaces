package br.com.trikascrm.view;

import java.io.Serializable;

import br.com.trikascrm.model.UsuarioVO;
import br.com.trikascrm.service.Service;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class LoginMbean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(LoginMbean.class);

    private Service service;
    private String matricula;
    private String senha;
    private UsuarioVO userVO;
    @Inject
    private UsuarioSessaoMBean usuarioSessaoMBean;

    public LoginMbean(){
        service = new Service();
    }

    public String validarLogin() {
        log.info("Tentativa de login recebida para a matricula: {}", matricula);
        userVO = service.obterUsuario(matricula);

        if (userVO == null) {
            log.warn("Login recusado. Usuario nao encontrado para a matricula: {}", matricula);
            showMessageErro("Usuario nao existe.");
            return null;
        }

        if (userVO.getSenha() == null || userVO.getSenha().isBlank()) {
            boolean senhaFoiCadastrada  = cadastrarSenha(userVO);
            log.info("senha foi cadastrada com sucesso para o usuario {}",userVO.getNome());
            return null;
        }

        boolean senhaCorreta = service.validarSenha(senha, userVO.getSenha());
        if (senhaCorreta) {
            usuarioSessaoMBean.setUsuarioLogado(userVO);
            log.info("Login realizado com sucesso para a matricula: {}", matricula);
            showMessageSucess("Login realizado com sucesso.");
            return "home?faces-redirect=true";
        }

        log.warn("Login recusado. Senha invalida para a matricula: {}", matricula);
        showMessageErro("Matricula ou senha invalidos.");
        return null;
    }

    public boolean cadastrarSenha(UsuarioVO userVO){
        log.info("Cadastrando primeira senha para a matricula: {}", userVO.getMatricula());
        boolean senhaCadastrada = service.cadastrarSenha(userVO.getMatricula(), senha);

        if (senhaCadastrada) {
            showMessageSucess("Senha cadastrada com sucesso.");
            return true;
        }

        showMessageErro("Nao foi possivel cadastrar a senha.");
        return false;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public UsuarioVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UsuarioVO userVO) {
        this.userVO = userVO;
    }

    public void showMessageErro(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "ERRO",
                        msg
                )
        );

    }

    public void showMessageSucess(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "SUCESSO",
                        msg
                )
        );

    }

    public UsuarioSessaoMBean getUsuarioSessaoMBean() {
        return usuarioSessaoMBean;
    }

    public void setUsuarioSessaoMBean(UsuarioSessaoMBean usuarioSessaoMBean) {
        this.usuarioSessaoMBean = usuarioSessaoMBean;
    }
}
