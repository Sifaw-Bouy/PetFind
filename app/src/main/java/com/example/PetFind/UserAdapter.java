package com.example.PetFind;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<Usermodel> mUserM;
    private OnUserListener mOnUserLis;

    public static class UserViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView ownerN;
        private TextView petType;
        private TextView ownerState;
        private ImageView petPicture;
        private OnUserListener onUserLis;
        private UserViewHolder(@NonNull View itemView,OnUserListener onUserListener) {
            super(itemView);
            ownerN= itemView.findViewById(R.id.ownerName);
            petType= itemView.findViewById(R.id.petSpecies);
            ownerState=itemView.findViewById(R.id.userState);
            petPicture=itemView.findViewById(R.id.petimgV);
            //onclick event handling
            this.onUserLis = onUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserLis.onUserClick(getAdapterPosition());
        }
    }

    public UserAdapter(ArrayList<Usermodel> userM, OnUserListener onUserListener){
        mUserM = userM;
        mOnUserLis = onUserListener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usercard_info,parent,false);

        return new UserViewHolder(view,mOnUserLis);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Usermodel currentUser= mUserM.get(position);
        holder.ownerN.setText(currentUser.getOwner());
        holder.petType.setText(currentUser.getPetType());
        holder.ownerState.setText(currentUser.getOwnerState());
        try {
            holder.petPicture.setImageURI(currentUser.getPetPicture());
        }catch (NullPointerException e){}
    }

    @Override
    public int getItemCount() {
        return mUserM.size();
    }

    public interface OnUserListener{
        void onUserClick(int position);
    }
    public void filterList(ArrayList<Usermodel> filterEd){
        mUserM = filterEd;
        notifyDataSetChanged();
    }

}
