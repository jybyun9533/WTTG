package com.example.wttg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ManagerRegisterActivity extends AppCompatActivity {

    EditText name, email, password, contact;
    Button btn_singup;
    TextView txt_login;


    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        contact = findViewById(R.id.contact);
        btn_singup = findViewById(R.id.btn_singup);
        txt_login = findViewById(R.id.txt_login);

        auth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerRegisterActivity.this, MainActivity.class));
            }
        });


        btn_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_name = name.getText().toString();
                String str_contact = contact.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();


                if (TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_contact) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(ManagerRegisterActivity.this, "모든 정보를 기입해주세요!", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(ManagerRegisterActivity.this, "패스워드는 6자 이상!", Toast.LENGTH_SHORT).show();
                } else if (str_contact.length() != 11) {
                    Toast.makeText(ManagerRegisterActivity.this, "번호를 제대로 입력하세요!", Toast.LENGTH_SHORT).show();
                } else {
                    register(str_name, str_contact, str_email, str_password);
                }

            }
        });
    }

    private void register(final String name, final String contact, final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ManagerRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Managers").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("id", userid);
                            hashMap.put("name", name);
                            hashMap.put("contact", contact);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(ManagerRegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(ManagerRegisterActivity.this, "이메일 혹은 비밀번호가 잘못되었습니다!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
