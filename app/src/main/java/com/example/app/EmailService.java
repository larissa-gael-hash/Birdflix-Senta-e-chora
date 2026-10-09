package com.example.app;

import android.app.Activity;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;

/**
 * E-mail SIMULADO: mostra o conteúdo num diálogo e no Logcat.
 * Para envio real, chame um servidor seu (não coloque senha de SMTP dentro do app).
 */
public class EmailService {
    public static void enviar(Activity a, String para, String assunto, String corpo) {
        Log.d("EMAIL", "Para: " + para + " | " + assunto + " | " + corpo);
        new AlertDialog.Builder(a)
                .setTitle("E-mail (simulado)")
                .setMessage("Para: " + para + "\nAssunto: " + assunto + "\n\n" + corpo)
                .setPositiveButton("OK", null)
                .show();
    }
}
