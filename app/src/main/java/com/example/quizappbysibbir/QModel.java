package com.example.quizappbysibbir;

import java.util.ArrayList;

public class QModel {
    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public ArrayList<String>getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(String selectedAns) {
        this.selectedAns = selectedAns;
    }

    ArrayList<String> options;
    String correctAns;
    String selectedAns = "";
    int selectedOption = -1;
    String Question;

}
