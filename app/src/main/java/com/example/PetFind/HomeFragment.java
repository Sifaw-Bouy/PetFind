package com.example.PetFind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment{
    private Button logoutB;
    private Button uploadPetImg;
    private ImageView userImage;
    private ImageView petImg;
    private ImageView editUserInfo;
    private TextView userName;
    private TextView userEmail;
    private ImageView EditProfImg;
    //
    private StorageReference UserProfilePicRef;
    private String userID;
    private int reqCodeProf = 1;
    private int reqCodePet = 2;
    private Map<String, Object> mapImg= new HashMap<>();
    private FirebaseAuth fAuth;// this gets the userID
    private FirebaseFirestore fbStore;// this goes to where all the user uniformation is located
    private FirebaseStorage fStorage;// this is where the images are located
    private DocumentReference docRef;//this is what's used to update
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        logoutB = view.findViewById(R.id.Logout_Btn);
        uploadPetImg = view.findViewById(R.id.uploadPet);
        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), Login.class));
                }catch (Exception er){}
            }
        });
        //user information
        userImage = view.findViewById(R.id.ProfilePic);
        petImg = view.findViewById(R.id.petImg);
        editUserInfo = view.findViewById(R.id.editUser);
        EditProfImg = view.findViewById(R.id.EditProfilePic);
        userName = view.findViewById(R.id.dispName);
        userEmail = view.findViewById(R.id.dispEmail);

        //fireBase storage/database
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();//getting the userid
        fStorage = FirebaseStorage.getInstance();//getting the storage for images
        fbStore = FirebaseFirestore.getInstance();// getting user dict.
        setProfInfo();//sets the user info everytime this fargment is called
        //
        UserProfilePicRef = fStorage.getReference().child("Profile Image");//entering image dic.
        EditProfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent cropP= CropImage.activity()
                       .setGuidelines(CropImageView.Guidelines.ON)
                       .setAspectRatio(1,1)
                       .setMultiTouchEnabled(true)
                       .getIntent(getContext());
                startActivityForResult(cropP, reqCodeProf);
            }
        });
        uploadPetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cropP= CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .setMultiTouchEnabled(true)
                        .getIntent(getContext());
                startActivityForResult(cropP, reqCodePet);
            }
        });
        updateUserInfo();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                setProfileImages(userImage,resultUri);
                sendToFireStorage(resultUri,"Profile Image");

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), "Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 2){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                setProfileImages(petImg,resultUri);
                sendToFireStorage(resultUri,"Pet Image");

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), "Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Sending images to fireStorage
     * */
    private void sendToFireStorage(final Uri profileImg, final String key) {
        final StorageReference filepath = UserProfilePicRef.child(userID+" "+key);
        filepath.putFile(profileImg).addOnCompleteListener(
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),
                                    key, Toast.LENGTH_SHORT).show();
                            docRef = fbStore.collection("users").document(userID);
                            mapImg.put(key,profileImg.toString());
                            docRef.update(mapImg);
                        }else{
                            String errorMsg= task.getException().toString();
                            Toast.makeText(getActivity(),
                                    "Error: "+errorMsg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }
    /** gets the user info to show into the fargment parts
    * */
    private void setProfInfo(){
        //go to the database in the users and check if they have profile pic set
        //if so set it whenever the user comes back
        docRef = fbStore.collection("users").document(userID);
        try {
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        if (documentSnapshot.getData().get("Profile Image") != null) {

                            String url = documentSnapshot.getData().get("Profile Image").toString();
                            String url2= documentSnapshot.getData().get("Pet Image").toString();
                            Uri ur = Uri.parse(url);
                            Uri ur2 = Uri.parse(url2);
                            Picasso.get().load(ur).into(userImage);
                            Picasso.get().load(ur2).into(petImg);
                            userName.setText(documentSnapshot.getData().get("UserName").toString());
                            userEmail.setText(documentSnapshot.getData().get("Email").toString());

                        }
                    }
                }
            });
        }catch (Exception er){}
    }
    /** setting user/pet images
     * */
    private void setProfileImages(ImageView pic, Uri picInfo){
        pic.setImageURI(picInfo);
    }
    /** calls the edit user class to edit user info
     * */
    private  void updateUserInfo(){
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditInfo.class));
            }
        });
    }
}
