package br.com.trikascrm.view;

import br.com.trikascrm.model.UsuarioVO;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UsuarioSessaoMBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private UsuarioVO usuarioLogado;

    public UsuarioVO getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(UsuarioVO usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String logout() {
        usuarioLogado = null;
        return "/index.xhtml?faces-redirect=true";
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }
}
