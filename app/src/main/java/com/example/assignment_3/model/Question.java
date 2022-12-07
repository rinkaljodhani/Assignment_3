package com.example.assignment_3.model;

import java.io.Serializable;

public class Question implements Serializable {

    String question;
    int answer;
    int color;

    public Question(String question, int answer)  {
        this.question = question;
        this.answer = answer;
    }

    public Question(int color) {
        this.color = color;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
