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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wttg.Model.Manager;
import com.example.wttg.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button btnlogin;
    TextView register_user, register_manager;

    RadioGroup radiogroup;
    RadioButton radio_user, radio_manager;
    String str_radio, str_email, str_password;

    FirebaseAuth auth;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radiogroup = findViewById(R.id.radio_group);
        radio_user = findViewById(R.id.radio_user);
        radio_manager = findViewById(R.id.radio_manager);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();

        btnlogin = findViewById(R.id.btnlogin);

        register_user = findViewById(R.id.register_user);
        register_manager = findViewById(R.id.register_manager);


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.radio_user) {
                    btnlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_email = email.getText().toString().trim();
                            str_password = password.getText().toString().trim();

                            UserLogin(str_email, str_password);
                        }
                    });
                    str_radio = radio_user.getText().toString();

                } else if (i == R.id.radio_manager) {
                    btnlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_email = email.getText().toString().trim();
                            str_password = password.getText().toString().trim();

                            ManagerLogin(str_email, str_password);
                        }
                    });
                    str_radio = radio_manager.getText().toString();
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_radio == null) {
                    Toast.makeText(MainActivity.this, "라디오 버튼을 선택해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //signup_user버튼 이동
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);
                startActivity(intent);
            }
        });

        //signup_manager버튼 이동
        register_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagerRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //로그인 메소드
    private void UserLogin(final String email, final String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "모든 정보를 기입해주세요!", Toast.LENGTH_SHORT).show();
        } else {  //1
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // 데이터체인지
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문

                        if (snapshot.child("email").getValue(String.class).equals(email)) {
                            count = 1;
                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() { //2
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) { //3
                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(MainActivity.this, "인증에 실패하였습니다! 계정을 다시 확인하세요!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            break;
                        }
                    } //반복문
                    if (count != 1) {
                        Toast.makeText(MainActivity.this, "계정이 존재하지 않습니다! 다시 확인하세요!", Toast.LENGTH_SHORT).show();
                    }
                } // 데이터체인지
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } //1
    }

    private void ManagerLogin(final String email, final String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "모든 정보를 기입해주세요!", Toast.LENGTH_SHORT).show();
        } else {  //1
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Managers");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // 데이터체인지
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문
                        if (snapshot.child("email").getValue(String.class).equals(email)) {
                            count = 1;
                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() { //2
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) { //3
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, ManagerHomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(MainActivity.this, "인증에 실패하였습니다! 계정을 다시 확인하세요!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            break;
                        }

                    } //반복문
                    if (count != 1) {
                        Toast.makeText(MainActivity.this, "계정이 존재하지 않습니다! 다시 확인하세요!", Toast.LENGTH_SHORT).show();
                    }
                } // 데이터체인지

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } //1
    }


}


