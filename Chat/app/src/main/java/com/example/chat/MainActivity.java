package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Consulta o usuario atual
        AuthUser currentUser = Amplify.Auth.getCurrentUser();

        // Se o usuario for nulo, significa que não esta logado
        Intent tela;
        if(currentUser == null){
            // Tela de login
            tela = new Intent(getApplicationContext(), LoginActivity.class);
        } else{
            // Tela de conversa
            tela = new Intent(getApplicationContext(), ChatActivity.class);
        }
        startActivity(tela);

        // Encerra
        finish();


    }
}