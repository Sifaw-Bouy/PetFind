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

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText Email,Username,PassWord;
    private Button RegisterBtn;
    private TextView loginText;
    private String userID;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fbStore;
    private Map<String, Object> userInfo;
    private Map<String, Object> userFiler;
    private DocumentReference docRef;
    private DocumentReference docRefForFiler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email= findViewById(R.id.UserEmail);
        Username = findViewById(R.id.UserName);
        PassWord = findViewById(R.id.Password);
        RegisterBtn = findViewById(R.id.Register_button);
        loginText = findViewById(R.id.Login_txt);

        fAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                final String userN = Username.getText().toString();
                final String passW = PassWord.getText().toString().trim();

                //firestore database

                //Error checking
                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(userN)){
                    Username.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(passW)){
                    PassWord.setError("Password is Required");
                    return;
                }
                //FireBase registration
                fAuth.createUserWithEmailAndPassword(email, passW)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created", Toast.LENGTH_SHORT)
                                    .show();
                            userID = fAuth.getCurrentUser().getUid();
                            docRef= fbStore.collection("users").document(userID);
                            docRefForFiler = fbStore.collection("filers").document(userID);
                            userInfo= new HashMap<>();
                            userFiler = new HashMap<>();
                            userInfo.put("UserName",userN);
                            userInfo.put("Email",email);
                            userInfo.put("PassWord",passW);
                            userInfo.put("Profile Image", "");
                            userInfo.put("Pet Image","");
                            userFiler.put("Show",0);
                            userFiler.put("ID",userID);
                            userFiler.put("owner",userN);
                            userFiler.put("petType","");
                            userFiler.put("ownerState","");
                            userFiler.put("description","");
                            userFiler.put("petPicture","");
                            docRef.set(userInfo);
                            docRefForFiler.set(userFiler);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));




                        }else{
                            Toast.makeText(Register.this, "Error:" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }


}
