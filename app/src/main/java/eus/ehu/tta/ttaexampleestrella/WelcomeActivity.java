package eus.ehu.tta.ttaexampleestrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN = "app_login";
    private PresentationIface presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.presentation = new PresentationLogic();
        TextView welcomeText = (TextView)findViewById(R.id.welcome_text);
        welcomeText.setText(getIntent().getStringExtra(EXTRA_LOGIN));


    }
    public void nTest(View view){
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }
    public void nExercise(View view){
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }
    public void getTracking(View view){

    }
}
