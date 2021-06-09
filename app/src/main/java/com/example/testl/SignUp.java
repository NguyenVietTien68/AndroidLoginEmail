package com.example.testl;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private EditText edEmail, edPass;
    private TextView tvQuayLai;
    private Button btnSignup;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edvEmail);
        edPass = findViewById(R.id.edvPassword);
        btnSignup = findViewById(R.id.btnDangKy);
        tvQuayLai = findViewById(R.id.tvQuayLai);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });

        tvQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignUp.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void Signup() {
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Chua nhap email",Toast.LENGTH_SHORT).show();
            return;
        }if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Chua nhap password",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this,"Dang ky thanh cong",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUp.this,"Dang ky khong thanh cong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
