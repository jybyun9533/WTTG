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

public class ManagerEditProfileActivity extends AppCompatActivity {

    EditText manager_new_name, manager_new_email, manager_new_password, manager_new_contact;

    Button manager_editprofile_comp;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_profile);

        manager_new_name = findViewById(R.id.manageredit_name);
        //manager_new_email = findViewById(R.id.useredit_email);
        //manager_new_password = findViewById(R.id.useredit_password);
        manager_new_contact = findViewById(R.id.manageredit_contact);

        manager_editprofile_comp = findViewById(R.id.btnmanagereditprofilecomplet);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Managers").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                manager_new_name.setText(user.getName());
                manager_new_contact.setText(user.getContact());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        manager_editprofile_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(manager_new_name.getText().toString(), manager_new_contact.getText().toString());
            }
        });
    }
    private void updateProfile(String name, String contact){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Managers").child(firebaseUser.getUid());

        HashMap<String, Object> newhashmap = new HashMap<>();
        newhashmap.put("name", name);
        newhashmap.put("contact", contact);

        reference.updateChildren(newhashmap);
    }
}
