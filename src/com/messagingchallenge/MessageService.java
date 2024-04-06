package com.messagingchallenge;

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
}
