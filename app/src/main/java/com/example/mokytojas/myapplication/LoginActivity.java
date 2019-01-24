package com.example.mokytojas.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.loginTitle);


        final EditText login = findViewById(R.id.textUsername);
        final EditText password = findViewById(R.id.textPassword);
        final CheckBox remember = findViewById(R.id.checkBoxRemember);
        Button loginBtn = findViewById(R.id.btnLogin);
        Button registerBtn = findViewById(R.id.btnRegister);

        final User person = new User(LoginActivity.this);

        remember.setChecked(person.isRemembered());

        if(remember.isChecked()){
            login.setText(person.getUsernameForLogin(), TextView.BufferType.EDITABLE);
            password.setText(person.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        } else {
            login.setText("", TextView.BufferType.EDITABLE);
            password.setText("", TextView.BufferType.EDITABLE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLogin = login.getText().toString();
                String userPassword = password.getText().toString();

                login.setError(null);
                password.setError(null);

                if (Validation.isValidUsername(userLogin) && Validation.isValidPassword(userPassword)) {
                    person.setUsernameForLogin(userLogin);
                    person.setPasswordForLogin(userPassword);
                    if (remember.isChecked()){
                        person.setRememberMeKey(true);
                    } else {
                        person.setRememberMeKey(false);
                    }
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