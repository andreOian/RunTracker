package com.example.andre.tabs.Model;

/**
 * Classe model per gli utenti del sistema
 *
 */
public class Utenti {

    private String nome;
    private String cognome;
    private int eta;
    private String sesso;
    private int peso;
    private int altezza;

    public Utenti(String nome, String cognome, int eta, String sesso, int peso, int altezza) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.sesso = sesso;
        this.peso = peso;
        this.altezza = altezza;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEta() {
        return eta;
    }

    public String getSesso() {
        return sesso;
    }

    public int getPeso() {
        return peso;
    }

    public int getAltezza() {
        return altezza;
    }
}
