package com.example.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ErroActivity extends AppCompatActivity {
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro);
        TextView tvErro = findViewById(R.id.tvErro);

        timer = new CountDownTimer(Repositorio.SEGUNDOS_ERRO * 1000L, 1000) {
            @Override
            public void onTick(long ms) {
                long s = (ms + 999) / 1000;
                tvErro.setText("Usuário ou senha incorretos\nTentativa " + Repositorio.erros
                        + " de " + Repositorio.MAX_ERROS + "\n\nVoltando ao login em " + s + "s");
            }

            @Override
            public void onFinish() {
                Util.irLogin(ErroActivity.this, " ");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) timer.cancel();
        super.onDestroy();
    }
}
