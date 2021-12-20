package com.amplifyframework.datastore.generated.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.example.chat.R;
import com.example.chat.VerificacaoActivity;

public class EntrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
    }

    public void onPressCadastrar(View view) {
        EditText email = findViewById(R.id.txtEmail);
        EditText senha = findViewById(R.id.txtPassword);
        Amplify.Auth.signUp(
                email.getText().toString(),
                senha.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(
                        AuthUserAttributeKey.email(),
                        email.getText().toString()
                ).build(),
                this::onEntrarSuccess,
                this::onEntrarError
        );
    }

    private void onEntrarSuccess(AuthSignUpResult authSignUpResult) {
        Intent telaVerif = new Intent(getApplicationContext(), VerificacaoActivity.class);

        // Retorna os valores inseridos para a verificação de e-mail
        EditText nome = findViewById(R.id.txtName);
        EditText email = findViewById(R.id.txtEmail);
        EditText senha = findViewById(R.id.txtPassword);
        telaVerif.putExtra("nome", nome.getText().toString());
        telaVerif.putExtra("email", email.getText().toString());
        telaVerif.putExtra("senha", senha.getText().toString());

        startActivity(telaVerif);
    }

    private void onEntrarError(AuthException e) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}