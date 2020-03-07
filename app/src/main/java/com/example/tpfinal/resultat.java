package com.example.tpfinal;
/*
 * page de rammasage de affichage du score de la partie joué et pour k'insertion du score dans la bd
 *
 * Auteur Ka-son Chau
 * */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class resultat extends AppCompatActivity {
    TextView affichageScore;
    EditText gatherText;
    dbHelper instance;
    Button monButton;
    int leScore = 0;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        gatherText = findViewById(R.id.gatherName);
        affichageScore = findViewById(R.id.userScore);
        leScore = getIntent().getIntExtra("score",0);
        affichageScore.setText(String.valueOf(leScore));
        instance = dbHelper.getInstance(this);
        monButton = findViewById(R.id.buttonSoumetre);
        Ecouteur ec = new Ecouteur();
        monButton.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // on ne fait rien si l'utilisateur entre rien dans la case de nom
            if(!gatherText.getText().toString().isEmpty())
            {
                // prend le nom
                String leName = gatherText.getText().toString();
                //crée une instance de score et insertion dans la bd
                instance.ajouterScore(new Score(leScore,leName),instance.getDatabase());
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.fermerDB();
    }
}
