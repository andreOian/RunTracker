package com.example.andre.tabs.Database;

import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Classe che modella il DB
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PrimoDB.db";
    public static final String TABLE_NAME = "UtentiSistema";
    public static final String TABLE_NAME2 = "CorseUtenteSistema";

    public static final String COL1 = "ID";
    public static final String COL2 = "Name";
    public static final String COL3 = "Surname";
    public static final String COL4 = "Eta";
    public static final String COL5 = "Sesso";
    public static final String COL6 = "Peso";
    public static final String COL7 = "Altezza";

    public static final String COL8 = "ID";
    public static final String COL9 = "Data";
    public static final String COL10 = "Durata";
    public static final String COL11 = "VelMedia";
    public static final String COL12 = "VelMax";
    public static final String COL13 = "Calorie";
    public static final String COL14 = "Id_Name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     *
     * Creaiamo le tabelle
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,ETA INT,SESSO TEXT,PESO INT,ALTEZZA INT)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATA TEXT,DURATA TEXT,VELMEDIA INT,VELMAX INT,CALORIE INT,ID_NAME INT)");
    }

    /**
     *
     * Cancelliamo le tabelle
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    /**
     *
     * Inserimento dati nella tabella UtentiSistema
     * @param name
     * @param surname
     * @param eta
     * @param sesso
     * @param peso
     * @param altezza
     * @return
     */
    public boolean insertData(String name,String surname,int eta,String sesso,int peso,int altezza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,surname);
        contentValues.put(COL4,eta);
        contentValues.put(COL5,sesso);
        contentValues.put(COL6,peso);
        contentValues.put(COL7,altezza);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     *
     * Otteniamo i dati dalla tabella UtentiSistema
     * @return
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    /**
     *
     * Aggiorniamo i dati nel DB
     * @param id
     * @param name
     * @param surname
     * @param marks
     * @return
     */
    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,surname);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    /**
     *
     * Cancelliamo i dati nella tabella UtentiSistema
     * @param id
     * @return
     */
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    /**
     *
     * CorseUtenteSistema
     * @param data
     * @param durata
     * @param velMedia
     * @param velMax
     * @param calorie
     * @param idName
     * @return
     */
    public boolean insertData2(String data,String durata,int velMedia, int velMax, int calorie,int idName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL9, data);
        contentValues.put(COL10, durata);
        contentValues.put(COL11, velMedia);
        contentValues.put(COL12, velMax);
        contentValues.put(COL13, calorie);
        contentValues.put(COL14, idName);
        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     *
     * Otteniamo i dati dalla tabella CorseUtenteSistema
     * @return
     */
    public Cursor getAllData2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2,null);
        return res;
    }
}
