package com.example.PetFind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ChatFragment extends Fragment {
    private TextView userName;
    private TextView userEmail;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fbStore;
    private FirebaseStorage fStorage;
    private DocumentReference docRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        userName = view.findViewById(R.id.userName);
        return view;

    }

}
