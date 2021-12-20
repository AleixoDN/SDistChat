package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.EntrarActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onPressLogin(View view) {
        EditText email = findViewById(R.id.txtEmail);
        EditText senha = findViewById(R.id.txtPassword);

        Amplify.Auth.signIn(
                email.getText().toString(),
                senha.getText().toString(),
                this::onLoginSuccess,
                this::onLoginError
        );

    }

    private void onLoginSuccess(AuthSignInResult authSignInResult) {
        Intent telaChat =  new Intent(this, ChatActivity.class);
        startActivity(telaChat);
    }

    private void onLoginError(AuthException e) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });

    }

    public void onPressEntrar(View view) {
        Intent telaEntrar = new Intent(this, EntrarActivity.class);
        startActivity(telaEntrar);
    }
}