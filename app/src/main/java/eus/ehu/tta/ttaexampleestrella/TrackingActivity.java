package eus.ehu.tta.ttaexampleestrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackingActivity extends AppCompatActivity {

    PresentationIface presentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        this.presentation = new PresentationLogic();
        new ProgressTask<JSONObject>(this){
            @Override
            protected JSONObject work(){
                return presentation.getStatus();
            }

            @Override
            protected void onFinish(JSONObject result){
                try {
                    TextView name = (TextView)findViewById(R.id.uname);
                    name.setText(result.getString("user"));
                    TextView lesson = (TextView)findViewById(R.id.title);
                    lesson.setText(result.getString("lessonTitle"));
                    TextView nextTest = (TextView)findViewById(R.id.nextTest2Do);
                    nextTest.setText(result.getString("nextTest"));
                    TextView nextEx = (TextView)findViewById(R.id.nextEx2Do);
                    nextEx.setText(result.getString("nextExercise"));
                }
                catch(JSONException je){
                    je.printStackTrace();
                }
            }
        }.execute();
    }
}
