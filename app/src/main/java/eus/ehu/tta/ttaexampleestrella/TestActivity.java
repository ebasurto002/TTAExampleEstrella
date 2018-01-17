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
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TestActivity extends AppCompatActivity{

    private Test test;
    private int correctAns;
    private PresentationIface presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        presentation = new PresentationLogic();
        //test = presentation.getNewTest();
        new ProgressTask<Test>(this){
            @Override
            protected Test work(){
                return presentation.getNewTest();
            }
            protected void onFinish(Test result){
                test=result;
            }

        }.execute();
        TextView wording = (TextView)findViewById(R.id.exWording);
        wording.setText(test.getWording());
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.testChoices);
        int i = 0;
        for(Test.Choice choice : test.getChoices()){
            RadioButton button = new RadioButton(this);
            button.setText(choice.getAnswer());
            button.setOnClickListener(new OnClickRadioButton());
            rGroup.addView(button);
            if(choice.getCorrect()){
                correctAns= i;
            }
            i++;
        }
    }

    public void createHelpButton(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.testLayout);
        Button helpBtn = new Button(this);
        helpBtn.setText(R.string.helpBtn);
        helpBtn.setOnClickListener(new OnClickHelp(correctAns));
        linearLayout.addView(helpBtn);
    }

    public void showHTML(int i){
        if(test.getChoices().get(i).getAdvise().substring(0,10).contains("://")){
            Uri uri = Uri.parse(test.getChoices().get(i).getAdvise());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        else{
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.testLayout);
            WebView web = new WebView(this);
            web.loadData(test.getChoices().get(i).getAdvise(),"text/html",null);
            linearLayout.addView(web);
        }
    }
    public void playAudio(int i){
        AudioPlayer audioPlayer = new AudioPlayer(findViewById(R.id.testLayout), new Runnable(){
            @Override
            public void run(){

            }
        });
        try{
            audioPlayer.setAudioUri(Uri.parse(test.getChoices().get(i).getAdvise()));
        }
        catch(IOException e){

        }

    }
    public void playVideo(int i){
        VideoView video = new VideoView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(layoutParams);
        video.setVideoURI(Uri.parse(test.getChoices().get(i).getAdvise()));

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

        private int helpForChild;
        public OnClickHelp(int i){
            this.helpForChild = i;
        }
        @Override
        public void onClick(View view){
            String type = test.getChoices().get(helpForChild).getMimeType();
            if(type.contains("html")){
                showHTML(helpForChild);
            }
            else if(type.contains("audio")){
                playAudio(helpForChild);
            }
            else{
                playVideo(helpForChild);
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
                final Test.Choice choice = test.getChoices().get(i);
                if(button.isChecked()){
                    new ProgressTask<Boolean>(TestActivity.this){
                        @Override
                        protected Boolean work(){
                            try {
                                JSONObject json = new JSONObject();
                                json.put("userId", 1);
                                json.put("choiceId", choice.getId());
                                return presentation.sendChoice(json);
                            }
                            catch (JSONException je){
                                je.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onFinish(Boolean result){
                            if(result.booleanValue() == true){
                                Toast.makeText(TestActivity.this, R.string.onSendSuccess,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(TestActivity.this, R.string.onSendFail, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                    if(correctAns == i){
                        button.setBackgroundColor(Color.GREEN);
                        Toast.makeText(TestActivity.this,R.string.cAns,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        button.setBackgroundColor(Color.RED);
                        Toast.makeText(TestActivity.this,R.string.fAns,Toast.LENGTH_SHORT).show();
                        createHelpButton();
                    }
                }
            }
            RadioButton radioButton = (RadioButton)radioGroup.getChildAt(correctAns);
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
