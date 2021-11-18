package javaapplication12;

import java.awt.Frame;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Andrea
 */
public class ThreadStampa extends Thread {

    Frame f;
    int millis;
    //Messaggi messaggi;

    public ThreadStampa(Frame f, int fps) {
        this.f = f;
        millis = 1000 / fps;
        //messaggi=Messaggi.getInstance();
    }

    @Override
    public void run() {

        while (!Messaggi.getInstance().comunicazione) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(ThreadStampa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            f.repaint();
        }

    }
}
