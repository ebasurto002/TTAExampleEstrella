package eus.ehu.tta.ttaexampleestrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

            }
        }.execute();
    }
}
