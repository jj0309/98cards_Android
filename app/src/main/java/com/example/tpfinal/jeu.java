package com.example.tpfinal;
/*
 * classe de jeu qui avec des fonctions qui verifie quelque logique du jeu
 *
 * Auteur Ka-son Chau
 * */
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class jeu {
    List<Integer> listOfInts = new ArrayList<>();
    int nbrCarte = 97;

    public jeu(){}

    // pour remplir le paquet au debut
    public List<Integer> remplirPack(List<Integer> listOfInts){
        List<Integer> listShuffled = listOfInts;
        for(int i=1;i<nbrCarte;i++){
            listShuffled.add(i);
        }
        return listShuffled;
    }
    // pour melanger les cartes
    public List<Integer> shufflePack(List<Integer> listOfInts){
        Collections.shuffle(listOfInts);
        return listOfInts;
    }
    // pour verifier si on peut placer la carte sur la portion superieur
    public boolean verif98(int valeurSelected,int valeurBoard){
        if(valeurSelected < valeurBoard || valeurSelected == valeurBoard+10)
            return true;
        return false;
    }
    // pour verifier si on peut placer la carte sur la portion superieur
    public boolean verif00(int valeurSelected,int valeurBoard){
        if(valeurSelected>valeurBoard || valeurSelected == valeurBoard-10)
            return true;
        return false;
    }
    //pour verifier la condition d'arret de jeu, on verifie toute les cartes en main et tout les carte su le board
    public boolean verifCondArret(List<Integer> listInHand,List<Integer> listOnTopField,List<Integer> listOnBotField){
        boolean firstHalf = false;
        boolean secondHalf = false;
        for(int i = 0;i<listInHand.size();i++){
            for(int j = 0;j<listOnTopField.size();j++)
            {
                if(listInHand.get(i) > listOnTopField.get(j))
                    firstHalf = true;
                else
                    firstHalf = false;
            }
        }
        for(int i = 0;i<listInHand.size();i++){
            for(int j = 0;j<listOnBotField.size();j++)
            {
                if(listInHand.get(i) < listOnBotField.get(j))
                    secondHalf = true;
                else
                    secondHalf = false;
            }
        }
        if(firstHalf == true && secondHalf == true)
            return false;
        return true;
    }

    public List<Integer> getListOfInts() {
        return listOfInts;
    }
}
