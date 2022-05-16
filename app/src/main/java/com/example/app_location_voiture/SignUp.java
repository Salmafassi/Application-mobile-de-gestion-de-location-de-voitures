package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app_location_voiture.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding bining;
private FirebaseAuth mAuth;
FirebaseDatabase database;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bining=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(bining.getRoot());
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressDialog=new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account..");
        progressDialog.setMessage("we're creating your account");
        bining.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String username=bining.username.getText().toString();
               String ageName=bining.agename.getText().toString();
               String tel=bining.phone.getText().toString();
               String Adress=bining.adresse.getText().toString();
               String Email=bining.email.getText().toString();
               String pwd=bining.passwd.getText().toString();
               String pwdC=bining.pass2.getText().toString();

                if (!username.isEmpty() && !ageName.isEmpty() && !ageName.isEmpty() && !tel.isEmpty() && !Adress.isEmpty() && !Email.isEmpty() && !pwd.isEmpty() && !pwdC.isEmpty()) {
                    progressDialog.show();

                    if(!pwd.equals(pwdC)){
                        progressDialog.dismiss();
                      Toast.makeText(SignUp.this, "two passwords are not identical", Toast.LENGTH_SHORT).show();
                  }
                   else{
                      mAuth.createUserWithEmailAndPassword(bining.email.getText().toString(),bining.passwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              progressDialog.dismiss();
                              if (task.isSuccessful()) {
                                  User user = new User(bining.username.getText().toString(), bining.agename.getText().toString(),bining.phone.getText().toString(),bining.adresse.getText().toString(),bining.passwd.toString(),bining.email.getText().toString());
                                  String id = task.getResult().getUser().getUid();
                                  database.getReference().child("Users").child(id).setValue(user);
                                  Toast.makeText(SignUp.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                  }

                } else {
                    Toast.makeText(SignUp.this, "Enter values..", Toast.LENGTH_SHORT).show();
                }
            }

        });
        bining.txtClickSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,SignIn.class);
                startActivity(intent);
            }
        });
    }
}