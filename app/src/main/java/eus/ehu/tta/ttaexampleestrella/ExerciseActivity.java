package eus.ehu.tta.ttaexampleestrella;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ExerciseActivity extends AppCompatActivity {

    private static final int PICTURE_REQUEST_CODE = 0;
    private static final int AUDIO_REQUEST_CODE = 1;
    private static final int VIDEO_REQUEST_CODE = 2;
    private PresentationIface presentation;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        this.presentation = new PresentationLogic();
        TextView exWording = (TextView)findViewById(R.id.nExWording);
        exWording.setText((CharSequence) presentation.getExWording());
    }

    public void takePicture(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this, R.string.no_cam, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                try{
                    File file = File.createTempFile("tta",".jpg",dir);
                    picUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,picUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                }
                catch(IOException e){

                }
            }
            else{
                Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void recordAudio(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            Toast.makeText(this, R.string.no_mic,Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, AUDIO_REQUEST_CODE);
            }
            else{
                Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void recordVideo(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this, R.string.no_cam, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            }
            else{
                Toast.makeText(this, R.string.no_app, Toast.LENGTH_SHORT);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        switch(requestCode){
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                presentation.uploadFile(data.getData());
                break;
            case PICTURE_REQUEST_CODE:
                presentation.uploadFile(picUri);
                break;
        }
    }
}
