package com.example.PetFind;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditInfo extends AppCompatActivity {
    private EditText name,email,pass;
    private Button upName,upEmail,upPass,removeP;
    private Button returnHome;
    private String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fbStore;

    private Map<String, Object> userInfo = new HashMap<>();//to put into database dictionary
    private DocumentReference docRef;//the database dictonary
    private DocumentReference remPos;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);
        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPass);
        //buttons
        upName = findViewById(R.id.nameUpd);
        upEmail = findViewById(R.id.emailUpd);
        upPass = findViewById(R.id.passUpd);
        returnHome = findViewById(R.id.goHome);
        removeP= findViewById(R.id.deleteFlier);
        //firebase
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        fbStore = FirebaseFirestore.getInstance();

        //do stuff here
        upName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uName= name.getText().toString();
                //then update firebase
                if(!TextUtils.isEmpty(uName)){
                    updateInfo(uName, "UserName");
                }else{name.setError("Name is required!");}
            }
        });
        upEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uEmail= email.getText().toString();
                //then update firebase
                if(!TextUtils.isEmpty(uEmail)){
                    updateInfo(uEmail, "Email");
                }else{email.setError("Email is required!");}

            }
        });
        upPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uPass= pass.getText().toString();
                //then update firebase
                if(!TextUtils.isEmpty(uPass)){
                    updateInfo(uPass, "PassWord");
                }else{pass.setError("Password is required!");}

            }
        });
        removeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePost();
            }
        });
        setReturnHome();
    }
    private void setReturnHome(){
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    private void updateInfo(String a, final String key){
        docRef = fbStore.collection("users").document(userID);
        try {
            userInfo.put(key,a);
            if(key == "PassWord"){
                FirebaseUser user = fAuth.getCurrentUser();
                user.updatePassword(a);
            }if(key == "Email"){
                FirebaseUser user = fAuth.getCurrentUser();
                user.updateEmail(a);
            }
            docRef.update(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),
                                key.toLowerCase()+" is updated", Toast.LENGTH_SHORT).show();
                    }else{
                        //error
                    }
                }
            });

        }catch (Exception e){}
    }
    private void removePost(){
        remPos = fbStore.collection("filers").document(userID);
        Map<String, Object> rem= new HashMap<>();
        rem.put("Show",0);
        remPos.update(rem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),
                            "Post is Removed", Toast.LENGTH_SHORT).show();
                }else{
                    //error
                }
            }
        });
    }
}
