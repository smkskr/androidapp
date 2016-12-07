package com.example.android.thenextbigthing.utils;

/**
 * Created by smk on 11/1/2016.
 */
public class Riddles {

    private int _id;
    private int _count;
    private String question;
    private String answer;
    private int repeat;

    // Empty constructor
    public Riddles(){

    }
    // constructor
    public Riddles(int id, String question, String answer){
        this._id = id;
        this.question = question;
        this.answer = answer;
    }

    // constructor
    public Riddles(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    //getter and setter
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int get_count() {
        return _count;
    }

    public void set_count(int _count) {
        this._count = _count;
    }
}
