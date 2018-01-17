package eus.ehu.tta.ttaexampleestrella;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by estre on 09/01/2018.
 */

public class Test {

    private String wording;
    private List<Choice> choices;



    public Test(String wording, List<Choice> choices){
        this.wording=wording;
        this.choices = choices;

    }
    public Test(){
        choices = new ArrayList<Choice>();
    }

    public String getWording(){

        return wording;
    }
    public void setWording(String wording){
        this.wording=wording;
    }

    public List<Choice> getChoices(){
        return choices;
    }
    public void setChoices(List<Choice> choices){
        this.choices = choices;
    }

    public static class Choice{

        private int id;
        private String advise;
        private String answer;
        private boolean correct;
        private String mimeType;

        public Choice(){

        }

        public int getId(){
            return this.id;
        }

        public void setId(int id){
            this.id=id;
        }

        public String getAdvise(){
            return this.advise;
        }

        public void setAdvise(String advise){
            this.advise=advise;
        }

        public String getAnswer(){
            return this.answer;
        }

        public void setAnswer(String answer){
            this.answer = answer;
        }

        public boolean getCorrect(){
            return this.correct;
        }

        public void setCorrect(boolean correct){
            this.correct=correct;
        }

        public String getMimeType(){
            return this.mimeType;
        }

        public void setMimeType(String mimeType){
            this.mimeType=mimeType;
        }
    }
}
