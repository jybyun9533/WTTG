package com.example.wttg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wttg.Model.Manager;
import com.example.wttg.Model.Post;
import com.example.wttg.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;

    DatabaseReference reference;

    String now = null,
            facility = null, userContact = null, facContact = null, birth = null, userid = null,
            current = null, result1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "취소되었습니다!", Toast.LENGTH_LONG).show();
                finish();

            } else {

                result1 = result.getContents();
                current = FirebaseAuth.getInstance().getCurrentUser().getUid();

                time();

                reference = FirebaseDatabase.getInstance().getReference();

                final String postid = reference.child("Post").push().getKey();

                final HashMap<String, Object> hashMap = new HashMap<>();

                // 각 변수 설정

                //유저
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Users").getChildren()) {
                            User user = snapshot.getValue(User.class);

                            if (user.getId().equals(result1)) {
                                userid = user.getName();
                                userContact = user.getContact();
                                birth = user.getBirth();

                                hashMap.put("user", userid);
                                hashMap.put("userContact", userContact);
                                hashMap.put("birth", birth);

                                reference.child("Post").child(postid).setValue(hashMap);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //메니저
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Managers").getChildren()) {
                            Manager manager = snapshot.getValue(Manager.class);
                            if (manager.getId().equals(current)) {
                                facility = manager.getName();
                                facContact = manager.getContact();

                                hashMap.put("facility", facility);
                                hashMap.put("facContact", facContact);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                hashMap.put("postid", postid);
                hashMap.put("userid", result1);
                hashMap.put("facid", current);
                hashMap.put("admission", now);

                //reference.child("Post").child(postid).setValue(hashMap);

                Toast.makeText(this, "입장처리되었습니다.", Toast.LENGTH_LONG).show();
                finish();


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void time() {
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        now = formatDate.format(date);
    }

   /* private void save_User() {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user.getId().equals(result1)) {
                        userid = user.getName();
                        userContact = user.getContact();
                        birth = user.getBirth();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void save_Manager() {

        reference = FirebaseDatabase.getInstance().getReference("Managers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Manager manager = snapshot.getValue(Manager.class);
                    if (manager.getId().equals(current)) {
                        facility = manager.getName();
                        facContact = manager.getContact();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }*/

}