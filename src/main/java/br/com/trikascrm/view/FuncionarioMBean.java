package br.com.trikascrm.view;

import br.com.trikascrm.model.UsuarioVO;
import br.com.trikascrm.service.Service;
import com.sun.faces.util.CollectionsUtils;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class FuncionarioMBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public FuncionarioMBean(){
        service = new Service();
        limparCampos();
    }

    public void limparCampos(){
        matriculaPesquisa = null;
        lstFuncionarios = new ArrayList<>();
    }

    private Service service;

    @Inject
    private UsuarioSessaoMBean usuarioSessaoMBean;
    private List<UsuarioVO> lstFuncionarios;
    private String matriculaPesquisa;
    private boolean renderConsultaFuncionarios;

    public void pesquisarFuncionarioMatricula(){
        if(matriculaPesquisa!=null && !matriculaPesquisa.trim().isEmpty()){
            matriculaPesquisa = matriculaPesquisa.trim();
            matriculaPesquisa =  matriculaPesquisa.equalsIgnoreCase("all")? null : matriculaPesquisa;

            lstFuncionarios = service.pesquisarFuncionario(matriculaPesquisa);
            if(CollectionUtils.isEmpty(lstFuncionarios)){
               showMessageErro("Nenhum funcionario encontrado para a matricula: " + matriculaPesquisa);
               return;
            } else {
                renderConsultaFuncionarios = !CollectionUtils.isEmpty(lstFuncionarios);
            }

        }

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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public UsuarioSessaoMBean getUsuarioSessaoMBean() {
        return usuarioSessaoMBean;
    }

    public void setUsuarioSessaoMBean(UsuarioSessaoMBean usuarioSessaoMBean) {
        this.usuarioSessaoMBean = usuarioSessaoMBean;
    }

    public List<UsuarioVO> getLstFuncionarios() {
        return lstFuncionarios;
    }

    public void setLstFuncionarios(List<UsuarioVO> lstFuncionarios) {
        this.lstFuncionarios = lstFuncionarios;
    }

    public String getMatriculaPesquisa() {
        return matriculaPesquisa;
    }

    public void setMatriculaPesquisa(String matriculaPesquisa) {
        this.matriculaPesquisa = matriculaPesquisa;
    }

    public boolean isRenderConsultaFuncionarios() {
        return renderConsultaFuncionarios;
    }

    public void setRenderConsultaFuncionarios(boolean renderConsultaFuncionarios) {
        this.renderConsultaFuncionarios = renderConsultaFuncionarios;
    }
}
