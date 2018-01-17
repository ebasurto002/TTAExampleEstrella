package eus.ehu.tta.ttaexampleestrella;

import android.content.Context;
import android.net.Uri;

import org.json.JSONObject;

/**
 * Created by estre on 27/12/2017.
 */

public interface PresentationIface {

    public abstract boolean authenticateUser(String uName, String psswd);
    public abstract boolean uploadFile(Uri uri, Context c);
    public abstract JSONObject getStatus();
    public abstract boolean sendChoice(JSONObject choice);
    public abstract String getExWording();
    public abstract Test getNewTest();
}
