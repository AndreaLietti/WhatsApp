/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lietti_andrea
 */
public class Comunicazione extends Thread {
    boolean stato;//ascolto->true/invio->false
    boolean comunicazione;
    DatagramSocket server;
    Messaggi messaggi;

    public Comunicazione(Messaggi messaggi) {
        this.messaggi = messaggi;
        stato=false;
        comunicazione=true;
        try {
            server = new DatagramSocket(12345);
        } catch (SocketException ex) {
            try {
                server = new DatagramSocket(12346);
            } catch (SocketException ex1) {
                Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public void setComunicazione(boolean comunicazione) {
        this.comunicazione = comunicazione;
    }            

    @Override
    public void run() {
        comunicazione = true;
        messaggi.getInstance();
        while (comunicazione) {
            System.out.println("partito");
            byte[] buffer = new byte[1500];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                server.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] dataReceived = packet.getData(); // copia del buffer dichiarato sopra
            String messaggioRicevuto = new String(dataReceived, 0, packet.getLength());
            System.out.println(messaggioRicevuto);
            messaggioRicevuto.trim();
        }
    }

    /*
    

byte[] buffer = new byte[1500];

DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

server.receive(packet);

byte[] dataReceived = packet.getData(); // copia del buffer dichiarato sopra

String messaggioRicevuto = new String(dataReceived, 0, packet.getLength());

System.out.println(messaggioRicevuto);
    
     */
}
