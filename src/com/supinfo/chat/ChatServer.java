/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Alexis
 */
public class ChatServer {
    
    public static void main(String[] args) throws Exception {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final MessagesService msgService = new MessagesService();
        
        ServerSocket serverSocket = new ServerSocket(18000);
        
        while(true) {
            Socket client = serverSocket.accept();
            System.out.println("New connexion !");
            
            exec.execute(new ConnectionThread(client, msgService));
        }
    }
    
}
