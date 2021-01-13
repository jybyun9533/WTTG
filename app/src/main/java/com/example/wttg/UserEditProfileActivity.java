package com.example.wttg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wttg.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserEditProfileActivity extends AppCompatActivity {

    EditText user_new_name, user_new_email, user_new_password, user_new_contact, user_new_birth;

    Button user_editprofile_comp;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        user_new_name = findViewById(R.id.useredit_name);
        //user_new_email = findViewById(R.id.useredit_email);
        //user_new_password = findViewById(R.id.useredit_password);
        user_new_contact = findViewById(R.id.useredit_contact);
        user_new_birth = findViewById(R.id.useredit_birth);

        user_editprofile_comp = findViewById(R.id.btnusereditprofilecomplet);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user_new_name.setText(user.getName());
                user_new_contact.setText(user.getContact());
                user_new_birth.setText(user.getBirth());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        user_editprofile_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(user_new_name.getText().toString(), user_new_contact.getText().toString(), user_new_birth.getText().toString());
            }
        });
    }
    private void updateProfile(String name, String contact, String birth){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> newhashmap = new HashMap<>();
        newhashmap.put("name", name);
        newhashmap.put("contact", contact);
        newhashmap.put("birth", birth);

        reference.updateChildren(newhashmap);

    }
}
