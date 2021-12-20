package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.User;
import com.example.chat.R;

public class VerificacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao);
    }

    public void onPressVerificar(View view) {
        EditText viewById = findViewById(R.id.txtConfirmationCode);
        Amplify.Auth.confirmSignUp(
                getEmail(),
                viewById.getText().toString(),
                this::onVerifSuccess,
                this::onError
        );
    }

    private void onVerifSuccess(AuthSignUpResult authSignUpResult) {
        // Usando os dados para logar
        String email = getEmail();
        String senha = getSenha();

        Amplify.Auth.signIn(
                email,
                senha,
                this::onLoginSuccess,
                this::onError
        );
    }

    private String getName() {
        return getIntent().getStringExtra("nome");
    }

    private String getEmail() {
        return getIntent().getStringExtra("email");
    }

    private String getSenha() {
        return getIntent().getStringExtra("senha");
    }

    private void onLoginSuccess(AuthSignInResult authSignInResult) {
        String userId = Amplify.Auth.getCurrentUser().getUserId();
        String nome = getName();

        // Salva os dados no AWSDataStore
        Amplify.DataStore.save(
                User.builder().id(userId).name(nome).build(),
                this::onSavarSuccess,
                this::onError
        );

    }

    private <T extends Model> void onSavarSuccess(DataStoreItemChange<T> tDataStoreItemChange) {
        Intent telaChat = new Intent(this, ChatActivity.class);
        startActivity(telaChat);
    }

    private void onError(DataStoreException e) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private void onError(AuthException e) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

}