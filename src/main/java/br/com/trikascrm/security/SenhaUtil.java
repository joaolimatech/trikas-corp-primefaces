package br.com.trikascrm.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public final class SenhaUtil {

    private static final int CUSTO = 12;

    private SenhaUtil() {
    }

    public static String gerarHash(String senhaPura) {
        return BCrypt.withDefaults()
                .hashToString(CUSTO, senhaPura.toCharArray());
    }

    public static boolean verificarSenha(String senhaPura, String hashSalvo) {
        if (senhaPura == null || hashSalvo == null || hashSalvo.isBlank()) {
            return false;
        }

        BCrypt.Result resultado = BCrypt.verifyer()
                .verify(senhaPura.toCharArray(), hashSalvo);

        return resultado.verified;
    }
}
