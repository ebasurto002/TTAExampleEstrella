package eus.ehu.tta.ttaexampleestrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TrackingActivity extends AppCompatActivity {

    PresentationIface presentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        presentation = new PresentationLogic();

    }
}
