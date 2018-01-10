package eus.ehu.tta.ttaexampleestrella;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class TestActivity extends AppCompatActivity{

    private Test test;
    private PresentationIface presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test = presentation.getNewTest();
        TextView wording = (TextView)findViewById(R.id.exWording);
        wording.setText(test.getWording());
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.testChoices);
        for(String choice : test.getChoices()){
            RadioButton button = new RadioButton(this);
            button.setText(choice);
            button.setOnClickListener(new OnClickRadioButton());
            rGroup.addView(button);
        }
    }

    public void createHelpButton(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.testLayout);
        Button helpBtn = new Button(this);
        helpBtn.setText(R.string.helpBtn);
        helpBtn.setOnClickListener(new OnClickHelp());
    }

    public void showHTML(){
        if(test.getHelp().substring(0,10).contains("://")){
            Uri uri = Uri.parse(test.getHelp());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        else{
            WebView web = new WebView(this);
            web.loadData(test.getHelp(),"text/html",null);
        }
    }
    public void playAudio(){
        AudioPlayer audioPlayer = new AudioPlayer(findViewById(R.id.testLayout), new Runnable(){
            @Override
            public void run(){

            }
        });
        try{
            audioPlayer.setAudioUri(Uri.parse(test.getHelp()));
        }
        catch(IOException e){

        }

    }
    public void playVideo(){
        VideoView video = new VideoView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(layoutParams);
        video.setVideoURI(Uri.parse(test.getHelp()));

        MediaController controller = new MediaController(this){
            @Override
            public void hide(){

            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    finish();
                }
                return super.dispatchKeyEvent(event);
            }
        };

        controller.setAnchorView(video);
        video.setMediaController(controller);
        LinearLayout layout = (LinearLayout)findViewById(R.id.testLayout);
        layout.addView(video);
        video.start();
    }

    private class OnClickHelp implements View.OnClickListener{

        public OnClickHelp(){

        }
        @Override
        public void onClick(View view){
            String type = test.getMimeType();
            switch(type){
                case "text/html":
                    showHTML();
                    break;
                case "audio/mp3":
                    playAudio();
                    break;
                case "video/mp4":
                    playVideo();
                    break;
            }
        }
    }

    private class OnClickSend implements View.OnClickListener{

        public OnClickSend(){

        }
        @Override
        public void onClick(View view){
            RadioGroup radioGroup = (RadioGroup)findViewById(R.id.testChoices);
            for(int i=0; i<radioGroup.getChildCount(); i++){
                RadioButton button = (RadioButton) radioGroup.getChildAt(i);
                if(button.isChecked()){
                    if(test.getCorrect() == i){
                        button.setBackgroundColor(Color.GREEN);
                    }
                    else{
                        button.setBackgroundColor(Color.RED);
                        createHelpButton();
                    }
                }
            }
            RadioButton radioButton = (RadioButton)radioGroup.getChildAt(test.getCorrect());
            radioButton.setBackgroundColor(Color.GREEN);
        }

    }

    private class OnClickRadioButton implements View.OnClickListener{
        public OnClickRadioButton(){}
        @Override
        public void onClick(View view){
            //Falta el botoncico de enviar
            Button sendButton = (Button)findViewById(R.id.sendButton);
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setOnClickListener(new OnClickSend());
        }
    }
}
