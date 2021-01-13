package com.example.wttg.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wttg.Model.Post;
import com.example.wttg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    public UserListAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }


    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.userlist_item, parent, false);

        return new UserListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {


        holder.tv_facility.setText(mPosts.get(position).getFacility());
        holder.tv_admission.setText(String.valueOf(mPosts.get(position).getAdmission()));
        holder.tv_contact.setText(String.valueOf(mPosts.get(position).getFacContact()));

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_facility;
        TextView tv_admission;
        TextView tv_contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_facility = itemView.findViewById(R.id.tv_facility);
            tv_admission = itemView.findViewById(R.id.tv_admission);
            tv_contact = itemView.findViewById(R.id.tv_contact);
        }
    }
}
