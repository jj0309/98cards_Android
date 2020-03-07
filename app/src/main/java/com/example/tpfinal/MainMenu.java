package com.example.tpfinal;
/*
 * classe de menu principale pour afficher le plus haut score, bouton jouer
 *
 * Auteur Ka-son Chau
 *
 * menu principal qui montre le score le plus haut avec le alias du joueur
 *cliquer sur le button jouer pour jouer
 * */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    TextView scoreText;
    TextView aliasText;
    dbHelper instance;
    Ecouteur ec;
    Button jouer;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        instance = dbHelper.getInstance(this);
        scoreText = findViewById(R.id.textScoreMenu);
        aliasText = findViewById(R.id.theAlias);
        // affichage du plus haut score
        Score highScore = instance.retournerScores();
        scoreText.setText(String.valueOf(highScore.getLeScore()));
        aliasText.setText(highScore.getLeName());
        Ecouteur ec = new Ecouteur();
        jouer = findViewById(R.id.buttonJouer);
        jouer.setOnClickListener(ec);
        i = new Intent(MainMenu.this,MainActivity.class);
    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //on passe au jeu si l'utilisateur clique sur le button jouer
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.fermerDB();
    }
}
