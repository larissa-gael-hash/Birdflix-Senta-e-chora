package com.example.app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/** V3 - Recuperação de senha com código FIXO, usado sempre que o usuário solicitar a troca. */
public class RecuperacaoActivity extends AppCompatActivity {
    private EditText etEmail, etCodigo, etNovaSenha;
    private TextView tvMensagem;
    private String emailSolicitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacao);

        etEmail = findViewById(R.id.etEmail);
        etCodigo = findViewById(R.id.etCodigo);
        etNovaSenha = findViewById(R.id.etNovaSenha);
        tvMensagem = findViewById(R.id.tvMensagem);

        findViewById(R.id.btnEnviar).setOnClickListener(v -> enviarCodigo());
        findViewById(R.id.btnTrocar).setOnClickListener(v -> trocarSenha());
        findViewById(R.id.tvVoltar).setOnClickListener(v -> finish());
    }

    private void enviarCodigo() {
        Usuario u = Repositorio.porEmail(etEmail.getText().toString().trim());
        if (u == null) { tvMensagem.setText("E-mail não cadastrado."); return; }
        emailSolicitado = u.email;
        EmailService.enviar(this, u.email, "Recuperação de senha",
                "Seu código de recuperação: " + Repositorio.CODIGO_RECUPERACAO_FIXO);
        tvMensagem.setText("Código enviado. Informe-o abaixo.");
    }

    private void trocarSenha() {
        Usuario u = emailSolicitado == null ? null : Repositorio.porEmail(emailSolicitado);
        if (u == null) { tvMensagem.setText("Solicite o código primeiro."); return; }
        if (!Repositorio.CODIGO_RECUPERACAO_FIXO.equals(etCodigo.getText().toString().trim())) {
            tvMensagem.setText("Código incorreto.");
            return;
        }
        String nova = etNovaSenha.getText().toString();
        if (nova.isEmpty()) { tvMensagem.setText("Informe a nova senha."); return; }
        u.senha = nova;
        Util.irLogin(this, "Senha alterada. Faça login.");
    }
}
