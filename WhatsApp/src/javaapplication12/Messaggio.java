/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

/**
 *
 * @author Andrea
 */
public class Messaggio {
    int id;
    String contenutoMessaggio;
    boolean stato;//inviato true/ricevuto false

    public Messaggio() {
        id=0;
        contenutoMessaggio="";
        stato=false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContenutoMessaggio(String contenutoMessaggio) {
        this.contenutoMessaggio = contenutoMessaggio;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public String getContenutoMessaggio() {
        return contenutoMessaggio;
    }

    public boolean isStato() {
        return stato;
    }       
}
