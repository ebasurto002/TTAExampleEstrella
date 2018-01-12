package eus.ehu.tta.ttaexampleestrella;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by estre on 12/01/2018.
 */

public class RestClient {
    private final static String AUTH = "Authorization";
    private final String baseURL;
    private final Map<String,String> properties = new HashMap<>();

    public RestClient(String baseURL){this.baseURL = baseURL;}

    public void setHttpBasicAuth(String uname, String psswd){
        String basicAuth = Base64.encodeToString(String.format("%s:%s",uname,psswd).getBytes(),Base64.DEFAULT);
        properties.put(AUTH,String.format("Basic %s",basicAuth));
    }
    public String getAuthorization(){
        return properties.get(AUTH);
    }
    public void setAuthorization(String auth){
        properties.put(AUTH,auth);
    }
    public void setProperties(String name, String value){
        properties.put(name, value);
    }

    private HttpURLConnection getConnection(String path, String method) throws IOException{
        URL url = new URL(String.format("%s/%s", baseURL, path));
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        for(Map.Entry<String, String> property : properties.entrySet()){
            connection.setRequestProperty(property.getKey(),property.getValue());
        }
        connection.setRequestMethod(method);
        connection.setUseCaches(false);

        return connection;
    }

    public String getString(String path) throws IOException{
        String string = "";
        return string;
    }

    public JSONObject getJson(String path) throws IOException , JSONException{
        JSONObject json = new JSONObject();
        return json;
    }

    public int postFile(String path, InputStream is, String fileName) throws IOException{
        int i = 0;
        return i;
    }

    public int postJson(final JSONObject json, String path) throws IOException{
        int i = 0;
        return i;
    }


}