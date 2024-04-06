package com.services;

import com.models.Message;

import java.time.LocalDateTime;
import java.util.List;

public class MessageService {

    private final static String databaseName = "local";

    private final DatabaseService DBService;

    public MessageService() {
        this.DBService = DatabaseService.getInstance(databaseName);
    }

    public List<Message> getMessagesByContactId(int contactId){
       return this.DBService.getMessagesByContactId( contactId);
    }

    public Message sendMessage( int receiverId, String content){
        return this.DBService.addMessage(0, receiverId, LocalDateTime.now(), content);
    }
}
