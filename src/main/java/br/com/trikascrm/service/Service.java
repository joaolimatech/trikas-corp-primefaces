package br.com.trikascrm.service;

import br.com.trikascrm.database.Repository;
import br.com.trikascrm.model.UsuarioVO;
import br.com.trikascrm.security.SenhaUtil;

import java.util.List;

public class Service {
    Repository repository = new Repository();

    // Classe meio de campo entre a camada de view e a camada de repository, onde ficam as regras de negocio, validacoes, etc.
    public boolean validarUserNaBase(String matricula){
        return repository.usuarioExiste(matricula);
    }

    public UsuarioVO obterUsuario(String matricula){
        return repository.obterUsuario(matricula);
    }

    public boolean cadastrarSenha(String matricula, String senhaPura) {
        String senhaHash = SenhaUtil.gerarHash(senhaPura);
        return repository.atualizarSenhaHash(matricula, senhaHash);
    }

    public boolean validarSenha(String senhaPura, String senhaHash) {
        return SenhaUtil.verificarSenha(senhaPura, senhaHash);
    }

    public List<UsuarioVO> pesquisarFuncionario(String matricula){
        return repository.listarFuncionarios(matricula);
    }
}
