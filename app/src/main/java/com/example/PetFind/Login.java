package com.example.PetFind;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private EditText EmailLogin, Password;
    private Button LoginButton;
    private TextView CreateBtn;
    private FirebaseAuth fAuth;
    private String userID;
    private DocumentReference docRef;
    private FirebaseFirestore fbStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailLogin = findViewById(R.id.EmailLogin);
        Password = findViewById(R.id.LoginPass);
        LoginButton = findViewById(R.id.LoginBtn);
        CreateBtn = findViewById(R.id.CreateAct);
        fAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserN= EmailLogin.getText().toString().trim();
                String PassW= Password.getText().toString().trim();

                if(TextUtils.isEmpty(UserN)){
                    EmailLogin.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(PassW)){
                    Password.setError("Password is Required");
                    return;
                }
                //authenticate user

                fAuth.signInWithEmailAndPassword(UserN, PassW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "User Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else{
                            Toast.makeText(Login.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }





}
