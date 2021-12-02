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

    int xDest;
    int xMit;
    int y;
    String mess;
    boolean stato;//ascolto->true/invio->false
    boolean inComunicazione;
    boolean comunicazione;
    boolean chat;
    DatagramSocket server;
    String nome;
    InetAddress ip;
    interfaccia frame;
    String nomeDest;

    public Comunicazione(interfaccia frame) {
        try {
            server = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        xDest = 10;
        xMit = 400;
        y = 10;
        this.frame = frame;
        mess = "";
        nomeDest = "";
        stato = true;
        inComunicazione = false;
        comunicazione = true;
        chat = false;
        try {
            server = new DatagramSocket(12345);
        } catch (SocketException ex) {
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

    public DatagramSocket getServer() {
        return server;
    }

    public void setX(int x) {
        this.xMit = x;
    }

    public void setxDest(int xDest) {
        this.xDest = xDest;
    }

    public int getxDest() {
        return xDest;
    }

    public void setY(int y) {
        this.y = y;
        System.out.println(y);
    }

    public int getX() {
        return xMit;
    }

    public int getY() {
        return y;
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
            String[] vett = messaggioRicevuto.split(";");
            if (vett[0].equals("m")) {
                JLabel label = new JLabel(vett[1]);
                label.setSize(new Dimension(vett[1].length() * 10, 20));
                label.setLocation(this.xDest, this.y);
                label.setText(vett[1]);
                frame.getPanel().add(label);
                this.setY(this.y + 20);
                frame.repaint();
            } else {
                this.setChat();
            }

        }
        String risposta = "c;";
        try {
            server = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] responseBuffer = risposta.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
        responsePacket.setAddress(ip);
        responsePacket.setPort(12345);
        try {
            server.send(responsePacket);
        } catch (IOException ex) {
            Logger.getLogger(interfaccia.class.getName()).log(Level.SEVERE, null, ex);
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
        responsePacket.setPort(12345);
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
            System.out.println("ho ricevuto-> " + messaggioRicevuto);
            ip = packet.getAddress();
            mess = messaggioRicevuto;
            String vett1[] = messaggioRicevuto.split(";");
            nomeDest = vett1[1];
            frame.setLabel(nomeDest);
            /*
            String vett[] = this.getMess().split(";");
            if (vett[0].equals("y")) {

                String risposta1 = "y;";
                byte[] responseBuffer1 = risposta1.getBytes();
                DatagramPacket responsePacket1 = new DatagramPacket(responseBuffer1, responseBuffer1.length);
                responsePacket1.setAddress(this.getIp());
                responsePacket1.setPort(12345);
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
                    responsePacket.setPort(12345);
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
                        responsePacket.setPort(12345);
                        try {
                            server.send(responsePacket);
                        } catch (IOException ex) {
                            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                        String risposta = "y;" + name + ";";
                        byte[] responseBuffer = risposta.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                        responsePacket.setAddress(packet.getAddress());
                        responsePacket.setPort(12345);
                        try {
                            server.send(responsePacket);
                        } catch (IOException ex) {
                            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        byte[] buffer1 = new byte[1500];
                        DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length);
                        try {
                            server.receive(packet1);
                        } catch (IOException ex) {
                            Logger.getLogger(Comunicazione.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("ho ricevuto-> " + new String(packet1.getData()));

                        String messaggioRicevuto1 = new String(packet1.getData(), 0, packet1.getLength());
                        messaggioRicevuto1.trim();

                        /* byte[] dataReceived1 = packet1.getData(); // copia del buffer dichiarato sopra
                        String messaggioRicevuto1 = new String(dataReceived, 0, packet.getLength());
                        System.out.println(messaggioRicevuto1+"suca");
                        messaggioRicevuto1.trim();
                         */
                        if (messaggioRicevuto1.equals("y;")) {
                            this.startChat(frame.getPanel());
                        }

                    }

                }

            } else if (vett1[0].equals("y")) {
                
                String risposta3 = "y;";
                byte[] responseBuffer3 = risposta3.getBytes();
                DatagramPacket responsePacket3 = new DatagramPacket(responseBuffer3, responseBuffer3.length);
                responsePacket3.setAddress(ip);
                responsePacket3.setPort(12345);
                try {
                    server.send(responsePacket3);     
                    this.startChat(frame.getPanel());
                    System.out.println(new String(responsePacket3.getData()));
                    System.out.println("ho mandato la y");
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
