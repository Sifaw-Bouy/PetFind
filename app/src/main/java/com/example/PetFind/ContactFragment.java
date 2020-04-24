package com.example.PetFind;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
        getEmail.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    ownerEmail.setText(documentSnapshot.getData().get("Email").toString());
                }
            }
        });
        /**
         * this sets the name,species,description call docRef
         * */
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String url= documentSnapshot.getData().get("petPicture").toString();
                    Uri uri= Uri.parse(url);
                    petPc.setImageURI(uri);
                    ownerName.setText(documentSnapshot.getData().get("owner").toString());
                    petName.setText(documentSnapshot.getData().get("petName").toString());
                    disCrp.setText(documentSnapshot.getData().get("description").toString());
                    disCrp.setMovementMethod(new ScrollingMovementMethod());
                }
            }
        });

    }
}
