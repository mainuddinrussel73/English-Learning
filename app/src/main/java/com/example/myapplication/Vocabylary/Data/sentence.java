package com.example.myapplication.Vocabylary.Data;

public class sentence {
    int ID;
    String WORD;
    String SENTENCE;
    public sentence(int id, String root, String sentence) {
        this.ID = id;
        this.WORD = root;
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
                ", WORD='" + WORD + '\'' +
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

    public String getWORD() {
        return WORD;
    }

    public void setWORD(String WORD) {
        this.WORD = WORD;
    }

}
