package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("titulo", "Login realizado com sucesso!");
            startActivity(i);
            finish();
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
