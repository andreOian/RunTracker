package com.example.andre.tabs.Model;

/**
 * Classe model per la corsa
 *
 */
public class Attività {

    private String data;
    private String durata;
    private int velMedia;
    private int velMax;
    private int calorie;

    public Attività(String data, String durata, int velMedia, int velMax, int calorie) {
        this.data = data;
        this.durata = durata;
        this.velMedia = velMedia;
        this.velMax = velMax;
        this.calorie = calorie;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public void setVelMedia(int velMedia) {
        this.velMedia = velMedia;
    }

    public void setVelMax(int velMax) {
        this.velMax = velMax;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getData() {
        return data;
    }

    public String getDurata() {
        return durata;
    }

    public int getVelMedia() {
        return velMedia;
    }

    public int getVelMax() {
        return velMax;
    }

    public int getCalorie() {
        return calorie;
    }
}
