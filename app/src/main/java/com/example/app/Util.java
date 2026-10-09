package com.example.app;

import android.content.Context;
import android.content.Intent;

public class Util {

    public static void irLogin(Context c, String msg) {
        Intent i = new Intent(c, LoginActivity.class);
        i.putExtra("msg", msg);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }

    public static void irAbertura(Context c) {
        Intent i = new Intent(c, AberturaActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }

    public static String mascarar(String email) {
        int i = email.indexOf('@');
        if (i <= 2) return email;
        StringBuilder sb = new StringBuilder(email.substring(0, 2));
        for (int k = 2; k < i; k++) sb.append('*');
        return sb.append(email.substring(i)).toString();
    }
}
