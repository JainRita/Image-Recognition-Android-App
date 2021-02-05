package com.example.imageinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {
    private EditText txt;
    private Button feedback;
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        txt=findViewById(R.id.editText);
        feedback=findViewById(R.id.feed);
        mAuth=FirebaseAuth.getInstance();

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = txt.getText().toString();
                if (TextUtils.isEmpty(a)) {
                    Toast.makeText(FeedbackActivity.this, "NO Feedback", Toast.LENGTH_LONG).show();
                }else{
                    sentToDatabase();
                }
            }
        });
    }

    private void sentToDatabase() {
        Toast.makeText(FeedbackActivity.this,"Feedback Sent",Toast.LENGTH_LONG).show();
        String a=txt.getText().toString();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        String userid=firebaseUser.getUid();
        mReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Feedback");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("Issues:",a);
        mReference.setValue(hashMap);
    }
}
