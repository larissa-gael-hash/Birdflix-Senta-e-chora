package com.example.app;

import java.util.HashMap;
import java.util.Map;

public class Repositorio {
    public static final int SEGUNDOS_ABERTURA = 5;
    public static final int SEGUNDOS_ERRO = 5;      // valor assumido
    public static final int SEGUNDOS_CODIGO = 20;
    public static final int MAX_ERROS = 3;
    public static final String CODIGO_RECUPERACAO_FIXO = "123456";

    public static final Map<String, Usuario> usuarios = new HashMap<>();
    public static int erros = 0;
    public static Usuario atual;

    static {
        Usuario admin = new Usuario();
        admin.telefone = "11999999999";
        admin.email = "admin@exemplo.com";
        admin.usuario = "admin";
        admin.senha = "admin123";
        admin.cpf = "00000000000";
        admin.nome = "Administrador";
        usuarios.put("admin", admin);
    }

    public static Usuario porEmail(String email) {
        for (Usuario u : usuarios.values()) {
            if (u.email.equalsIgnoreCase(email)) return u;
        }
        return null;
    }
}
