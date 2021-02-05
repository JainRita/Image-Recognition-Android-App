package com.example.imageinsta;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VoiceActivity extends AppCompatActivity {
    private TextToSpeech mytts;
    private SpeechRecognizer mySpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechRecognizer.startListening(intent);
            }
        });
        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (mytts.getEngines().size()==0){
                    Toast.makeText(VoiceActivity.this,"There is no TTS engine on your device",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    mytts.setLanguage(Locale.US);
                    speak("Hello I am Ready");
                }
            }
        });
    }

    public void speak(String message){
        if(Build.VERSION.SDK_INT>=21){
            mytts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }else{
            mytts.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private void initializeSpeechRecognizer(){
        if(SpeechRecognizer.isRecognitionAvailable(this)){
            mySpeechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                }
                @Override
                public void onBeginningOfSpeech() {

                }
                @Override
                public void onRmsChanged(float rmsdB) {

                }
                @Override
                public void onBufferReceived(byte[] buffer) {

                }
                @Override
                public void onEndOfSpeech() {

                }
                @Override
                public void onError(int error) {

                }
                @Override
                public void onResults(Bundle results) {
                    List<String> result= results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result.get(0));
                }
                @Override
                public void onPartialResults(Bundle partialResults) {
                }
                @Override
                public void onEvent(int eventType, Bundle params) {
                }
            });
        }
    }
    private void processResult(String s) {
        s=s.toLowerCase();//what is your name, what is the time, open the browser
        if(s.indexOf("what")!=-1){
            if(s.indexOf("your name")!=-1){
                speak("My name is ImageInsta");
            }if(s.indexOf("the time")!=-1){
                Date now=new Date();
                String time= DateUtils.formatDateTime(this,now.getTime(),DateUtils.FORMAT_SHOW_TIME);
                speak("Current time is"+time);
            }
        }
        else if(s.indexOf("open")!=-1){
            if(s.indexOf("browser")!=-1){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
            }
            if(s.indexOf("camera")!=-1){
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mytts.shutdown();
    }

}
