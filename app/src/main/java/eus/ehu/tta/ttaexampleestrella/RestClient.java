package eus.ehu.tta.ttaexampleestrella;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        HttpURLConnection c = null;

        try{
            c = getConnection(path, "GET");
            try(BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))){
                return br.readLine();
            }
        }
        finally{
            if(c != null){
                c.disconnect();
            }
        }
    }

    public JSONObject getJson(String path) throws IOException , JSONException{
        JSONObject json = new JSONObject(getString(path));
        return json;
    }

    public int postFile(String path, InputStream is, String fileName) throws IOException{
        String boundary = Long.toString(System.currentTimeMillis());
        String newLine = "\r\n";
        String prefix = "--";

        HttpURLConnection c = null;
        try{
            c = getConnection(path, "POST");
            c.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            c.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(c.getOutputStream());
            out.writeBytes(prefix+boundary+newLine);
            out.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + newLine);
            out.writeBytes(newLine);
            byte[] data = new byte[1024 * 1024];
            int len;
            while((len = is.read(data)) > 0){
                out.write(data,0,len);
            }
            out.writeBytes(newLine);
            out.writeBytes(prefix+boundary+prefix+newLine);
            out.close();
            return c.getResponseCode();
        }
        finally{
            if(c != null){
                c.disconnect();
            }
        }
    }

    public int postJson(final JSONObject json, String path) throws IOException{
        HttpURLConnection c = null;

        try{
            c = getConnection(path, "POST");
            c.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            c.setDoOutput(true);
            try(PrintWriter pw = new PrintWriter(c.getOutputStream())){
                pw.print(json.toString());
                return c.getResponseCode();
            }
        }finally{
            if(c != null){
                c.disconnect();
            }
        }
    }


}
