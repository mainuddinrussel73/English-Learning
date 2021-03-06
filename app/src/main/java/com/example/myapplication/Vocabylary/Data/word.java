package com.example.myapplication.Vocabylary.Data;

public class word implements Comparable<word> {
    int ID;
    String WORD;
    String MEANINGB;
    String MEANINGE;
    String SYNONYMS;
    String ANTONYMS;

    public word(int id, String root, String meaningB,String meaningE, String syn,String ant) {
        this.ID = id;
        this.WORD = root;
        this.MEANINGB = meaningB;
        this.MEANINGE = meaningE;
        this.SYNONYMS = syn;
        this.ANTONYMS = ant;
    }

    @Override
    public String toString() {
        return "word{" +
                "ID=" + ID +
                ", WORD='" + WORD + '\'' +
                ", MEANINGB='" + MEANINGB + '\'' +
                ", MEANINGE='" + MEANINGE + '\'' +
                ", SYNONYMS='" + SYNONYMS + '\'' +
                ", ANTONYMS='" + ANTONYMS + '\'' +
                '}';
    }

    public String getMEANINGB() {
        return MEANINGB;
    }

    public void setMEANINGB(String MEANINGB) {
        this.MEANINGB = MEANINGB;
    }

    public String getMEANINGE() {
        return MEANINGE;
    }

    public void setMEANINGE(String MEANINGE) {
        this.MEANINGE = MEANINGE;
    }

    public String getSYNONYMS() {
        return SYNONYMS;
    }

    public void setSYNONYMS(String SYNONYMS) {
        this.SYNONYMS = SYNONYMS;
    }

    public String getANTONYMS() {
        return ANTONYMS;
    }

    public void setANTONYMS(String ANTONYMS) {
        this.ANTONYMS = ANTONYMS;
    }
    public word() {

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


    @Override
    public int compareTo(word o) {
        return this.getID() - (o.getID());
    }
}
