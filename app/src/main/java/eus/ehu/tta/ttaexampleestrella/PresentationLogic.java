package eus.ehu.tta.ttaexampleestrella;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by estre on 03/01/2018.
 */

public class PresentationLogic implements PresentationIface {

    private Test [] tests;
    private static int numTest;

    public PresentationLogic(){
        numTest = 0;

        tests = new Test[]{new Test("¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                1,
                2,
                "The manifest describes the <b>components of the application</b>: the activities, services, broadcast receivers, and content providers that ...",
                "text/html"),
                new Test("¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        2,
                        2,
                        "https://developer.android.com/guide/topics/manifest/manifest-intro.html?hl=es-419",
                        "text/html"),
                new Test("¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        3,
                        2,
                        "http://techslides.com/demos/sample-videos/small.mp4",
                        "video/mp4"),
                new Test("¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        4,
                        2,
                        "http://techslides.com/demos/sample-videos/small.mp4",
                        "audio/mp4")};
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
        Test test = tests[numTest%tests.length];
        numTest++;
        return test;
    }
}
