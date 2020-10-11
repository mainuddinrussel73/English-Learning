package com.example.myapplication.Phrase.Data;

public class sentence {
    int ID;
    String PHRASE;
    String SENTENCE;
    public sentence(int id, String root, String sentence) {
        this.ID = id;
        this.PHRASE = root;
        this.SENTENCE = sentence;
    }
    public sentence() {

    }

    public String getSENTENCE() {
        return SENTENCE;
    }

    @Override
    public String toString() {
        return "sentence{" +
                "ID=" + ID +
                ", PHRASE='" + PHRASE + '\'' +
                ", SENTENCE='" + SENTENCE + '\'' +
                '}';
    }

    public void setSENTENCE(String SENTENCE) {
        this.SENTENCE = SENTENCE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPHRASE() {
        return PHRASE;
    }

    public void setPHRASE(String PHRASE) {
        this.PHRASE = PHRASE;
    }

}
