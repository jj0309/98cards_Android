package com.example.tpfinal;
/*
* classe de score qui va être utilisé dans le DB et dans le menu principale
*
* Auteur Ka-son Chau
* */
public class Score {
    int leScore;
    String leName;
    public Score(int score, String name){
        leScore = score;
        leName = name;
    }

    public int getLeScore() {
        return leScore;
    }

    public String getLeName() {
        return leName;
    }

    public void setLeScore(int leScore) {
        this.leScore = leScore;
    }

    public void setLeName(String leName) {
        this.leName = leName;
    }
}
