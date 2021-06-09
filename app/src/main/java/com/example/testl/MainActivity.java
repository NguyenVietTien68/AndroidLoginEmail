package com.example.testl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText edEmail, edPass;
    private Button btnSignin,btnSignup;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edEmailMain1);
        edPass = findViewById(R.id.edPassMain);
        btnSignin = findViewById(R.id.btnSignInMain);
        btnSignup = findViewById(R.id.btnSignUpMain);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
    private void login() {
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Chua nhap email",Toast.LENGTH_SHORT).show();
            return;
        }if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Chua nhap password",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,MainChinh.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this,"Dang nhap khong thanh cong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
