package com.messagingchallenge;

public class ContactService {

    private static String database;
    public void addNewContact(String name){
        new Contact( name, 3);
        //TODO - Database service add new contact
    }

    public ContactService() {
    }
}
