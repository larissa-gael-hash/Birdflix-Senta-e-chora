package com.example.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

/** V2 - Cadastro. Ao concluir, mostra a home com os dados cadastrados. */
public class CadastroActivity extends AppCompatActivity {
    private EditText etNome, etCpf, etTelefone, etEmail, etUsuario, etSenha, etConfirmacao;
    private ImageView ivFoto;
    private TextView tvMensagem;
    private String fotoUri;

    private final ActivityResultLauncher<String> seletorFoto = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    fotoUri = uri.toString();
                    ivFoto.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.etNome);
        etCpf = findViewById(R.id.etCpf);
        etTelefone = findViewById(R.id.etTelefone);
        etEmail = findViewById(R.id.etEmail);
        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        etConfirmacao = findViewById(R.id.etConfirmacao);
        ivFoto = findViewById(R.id.ivFoto);
        tvMensagem = findViewById(R.id.tvMensagem);

        findViewById(R.id.btnFoto).setOnClickListener(v -> seletorFoto.launch("image/*"));
        findViewById(R.id.btnCadastrar).setOnClickListener(v -> cadastrar());
        findViewById(R.id.tvVoltar).setOnClickListener(v -> finish());
    }

    private void cadastrar() {
        String nome = etNome.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String tel = etTelefone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String user = etUsuario.getText().toString().trim();
        String s1 = etSenha.getText().toString();
        String s2 = etConfirmacao.getText().toString();

        String erro = null;
        if (nome.isEmpty() || cpf.isEmpty() || tel.isEmpty() || email.isEmpty() || user.isEmpty() || s1.isEmpty())
            erro = "Preencha todos os campos.";
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) erro = "E-mail inválido.";
        else if (!cpf.replaceAll("\\D", "").matches("\\d{11}")) erro = "CPF deve ter 11 dígitos.";
        else if (!s1.equals(s2)) erro = "As senhas não conferem.";
        else if (Repositorio.usuarios.containsKey(user)) erro = "Usuário já existe.";
        if (erro != null) { tvMensagem.setText(erro); return; }

        Usuario u = new Usuario();
        u.nome = nome; u.cpf = cpf; u.telefone = tel; u.email = email;
        u.usuario = user; u.senha = s1; u.foto = fotoUri;
        Repositorio.usuarios.put(user, u);
        Repositorio.atual = u;

        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("titulo", "Cadastro concluído!");
        startActivity(i);
        finish();
    }
}
