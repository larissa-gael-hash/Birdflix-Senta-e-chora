package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/** V4 - Login com links para cadastro e recuperação de senha; login correto exige o código de verificação. Acerto leva à Home; erro à tela de erro; 3º erro à abertura. */
public class LoginActivity extends AppCompatActivity {
    private EditText etUsuario, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        TextView tvMensagem = findViewById(R.id.tvMensagem);
        Button btnEntrar = findViewById(R.id.btnEntrar);

        String msg = getIntent().getStringExtra("msg");
        tvMensagem.setText(msg == null ? " " : msg);

        btnEntrar.setOnClickListener(v -> tentarLogin());
        findViewById(R.id.tvCadastro).setOnClickListener(
                v -> startActivity(new Intent(this, CadastroActivity.class)));
        findViewById(R.id.tvRecuperar).setOnClickListener(
                v -> startActivity(new Intent(this, RecuperacaoActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        etSenha.setText("");
    }

    private void tentarLogin() {
        Usuario u = Repositorio.usuarios.get(etUsuario.getText().toString().trim());
        String senha = etSenha.getText().toString();
        if (u != null && u.senha.equals(senha)) {
            Repositorio.erros = 0;
            Repositorio.atual = u;
            // Credenciais corretas: segue para a verificação por código (20s)
            startActivity(new Intent(this, VerificacaoActivity.class));
        } else {
            Repositorio.erros++;
            if (Repositorio.erros >= Repositorio.MAX_ERROS) {
                Util.irAbertura(this);                                   // 3º erro
            } else {
                startActivity(new Intent(this, ErroActivity.class));
            }
        }
    }
}
