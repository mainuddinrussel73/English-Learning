package com.example.myapplication.Parts.Data;

import com.example.myapplication.Phrase.Data.phrase;

public class parts  implements Comparable<parts> {
    String word;
    String nounform;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    int ID;


    public parts() {

    }

    public parts(int id,String word, String nounform, String verbform, String adjectiveform, String adverbform) {
        this.ID = id;
        this.word = word;
        this.nounform = nounform;
        this.verbform = verbform;
        this.adjectiveform = adjectiveform;
        this.adverbform = adverbform;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getNounform() {
        return nounform;
    }

    public void setNounform(String nounform) {
        this.nounform = nounform;
    }

    public String getVerbform() {
        return verbform;
    }

    public void setVerbform(String verbform) {
        this.verbform = verbform;
    }

    public String getAdjectiveform() {
        return adjectiveform;
    }

    public void setAdjectiveform(String adjectiveform) {
        this.adjectiveform = adjectiveform;
    }

    @Override
    public String toString() {
        return "parts{" +
                "word='" + word + '\'' +
                ", nounform='" + nounform + '\'' +
                ", verbform='" + verbform + '\'' +
                ", adjectiveform='" + adjectiveform + '\'' +
                ", adverbform='" + adverbform + '\'' +
                '}';
    }

    public String getAdverbform() {
        return adverbform;
    }

    public void setAdverbform(String adverbform) {
        this.adverbform = adverbform;
    }

    String verbform;
    String adjectiveform;
    String adverbform;

    @Override
    public int compareTo(parts o) {
        return this.getID() - (o.getID());
    }

}
