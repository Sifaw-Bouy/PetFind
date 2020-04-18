package com.example.PetFind;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements UserAdapter.OnUserListener {
    private RecyclerView mRecView;
    private RecyclerView.Adapter mAdapter;
    private UserAdapter usAdapter;
    private RecyclerView.LayoutManager mLayManger;
    public ArrayList<Usermodel> userFilerInfo;
    public ArrayList<Usermodel> filteredList;
    //firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference docref;
    //search filter
    private EditText searchInput;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //here I need to go into firestorage and grap the filer which has the infromation
        // then set the information to each new instance of Useritems
        ArrayList<Usermodel> userInfom = new ArrayList<>();//this acts as a dummy list
        docref = db.collection("filers");
        searchInput = view.findViewById(R.id.searchFilter);
        //add the on addTextChangeListener here and use afterTextCHanged then call filter
        //......

        docref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            ArrayList<Usermodel> userInfo = new ArrayList<>();
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //feed information to display
                if(task.isSuccessful()){
                    String name;
                    String pet;
                    String stat;
                    String pic;
                    String id;
                    int index=0;
                    List<DocumentSnapshot> userList = task.getResult().getDocuments();
                    for(index=0;index<userList.size();index++){
                        int add = userList.get(index).get("Show").hashCode();
                        if(add==1) {//only shows filers that are needed
                            name = userList.get(index).get("owner").toString();
                            pet = userList.get(index).get("petType").toString();
                            stat = userList.get(index).get("ownerState").toString();
                            pic = userList.get(index).get("petPicture").toString();
                            id = userList.get(index).get("ID").toString();
                            userInfo.add(new Usermodel(name, pet, stat, pic,id));
                        }
                    }
                }
                userFilerInfo=userInfo;
                mAdapter = new UserAdapter(userInfo,SearchFragment.this);
                usAdapter= (UserAdapter) mAdapter;
                mRecView.setLayoutManager(mLayManger);
                mRecView.setAdapter(usAdapter);

            }

        });
        mRecView = view.findViewById(R.id.userFeedView);
        mRecView.setHasFixedSize(true);
        mLayManger= new LinearLayoutManager(getActivity());
        mAdapter= new UserAdapter(userInfom,this);
        //need this so it can actually show the information
        mRecView.setLayoutManager(mLayManger);
        mRecView.setAdapter(mAdapter);

        return view;
    }
    /**
     *  add filter method here
     * */
    //......

    //takes you the contact information activity
    @Override
    public void onUserClick(int position) {
        Intent intent=new Intent(getActivity(),ContactFragment.class);
        intent.putExtra("userKey",userFilerInfo.get(position));
        startActivity(intent);
    }

}