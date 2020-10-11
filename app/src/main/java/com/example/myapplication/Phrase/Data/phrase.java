package com.example.myapplication.Phrase.Data;

public class phrase implements Comparable<phrase> {
    int ID;
    String PHRASE;
    String MEANINGB;
    String MEANINGE;
    String ORIGINE;

    public String getORIGINE() {
        return ORIGINE;
    }

    @Override
    public String toString() {
        return "phrase{" +
                "ID=" + ID +
                ", PHRASE='" + PHRASE + '\'' +
                ", MEANINGB='" + MEANINGB + '\'' +
                ", MEANINGE='" + MEANINGE + '\'' +
                ", ORIGINE='" + ORIGINE + '\'' +
                '}';
    }

    public void setORIGINE(String origin) {
        this.ORIGINE = origin;
    }

    public phrase(int id, String root, String meaningB, String meaningE , String origin) {
        this.ID = id;
        this.PHRASE = root;
        this.MEANINGB = meaningB;
        this.MEANINGE = meaningE;
        this.ORIGINE = origin;
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

    public phrase() {

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


    @Override
    public int compareTo(phrase o) {
        return this.getID() - (o.getID());
    }
}
