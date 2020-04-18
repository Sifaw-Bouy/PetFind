package com.example.PetFind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PosterFragment extends Fragment {
    private EditText petname,pettype,ownstate,descrip;
    private ImageView petPic;
    private Button sendToFiler;
    private String userID;
    public String petPicture;
    private FirebaseAuth fAuth;// this gets the userID
    private FirebaseFirestore db;
    private DocumentReference docref;
    private DocumentReference getPic;
    private Map<String, Object> filerInfo= new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poster, container, false);
        //getting ids
        petname= view.findViewById(R.id.petNameID);
        pettype= view.findViewById(R.id.speciesID);
        ownstate= view.findViewById(R.id.statResID);
        descrip= view.findViewById(R.id.descripID);
        petPic= view.findViewById(R.id.petPicID);

        //firebase
        db= FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        docref= db.collection("filers").document(userID);
        getPic= db.collection("users").document(userID);
        sendToFiler = view.findViewById(R.id.sendTFiler);//button
        //get pet image in users collections call addonsnapshotlistner
        //getPic here:



        //sendFiler to database
        //call the buttons onclicklistener
        //sendToFiler here:

        return view;
    }
}
