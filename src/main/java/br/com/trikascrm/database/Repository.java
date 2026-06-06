package br.com.trikascrm.database;

import br.com.trikascrm.model.UsuarioVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public List<UsuarioVO> listarFuncionarios(String matricula) {
        String parametroMatricula = matricula != null && !matricula.trim().isEmpty()? " and matricula = '"+matricula.trim()+"' " : " ";
        String sql = "SELECT nome, matricula, email,  cargo, telefone, created, perfil FROM usuario where 1=1 \n " +
                parametroMatricula;

        log.info("Executando SQL para listar funcionarios: \n{}", sql);
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery() ;
            List<UsuarioVO> funcionarios = new java.util.ArrayList<>();
                while (rs.next()) {
                    UsuarioVO usuario = new UsuarioVO();
                    usuario.setMatricula(rs.getString("matricula"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCargo(rs.getString("cargo"));
                    usuario.setPerfil(rs.getString("perfil"));
                    Timestamp dataCriacao = (rs.getTimestamp("created"));
                    LocalDate dataLocal = dataCriacao.toLocalDateTime().toLocalDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    usuario.setCreated(dataLocal.format(formatter));
                    usuario.setTelefone(rs.getString("telefone"));

                    funcionarios.add(usuario);
                }
                return funcionarios;


        } catch (Exception e) {
            log.error("Erro ao listar funcionarios: {}", e.getMessage(), e);
            return null;
        }
    }
}
