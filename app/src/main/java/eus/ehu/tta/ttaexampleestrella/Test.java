package eus.ehu.tta.ttaexampleestrella;

import java.util.List;

/**
 * Created by estre on 09/01/2018.
 */

public class Test {

    private String wording;
    private List<String> choices;
    private int exID;
    private int correct;
    private String help;
    private String mimeType;


    public Test(String wording, List<String> choices, int exID, int correct, String help, String mimeType){
        this.wording=wording;
        this.choices=choices;
        this.exID=exID;
        this.correct=correct;
        this.help=help;
        this.mimeType=mimeType;
    }

    public String getWording(){
        return wording;
    }
    public void setWording(String wording){

    }
    public List<String> getChoices(){
        return choices;
    }
    public void setChoices(List<String> choices){

    }
    public String getHelp(){
        return help;
    }
    public void setHelp(String help){

    }
    public String getMimeType(){
        return mimeType;
    }
    public void setMimeType(String mimeType){

    }

    public int getExID() {
        return exID;
    }

    public void setExID(int exID) {
        this.exID = exID;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
