package com.utehy.timviec247.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.utehy.timviec247.R;
import com.utehy.timviec247.utils.Common;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    TextView txtForgot, txtSignup;
    Button btnLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        onClick();
    }

    private void onClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private void login() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email không dược bỏ trống", Toast.LENGTH_LONG).show();
        }
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mật khẩu không dược bỏ trống", Toast.LENGTH_LONG).show();
        }
        if (!email.isEmpty() && !password.isEmpty()) {
            ProgressDialog dialog = new ProgressDialog(getApplicationContext());
            dialog.setTitle("Loading");
            dialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        Common.user = firebaseAuth.getCurrentUser();
                        startActivity(new Intent(getApplicationContext(), ParentActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    private void init() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtForgot = findViewById(R.id.txtForgotPassword);
        txtSignup = findViewById(R.id.txtSignup);
        btnLogin = findViewById(R.id.btnLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }

}