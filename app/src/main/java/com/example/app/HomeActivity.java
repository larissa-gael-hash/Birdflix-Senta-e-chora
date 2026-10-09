package com.example.app;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Usuario u = Repositorio.atual;
        if (u == null) { Util.irLogin(this, " "); return; }

        String titulo = getIntent().getStringExtra("titulo");
        ((TextView) findViewById(R.id.tvTitulo)).setText(titulo == null ? "Bem-vindo!" : titulo);
        ((TextView) findViewById(R.id.tvNome)).setText("Nome: " + u.nome);
        ((TextView) findViewById(R.id.tvUsuario)).setText("Usuário: " + u.usuario);
        ((TextView) findViewById(R.id.tvEmail)).setText("E-mail: " + u.email);
        ((TextView) findViewById(R.id.tvTelefone)).setText("Telefone: " + u.telefone);
        ((TextView) findViewById(R.id.tvCpf)).setText("CPF: " + u.cpf);
        ((TextView) findViewById(R.id.tvSenha)).setText("Senha: " + "*".repeat(u.senha.length()));

        ImageView ivFoto = findViewById(R.id.ivFoto);
        if (u.foto != null) ivFoto.setImageURI(Uri.parse(u.foto));

        findViewById(R.id.btnSair).setOnClickListener(v -> {
            Repositorio.atual = null;
            Util.irLogin(this, " ");
        });
    }
}
