package com.example.wttg.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wttg.Adapter.UserListAdapter;
import com.example.wttg.Model.Post;
import com.example.wttg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserHistoryFragment extends Fragment {

    String current;

    RecyclerView recyclerView;
    UserListAdapter userListAdapter;

    List<Post> postList;

    FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_history, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        current = firebaseUser.getUid();

        recyclerView = view.findViewById(R.id.recyclerView); // 아디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        /*linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);*/
        recyclerView.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)



        readPost();

        return view;
    }

    private void readPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if(post.getUserid().equals(current)) {
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                userListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userListAdapter = new UserListAdapter(getContext(), postList);
        recyclerView.setAdapter(userListAdapter);
    }


}
