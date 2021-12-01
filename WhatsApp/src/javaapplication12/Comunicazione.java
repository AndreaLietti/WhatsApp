/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author lietti_andrea
 */
public class Comunicazione extends Thread {

    String mess;
    boolean stato;//ascolto->true/invio->false
    boolean inComunicazione;
    boolean comunicazione;
    boolean chat;
    DatagramSocket server;
    Messaggi messaggi;
    String nome;
    InetAddress ip;
    interfaccia frame;
    String nomeDest;

    public Comunicazione(Messaggi messaggi, interfaccia frame) {
        this.frame = frame;
        mess = "";
        nomeDest = "";
        this.messaggi = messaggi;
        stato = true;
        inComunicazione = false;
        comunicazione = true;
        chat = false;
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

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (this.stato == false) {
                    System.out.println("sono dentro");
                    this.invio();
                    return;
                }
            }
        }).start();
    }

    public void setInComunicazione(boolean inComunicazione) {
        this.inComunicazione = inComunicazione;
    }

    public String getNomeDest() {
        return nomeDest;
    }

    public boolean isStato() {
        return stato;
    }

    public boolean isInComunicazione() {
        return inComunicazione;
    }

    public boolean isComunicazione() {
        return comunicazione;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public void setComunicazione(boolean comunicazione) {
        this.comunicazione = comunicazione;
    }

    public void setComunicazioneInvio(String name, String Ip) {
        /*setStato(false);

        nome = name;
        ip = Ip;

        //a;NOME_MITTENTE;
        String risposta = "a" + ";" + nome + ";";
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
         */
    }

    public void setChat() {
        chat = !chat;
    }

    public boolean getChat() {
        return chat;
    }

    public void startChat(JPanel panel) {
        chat = true;
        while (chat) {
            byte[] buffer1 = new byte[1500];
            DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length);
            try {
                server.receive(packet1);
            } catch (IOException ex) {
                Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] dataReceived = packet1.getData(); // copia del buffer dichiarato sopra
            String messaggioRicevuto = new String(dataReceived, 0, packet1.getLength());
            System.out.println(messaggioRicevuto);

            JLabel label = new JLabel(messaggioRicevuto);
            label.setSize(new Dimension(100, 100));
            label.setLocation(100, 100);
            panel.add(label);
            frame.repaint();

        }
    }

    public void invio() {
        String risposta = "a;" + nome + ";";
        try {
            server = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] responseBuffer = risposta.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
        responsePacket.setAddress(ip);
        responsePacket.setPort(12346);
        try {
            server.send(responsePacket);
        } catch (IOException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMess() {
        return mess;
    }

    @Override
    public void run() {

        comunicazione = true;
        messaggi.getInstance();
        while (true) {

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
            ip = packet.getAddress();
            mess = messaggioRicevuto;
            String vett1[] = messaggioRicevuto.split(";");
            nomeDest = vett1[1];
            /*
            System.out.println(this.getMess());
            
            String vett[] = this.getMess().split(";");
            if (vett[0].equals("y")) {

                String risposta1 = "y;";
                byte[] responseBuffer1 = risposta1.getBytes();
                DatagramPacket responsePacket1 = new DatagramPacket(responseBuffer1, responseBuffer1.length);
                responsePacket1.setAddress(this.getIp());
                responsePacket1.setPort(12346);
                try {
                    server.send(responsePacket1);
                } catch (IOException ex) {
                    Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
                }
             */
            //this.startChat(frame.getPanel());

            if (vett1[0].equals("a")) {
                if (stato == false || inComunicazione == true) {
                    String risposta = "n;";
                    byte[] responseBuffer = risposta.getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                    responsePacket.setAddress(packet.getAddress());
                    responsePacket.setPort(12346);
                    try {
                        server.send(responsePacket);
                    } catch (IOException ex) {
                        Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    String name = JOptionPane.showInputDialog(frame, "Vuoi accettare la connessione?", null);

                    if (name == null) {
                        String risposta = "n;";
                        byte[] responseBuffer = risposta.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                        responsePacket.setAddress(packet.getAddress());
                        responsePacket.setPort(12346);
                        try {
                            server.send(responsePacket);
                        } catch (IOException ex) {
                            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                        String risposta = "y;" + name + ";";
                        byte[] responseBuffer1 = risposta.getBytes();
                        DatagramPacket responsePacket1 = new DatagramPacket(responseBuffer1, responseBuffer1.length);
                        responsePacket1.setAddress(packet.getAddress());
                        responsePacket1.setPort(12345);
                        try {
                            server.send(responsePacket1);
                        } catch (IOException ex) {
                            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(new String(responsePacket1.getData()));

                        DatagramPacket packet1 = new DatagramPacket(buffer, buffer.length);
                        try {
                            server.receive(packet1);
                        } catch (IOException ex) {
                            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(new String(packet1.getData()));
                        
                        
                        byte[] dataReceived1 = packet1.getData(); // copia del buffer dichiarato sopra
                        String messaggioRicevuto1 = new String(dataReceived, 0, packet.getLength());
                        System.out.println(messaggioRicevuto);
                        messaggioRicevuto.trim();
                        if (messaggioRicevuto.equals("y;")) {
                            this.startChat(frame.getPanel());
                        }

                    }

                }

            } else if (vett1[0].equals("y")) {                
                System.out.println("connessione avvenuta");

                String risposta3 = "y;";
                byte[] responseBuffer3 = risposta3.getBytes();
                DatagramPacket responsePacket3 = new DatagramPacket(responseBuffer3, responseBuffer3.length);
                responsePacket3.setAddress(packet.getAddress());
                responsePacket3.setPort(12345);
                try {
                    server.send(responsePacket3);
                } catch (IOException ex) {
                    Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public InetAddress getIp() {
        return ip;
    }
}

/*
    

byte[] buffer = new byte[1500];

DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

server.receive(packet);

byte[] dataReceived = packet.getData(); // copia del buffer dichiarato sopra

String messaggioRicevuto = new String(dataReceived, 0, packet.getLength());

System.out.println(messaggioRicevuto);
    
    
    
    
    String risposta = "CIAO CLIENT";

byte[] responseBuffer = risposta.getBytes();

DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

responsePacket.setAddress(packet.getAddress());

responsePacket.setPort(packet.getPort());

server.send(responsePacket);
    
 */
