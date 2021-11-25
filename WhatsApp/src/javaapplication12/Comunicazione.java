/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author lietti_andrea
 */
public class Comunicazione extends Thread {
    boolean stato;//ascolto->true/invio->false
    boolean comunicazione;
    DatagramSocket server;
    Messaggi messaggi;
    String nome;
    String ip;
    JFrame frame;
    
    
    
    
    
    
    public Comunicazione(Messaggi messaggi,JFrame frame) {
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

    
    public void setComunicazioneInvio(String name,String Ip)
    {
        setStato(false);
        
        nome=name;
        ip=Ip;

        //a;NOME_MITTENTE;
        String risposta = "a"+";"+nome+";";
        try {
            server = new DatagramSocket(12349);
        } catch (SocketException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] responseBuffer = risposta.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        try {
            responsePacket.setAddress(InetAddress.getByName("172.16.0.0"));
        } catch (UnknownHostException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }

        responsePacket.setPort(12349);

        try {
            server.send(responsePacket);
        } catch (IOException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            
            String[] vett= messaggioRicevuto.split(";");
            
            if(vett[0].equals("a"))
            {
                String name = JOptionPane.showInputDialog(frame, "Vuoi accettare la connessione?", null);                
            }
                
            
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
