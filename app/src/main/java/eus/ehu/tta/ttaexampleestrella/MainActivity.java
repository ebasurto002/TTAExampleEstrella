package eus.ehu.tta.ttaexampleestrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PresentationIface presentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view){
        Intent intent = new Intent(this, WelcomeActivity.class);
        String login = ((EditText)findViewById(R.id.login)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.psswd)).getText().toString();
        if(presentation.authenticateUser(login, passwd)){
            intent.putExtra(WelcomeActivity.EXTRA_LOGIN,login);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,R.string.loginErrToast, Toast.LENGTH_SHORT).show();
        }
    }
}
