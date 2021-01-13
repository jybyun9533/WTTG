package com.example.wttg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wttg.Fragment.ManagerHistoryFragment;
import com.example.wttg.Model.Post;
import com.example.wttg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.CustomViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    private FirebaseUser firebaseUser;

    public ManagerListAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public ManagerListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.managerlist_item, parent, false);

        return new ManagerListAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerListAdapter.CustomViewHolder holder, int position) {


        holder.tv_user.setText(mPosts.get(position).getUser() + "(" +mPosts.get(position).getBirth() + ")");
        holder.tv_admission.setText(String.valueOf(mPosts.get(position).getAdmission()));
        holder.tv_contact.setText(String.valueOf(mPosts.get(position).getUserContact()));

    }


    public int getItemCount() {
        return mPosts.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user;
        TextView tv_admission;
        TextView tv_contact;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_user = itemView.findViewById(R.id.tv_user);
            this.tv_admission = itemView.findViewById(R.id.tv_admission);
            this.tv_contact = itemView.findViewById(R.id.tv_contact);
        }
    }
}
