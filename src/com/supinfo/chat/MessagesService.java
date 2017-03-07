/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexis
 */
public class MessagesService {
    
    interface MessagesListener {
        void onNewMessage(final String message);
    }
    
    private Map<String, MessagesListener> listeners = new HashMap<>();
    
    public void subscribe(final String id, final MessagesListener listener) {
        this.listeners.put(id, listener);
    }
    
    public void newMessage(final String id, final String message) {
        System.out.println("new message (" + id + ") : " + message);
        this.listeners
            .forEach((listId, listener) -> {
                if(!listId.equals(id))
                    listener.onNewMessage(message);
            });
    }
    
}
