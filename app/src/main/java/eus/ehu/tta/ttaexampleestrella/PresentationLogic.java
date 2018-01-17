package eus.ehu.tta.ttaexampleestrella;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by estre on 03/01/2018.
 */

public class PresentationLogic implements PresentationIface {

    RestClient rest;

    public PresentationLogic(){
        rest = new RestClient("http://u017633.ehu.eus:28080/ServidorTta/rest/tta");

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
    public JSONObject getStatus(){
        try {
            rest.setHttpBasicAuth("12345678A", "tta");
            JSONObject json = rest.getJson("getStatus?dni=12345678A");
            return json;
        }
        catch (JSONException je){
            je.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean sendChoice(JSONObject choice){
        rest.setHttpBasicAuth("12345678A", "tta");
        try {
            int i = rest.postJson(choice, "postChoice");
            if(i == 200| i== 204){
                return true;
            }
            else{
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    public String getExWording(){
        try{
            rest.setHttpBasicAuth("12345678A", "tta");
            JSONObject json = rest.getJson("getExercise?id=1");
            String wording = json.getString("wording");
            return wording;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Test getNewTest(){

        try{
            rest.setHttpBasicAuth("12345678A", "tta");
            JSONObject json = rest.getJson("getTest?id=1");
            Test test = new Test();
            test.setWording(json.getString("wording"));
            JSONArray array = json.getJSONArray("choices");
            for(int i = 0; i < array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                Test.Choice choice = new Test.Choice();
                choice.setId(item.getInt("id"));
                choice.setAnswer(item.getString("answer"));
                choice.setCorrect(item.getBoolean("correct"));
                choice.setAdvise(item.optString("advise",null));
                choice.setMimeType(item.optString("mime",null));
                test.getChoices().add(choice);
            }
            return test;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
