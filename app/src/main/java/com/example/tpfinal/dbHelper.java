package com.example.tpfinal;

/*
 * classe de base de donné pour garder le score et le nom des joueurs
 *
 * Auteur Ka-son Chau
 * */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class dbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static dbHelper instance;
    // singleton pour pas avoir plusieur instance si elle existe deja
    public static dbHelper getInstance(Context context){
        if(instance == null){
            instance = new dbHelper(context.getApplicationContext());
        }
        return instance;
    }

    // pour ouvrir la db à l'instantiation
    // j'ai appeller ajouterscore ici pcq sinon ca crash ....
    private dbHelper(Context context){
        super(context, "db",null,1);
        ouvrirDB();
        ajouterScore(new Score(0,"NoScore"),database);
    }
    // pour créer les tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tableScore (_id INTEGER PRIMARY KEY AUTOINCREMENT, Nom TEXT, Score INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE FROM tableScore");
        onCreate(db);
    }
    // pour ajouter un score dans la bd, elle prend en para une instance de score et la bd
    public void ajouterScore(Score score,SQLiteDatabase database){
        ContentValues valeurs = new ContentValues();
        valeurs.put("Nom",score.getLeName());
        valeurs.put("Score",score.getLeScore());
        database.insert("tableScore",null,valeurs);
    }
    public void ouvrirDB(){
        database = this.getWritableDatabase();
    }
    public void fermerDB(){
        database.close();
    }
    // pour retourner le score le plus haut, elle retourne une instance de score
    public Score retournerScores(){
        Cursor c = database.rawQuery("SELECT Nom FROM tableScore ORDER BY Score DESC LIMIT 1",null);
        c.moveToNext();
        String leNom = c.getString(0);
        String[] tab = {leNom};
        Cursor d = database.rawQuery("SELECT Score from tableScore WHERE Nom = ?",tab);
        d.moveToNext();
        int leScore = d.getInt(0);
        Score maScore = new Score(leScore,leNom);
        return maScore;
    }
    // pour retourner la bd
    public SQLiteDatabase getDatabase() {
        return database;
    }
}
