package com.example.PetFind;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
        getPic.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String url= documentSnapshot.getData().get("Pet Image").toString();
                    petPicture=url;
                    Uri uri= Uri.parse(url);
                    petPic.setImageURI(uri);
                }
            }
        });
        //sendFiler to database
        sendToFiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pName= petname.getText().toString().trim();//send to users collection
                final String pType= pettype.getText().toString().trim();
                final String oState= ownstate.getText().toString().trim();
                final String desCrp= descrip.getText().toString();
                if(TextUtils.isEmpty(pName)){
                    petname.setError("Pet name required!");
                }if(TextUtils.isEmpty(pType)){
                    pettype.setError("Pet species required!");
                }if(TextUtils.isEmpty(oState)){
                    ownstate.setError("State name required!");
                }if(TextUtils.isEmpty(desCrp)){
                    descrip.setError("Description name required!");
                }
                filerInfo.put("Show",1);
                filerInfo.put("petName",pName);
                filerInfo.put("petType",pType);
                filerInfo.put("ownerState",oState);
                filerInfo.put("description",desCrp);
                filerInfo.put("petPicture",petPicture);
                docref.update(filerInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Filer Uploaded",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        return view;
    }
}
