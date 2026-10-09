package com.example.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class AberturaActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable irLogin = () -> Util.irLogin(this, " ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);
        Repositorio.erros = 0;   // contador zera ao voltar para a abertura
        handler.postDelayed(irLogin, Repositorio.SEGUNDOS_ABERTURA * 1000L);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(irLogin);
        super.onDestroy();
    }
}
