/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.util.ArrayList;

/**
 *
 * @author Andrea
 */
public class Messaggi {
    
    ArrayList<Messaggio> messaggi;
    boolean comunicazione;

    public Messaggi() {
       messaggi=new ArrayList<Messaggio>();
       comunicazione=false;
    }
    
    
    
    
static Messaggi _instance=null;
    static synchronized public Messaggi getInstance()
    {
        if(_instance==null)
            _instance=new Messaggi();
        return _instance;
    }
}
