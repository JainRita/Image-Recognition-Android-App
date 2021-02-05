package com.example.imageinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    public void txt_clk(View view) {
        startActivity(new Intent(MainActivity.this,TextActivity.class));
    }

    public void qr_clk(View view) {
        startActivity(new Intent(MainActivity.this,QRActivity.class));
    }

    public void face_clk(View view) {
        startActivity(new Intent(MainActivity.this,FaceActivity.class));
    }

    public void land_clk(View view) {
       startActivity(new Intent(MainActivity.this,MapsActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Do you want to leave?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog= alertDialog.create();
        dialog.show();
    }

    public void voice_clk(View view) {
        startActivity(new Intent(MainActivity.this,VoiceActivity.class));
    }
}
