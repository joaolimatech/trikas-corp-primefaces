package br.com.trikascrm.database;

import br.com.trikascrm.model.UsuarioVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository {
    private static final Logger log = LoggerFactory.getLogger(Repository.class);

    public boolean usuarioExiste(String matricula){
        String sql = "SELECT matricula FROM usuario WHERE matricula = ?";

        try(Connection conn = ConexaoPostgres.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, matricula);

            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }

        }catch (Exception e){
            log.error("Erro ao verificar usuario: {}", e.getMessage(), e);
            return false;
        }

    }

    public UsuarioVO obterUsuario(String matricula){
        String sql = "SELECT nome, matricula, email, senha_hash, cargo, perfil " +
                "FROM usuario " +
                "WHERE matricula = ?";

        try(Connection conn = ConexaoPostgres.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, matricula);

            try (ResultSet rs = pstm.executeQuery()) {
                if(rs.next()){
                    UsuarioVO usuario = new UsuarioVO();
                    usuario.setMatricula(rs.getString("matricula"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha_hash"));
                    usuario.setCargo(rs.getString("cargo"));
                    usuario.setPerfil(rs.getString("perfil"));
                    return usuario;
                }

                return null;
            }

        }catch (Exception e){
            log.error("Erro ao obter usuario: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean atualizarSenhaHash(String matricula, String senhaHash) {
        String sql = "UPDATE usuario SET senha_hash = ? WHERE matricula = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, senhaHash);
            pstm.setString(2, matricula);

            int linhasAfetadas = pstm.executeUpdate();
            return linhasAfetadas == 1;

        } catch (Exception e) {
            log.error("Erro ao atualizar senha do usuario: {}", e.getMessage(), e);
            return false;
        }
    }
}
