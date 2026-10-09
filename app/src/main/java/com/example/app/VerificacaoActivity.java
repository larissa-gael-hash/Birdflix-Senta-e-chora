package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

/**
 * V4 - Verificação por código enviado por e-mail.
 * O código vale 20 segundos; se não for usado, é invalidado e o usuário volta ao login.
 */
public class VerificacaoActivity extends AppCompatActivity {
    private String codigo;               // null = código invalidado
    private CountDownTimer timer;
    private EditText etCodigo;
    private TextView tvMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao);

        Usuario u = Repositorio.atual;
        if (u == null) { Util.irLogin(this, " "); return; }

        TextView tvInfo = findViewById(R.id.tvInfo);
        TextView tvContagem = findViewById(R.id.tvContagem);
        tvMensagem = findViewById(R.id.tvMensagem);
        etCodigo = findViewById(R.id.etCodigo);

        // Gera um código de 6 dígitos e envia por e-mail
        codigo = String.format("%06d", new Random().nextInt(1_000_000));
        EmailService.enviar(this, u.email, "Seu código de verificação",
                "Código: " + codigo + "\nVálido por " + Repositorio.SEGUNDOS_CODIGO + " segundos.");
        tvInfo.setText("Código enviado para " + Util.mascarar(u.email));

        // Contagem de 20s: ao zerar, invalida o código e volta ao login
        timer = new CountDownTimer(Repositorio.SEGUNDOS_CODIGO * 1000L, 1000) {
            @Override
            public void onTick(long ms) {
                tvContagem.setText("Expira em " + ((ms + 999) / 1000) + "s");
            }

            @Override
            public void onFinish() {
                codigo = null;
                Util.irLogin(VerificacaoActivity.this, "Código expirado. Faça login novamente.");
            }
        }.start();

        findViewById(R.id.btnConfirmar).setOnClickListener(v -> confirmar());
    }

    // Código correto: para o timer e abre a Home. Incorreto: avisa e o tempo continua correndo
    private void confirmar() {
        if (codigo != null && codigo.equals(etCodigo.getText().toString().trim())) {
            codigo = null;
            timer.cancel();
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("titulo", "Login realizado com sucesso!");
            startActivity(i);
            finish();
        } else {
            tvMensagem.setText("Código incorreto. O tempo continua correndo.");
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) timer.cancel();
        super.onDestroy();
    }
}
