package eus.ehu.tta.ttaexampleestrella;

import android.net.Uri;

/**
 * Created by estre on 03/01/2018.
 */

public class PresentationLogic implements PresentationIface {

    public PresentationLogic(){

    }

    @Override
    public boolean authenticateUser(String uName, String psswd){

        return true;
    }

    @Override
    public boolean uploadFile(Uri uri){

        return true;
    }

    @Override
    public boolean takePic(){

        return true;
    }

    @Override
    public boolean recordAud(){

        return true;
    }

    @Override
    public boolean recordVid(){

        return true;
    }

    @Override
    public boolean sendExercise(){

        return true;
    }
    public String getExWording(){
        return "Explica cómo aplicarías el patrón de diseño MVP en el desarrollo de una app para Android";
    }
    public Test getNewTest(){
        Test test = new Test();

        return test;
    }
}
