package com.utehy.timviec247.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.TokenWatcher;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.utehy.timviec247.R;
import com.utehy.timviec247.models.Account;
import com.utehy.timviec247.utils.Common;

public class SignUpActivity extends AppCompatActivity {

    EditText edtFullName, edtEmail, edtPassword, edtRePassword;
    Button btnSignup;
    TextView txtLogin;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        onClick();
    }

    private void onClick() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void signup() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String rePassword = edtRePassword.getText().toString().trim();
        if (fullName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập họ tên", Toast.LENGTH_LONG).show();
        } else if (!email.contains("@")) {
            Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_LONG).show();
        } else if (password.isEmpty() || rePassword.isEmpty() || password.compareToIgnoreCase(rePassword) == 1) {
            Toast.makeText(getApplicationContext(), "Mật khẩu không hợp lệ", Toast.LENGTH_LONG).show();
        } else {
            ProgressDialog dialog = new ProgressDialog(getApplicationContext());
            dialog.setTitle("Loading");
            dialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Account account = new Account(user.getUid(), user.getEmail(), email);
                                reference.child(account.getId()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_LONG).show();
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
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);
        btnSignup = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.txtLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Account");
    }
}