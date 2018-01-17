package eus.ehu.tta.ttaexampleestrella;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
    private static final int READ_REQUEST_CODE = 3;
    private PresentationIface presentation;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        this.presentation = new PresentationLogic();
        new ProgressTask<String>(this){
            @Override
            public String work(){
                return presentation.getExWording();
            }
            @Override
            public void onFinish(String result){
                TextView exWording = (TextView)findViewById(R.id.nExWording);
                exWording.setText((CharSequence) result);
            }
        }.execute();

    }

    public void selectFile2Upload(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
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
            case READ_REQUEST_CODE:
               final  Uri uri = data.getData();
                showMetadata(uri);
                new ProgressTask<Boolean>(ExerciseActivity.this){
                    @Override
                    public Boolean work(){
                        return presentation.uploadFile(uri, ExerciseActivity.this);
                    }
                    public void onFinish(Boolean result){
                        if(result.booleanValue() == true){
                            Toast.makeText(ExerciseActivity.this, R.string.onUploadSuccess,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ExerciseActivity.this, R.string.onUploadFail,Toast.LENGTH_SHORT).show();
                        }

                    }
                }.execute();
                break;
            case PICTURE_REQUEST_CODE:
                new ProgressTask<Boolean>(ExerciseActivity.this){
                    @Override
                    public Boolean work(){
                        return presentation.uploadFile(picUri, ExerciseActivity.this);
                    }
                    public void onFinish(Boolean result){
                        if(result.booleanValue() == true){
                            Toast.makeText(ExerciseActivity.this, R.string.onUploadSuccess,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ExerciseActivity.this, R.string.onUploadFail,Toast.LENGTH_SHORT).show();
                        }

                    }
                }.execute();
                break;
        }
    }

    public void showMetadata(Uri uri){
        Cursor cursor = getContentResolver().query(uri,null, null, null, null, null);
        try{
            if(cursor != null && cursor.moveToFirst()){
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                String size = null;
                if(!cursor.isNull(sizeIndex)){
                    size = cursor.getString(sizeIndex);
                }
                else{
                    size = "Unknown";
                }
                String message = displayName + " ("+ size +" bytes) selected";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
        finally{
            cursor.close();
        }

    }
}
