package eus.ehu.tta.ttaexampleestrella;

/**
 * Created by estre on 27/12/2017.
 */

public interface PresentationIface {

    public abstract boolean authenticateUser(String uName, String psswd);
    public abstract boolean uploadFile();
    public abstract boolean takePic();
    public abstract boolean recordAud();
    public abstract boolean recordVid();
    public abstract boolean sendExercise();
    public abstract String getExWording();
    public abstract Test getNewTest();
}
