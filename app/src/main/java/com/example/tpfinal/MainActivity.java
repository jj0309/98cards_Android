package com.example.tpfinal;

/*
 * classe de de jeu principale
 * 97 cartes
 * Auteur Ka-son Chau
 * */

import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    GridLayout topGrid;
    GridLayout botGrid;
    LinearLayout top00;
    LinearLayout top01;
    LinearLayout top10;
    LinearLayout top11;
    Intent i;
    int userScore;
    Chronometer chrono;
    TextView textScore;
    List<Integer> listeDansMain = new ArrayList<Integer>();
    List<Integer> listeDansTopField = new ArrayList<Integer>();
    List<Integer> listeDansBotField = new ArrayList<Integer>();

    jeu jeu = new jeu();

    List<Integer> listOfInts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topGrid = findViewById(R.id.topgrid);
        botGrid = findViewById(R.id.botgrid);
        chrono = findViewById(R.id.simpleChronometer);
        textScore = findViewById(R.id.textScore);

        top00 = findViewById(R.id.top_0_0);
        top01 = findViewById(R.id.top_0_1);
        top10 = findViewById(R.id.top_1_0);
        top11 = findViewById(R.id.top_1_1);
        i = new Intent(MainActivity.this,resultat.class);
        userScore = 0;

        listOfInts = jeu.remplirPack(listOfInts);

        // les deux ecouteurs
        dragListener dragEC = new dragListener();
        ecouteurTouch touchEC = new ecouteurTouch();
        // pour ajouter les ecouteurs au linear layouts et pour set les texts de debut de la partie superieur
        for(int i = 0;i<topGrid.getChildCount();i++){
            LinearLayout lin = (LinearLayout) topGrid.getChildAt(i);
            lin.setOnDragListener(dragEC);
            if(i == 0 || i == 2){
                ((TextView)lin.getChildAt(0)).setText(Integer.toString(98));
            }
            else{
                ((TextView)lin.getChildAt(0)).setText(Integer.toString(0));
            }
        }
        //pour ajouter les ecouteurs au textviews qui sont dans les linear layouts
        for(int i = 0;i<botGrid.getChildCount();i++){
            LinearLayout lin = (LinearLayout) botGrid.getChildAt(i);
            lin.setOnDragListener(dragEC);
            lin.setOnTouchListener(touchEC);
            listOfInts = jeu.shufflePack(listOfInts);
            int randomCard = listOfInts.remove(0);
            ((TextView)lin.getChildAt(0)).setText(Integer.toString(randomCard));
        }
    }


    private class dragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch(event.getAction()){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    //deux textviews placeholders pour les textviews quon va devoir modifier
                    TextView ancien = new TextView(MainActivity.this);
                    TextView nouveau = new TextView(MainActivity.this);
                    String selectedValue;
                    String boardValue;
                    // basic swapping pour sauvegarde des données avant de les changers
                    LinearLayout maCarte = (LinearLayout)event.getLocalState();;
                    TextView textSelectioner = (TextView)maCarte.getChildAt(0);
                    ancien.setText(textSelectioner.getText());
                    LinearLayout destination = (LinearLayout) v;
                    TextView textDrop = (TextView)destination.getChildAt(0);
                    selectedValue = ancien.getText().toString();
                    boardValue =  textDrop.getText().toString();

                    //verification de si on place une carte au bon endroit
                    boolean valid = false;
                    if(destination == top00 || destination == top10){
                        valid = jeu.verif98(Integer.parseInt(selectedValue),Integer.parseInt(boardValue));
                    }
                    if(destination == top11 || destination == top01){
                        valid = jeu.verif00(Integer.parseInt(selectedValue),Integer.parseInt(boardValue));
                    }
                    // si c'est un mouvement valide, on fait le swap des texts
                    if(valid == true) {
                        maCarte.removeView(textSelectioner);
                        destination.removeView(textDrop);
                        jeu.shufflePack(listOfInts);
                        nouveau.setTextSize(24);
                        nouveau.setTextColor(Color.BLACK);
                        nouveau.setGravity(Gravity.CENTER);
                        nouveau.setText(Integer.toString(listOfInts.get(0)));

                        ancien.setGravity(Gravity.CENTER);
                        ancien.setTextColor(Color.BLACK);
                        destination.addView(ancien);
                        maCarte.addView(nouveau);
                        //arret du chrono pour savoir le temps et quel score donner
                        chrono.stop();
                        int scoreRound = (int) (SystemClock.elapsedRealtime() - chrono.getBase());
                        chrono.setBase(SystemClock.elapsedRealtime());
                        chrono.start();
                        if(scoreRound>10000)
                            userScore += 1;
                        else if(scoreRound>5000)
                            userScore +=5;
                        else if(scoreRound>1000)
                            userScore += 10;
                        //affichage du nouveau pointage
                        textScore.setText(String.valueOf(userScore)+" points");
                    }
                    // trois liste qu'on verifie a chaque tours pour voir si c la fin de partie
                    listeDansMain.removeAll(listeDansMain);
                    for(int i = 0;i<botGrid.getChildCount();i++){
                        TextView n = new TextView(MainActivity.this);
                        LinearLayout lin = (LinearLayout) botGrid.getChildAt(i);
                        n = (TextView) lin.getChildAt(0);
                        listeDansMain.add(Integer.parseInt(n.getText().toString()));
                    }
                    listeDansTopField.removeAll(listeDansTopField);
                    for(int i = 0;i<topGrid.getChildCount()-1;i+=2){
                        TextView n = new TextView(MainActivity.this);
                        LinearLayout lin = (LinearLayout) topGrid.getChildAt(i);
                        n = (TextView) lin.getChildAt(0);
                        listeDansTopField.add(Integer.parseInt(n.getText().toString()));
                    }
                    listeDansBotField.removeAll(listeDansBotField);
                    for(int i = 1;i<topGrid.getChildCount();i+=2){
                        TextView n = new TextView(MainActivity.this);
                        LinearLayout lin = (LinearLayout) topGrid.getChildAt(i);
                        n = (TextView) lin.getChildAt(0);
                        listeDansBotField.add(Integer.parseInt(n.getText().toString()));
                    }
                    // c'est la faim de la partie si on ne peut plus rien jouer (verification des trois liste)
                    boolean estIlFaim = jeu.verifCondArret(listeDansMain,listeDansTopField,listeDansBotField);
                    if(!estIlFaim || listOfInts.isEmpty()) {
                        i.putExtra("score", userScore);
                        startActivity(i);
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private class ecouteurTouch implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null,shadowBuilder,v,0);
            return true;
        }
    }
}
