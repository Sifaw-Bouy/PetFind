package com.example.PetFind;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ContactFragment extends AppCompatActivity {
    private TextView ownerName,petName,disCrp,ownerEmail;
    private ImageView petPc;

    private Usermodel getUserInfo;// this helps get the user associated with the filer
    //this holds the user ID to get all the filer information related to this user
    private String userID;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private DocumentReference getEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);
        if(getIntent().hasExtra("userKey")){
            getUserInfo = getIntent().getParcelableExtra("userKey");

        }
        //getting the information from the xml fragment_contact.xml
        ownerName = findViewById(R.id.nameCont);
        petName= findViewById(R.id.contPetN);
        disCrp= findViewById(R.id.contDiscrip);
        ownerEmail= findViewById(R.id.contEmail);
        petPc = findViewById(R.id.contPImg);
        //get user and their filer
        assert getUserInfo != null;
        userID = getUserInfo.getUserID();
        docRef = db.collection("filers").document(userID);
        getEmail = db.collection("users").document(userID);
        //do setting the variables to their values
        /**
         * this sets the email from the user collection. call getEmail
         * */
        //...

        /**
         * this sets the name,species,description call docRef
         * */
        //...

    }
}
