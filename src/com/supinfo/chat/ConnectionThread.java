/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Alexis
 */
public class ConnectionThread implements Runnable {
    private final Socket socket;
    private final MessagesService msgService;

    public ConnectionThread(Socket socket, MessagesService msgService) {
        this.socket = socket;
        this.msgService = msgService;
    }

    @Override
    public void run() {        
        try(
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine, username = null;
            
            while((inputLine = in.readLine()) != null) {
                if(inputLine.startsWith("username:")) {
                    username = inputLine.replace("username:", "");
                    
                    msgService.subscribe(username, msg -> {
                        out.println(msg);
                        out.flush();
                    });
                } else if(username != null) {
                    msgService.newMessage(username, "<" + username + "> : " + inputLine);
                }
            }
        } catch(SocketException e) {
            System.out.println("Connexion closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
