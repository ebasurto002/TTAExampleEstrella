package eus.ehu.tta.ttaexampleestrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private PresentationIface presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Test test = presentation.getNewTest();
        TextView wording = (TextView)findViewById(R.id.exWording);
        wording.setText(test.getWording());
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.testChoices);
        for(String choice : test.getChoices()){
            RadioButton button = new RadioButton(this);
            button.setText(choice);
            rGroup.addView(button);
        }
    }
}
