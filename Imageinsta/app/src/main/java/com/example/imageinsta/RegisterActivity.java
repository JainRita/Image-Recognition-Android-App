package com.example.imageinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    EditText username,email,pass, cnfpass;
    Button register;
    TextView txt_login;

    FirebaseAuth mAuth;
    DatabaseReference mReference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.paswd);
        cnfpass=findViewById(R.id.cnfmpaswd);
        register=findViewById(R.id.register);
        txt_login=findViewById(R.id.txt_login);

        mAuth=FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please Wait..");
                pd.show();

                String user=username.getText().toString();
                String mail=email.getText().toString();
                String passwd=pass.getText().toString();
                String cnf=cnfpass.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(cnf)) {
                    Toast.makeText(RegisterActivity.this,"All fields are not registered",Toast.LENGTH_SHORT).show();
                }if(passwd.length()<6){
                    Toast.makeText(RegisterActivity.this,"Password must have 6 characters",Toast.LENGTH_SHORT).show();
                }if(!passwd.equals(cnf)){
                    Toast.makeText(RegisterActivity.this,"Both passwords dont match",Toast.LENGTH_SHORT).show();
                }if(!mail.isEmpty()){
                    if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                        Toast.makeText(RegisterActivity.this,"Invalid email address",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    register(user,mail,passwd,cnf);
                }
            }
        });
    }

    private void register(final String muser, final String memail, final String mpass, String mcnf){
        mAuth.createUserWithEmailAndPassword(memail,mpass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                    String userid=firebaseUser.getUid();
                    mReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("User: ",muser.toLowerCase());
                    hashMap.put("Email: ",memail);
                    hashMap.put("Password: ",mpass);
                    hashMap.put("Imageurl: ","https://firebasestorage.googleapis.com/v0/b/imageinsta-68b7c.appspot.com/o/user.png?alt=media&token=d8fb1d77-902a-49a1-97db-997e62b06a25");

                    mReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this,"You can't register with this email or password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
