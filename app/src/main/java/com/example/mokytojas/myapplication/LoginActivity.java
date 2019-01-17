package com.example.mokytojas.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.loginTitle);


        final EditText login = findViewById(R.id.textUsername);
        final EditText password = findViewById(R.id.textPassword);
        CheckBox remember = findViewById(R.id.checkBoxRemember);
        Button loginBtn = findViewById(R.id.btnLogin);
        Button registerBtn = findViewById(R.id.btnRegister);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLogin = login.getText().toString();
                String userPassword = password.getText().toString();

                User person = new User(userLogin, userPassword);

                login.setError(null);
                password.setError(null);

                if (Validation.isValidUsername(person.getUsername()) && Validation.isValidPassword(person.getPassword())) {
                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);
                } else {
                    login.setError(getResources().getString(R.string.loginError));
                    login.requestFocus();
                }
            }
        });
    }
}