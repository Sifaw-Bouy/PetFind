package com.example.PetFind;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ContactFragment extends AppCompatActivity {
    private Usermodel getUserInfo;
    private String userID;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);
        if(getIntent().hasExtra("userKey")){
            getUserInfo = getIntent().getParcelableExtra("userKey");

        }

        userID = getUserInfo.getUserID();
        docRef = db.collection("filers").document(userID);


    }
}
