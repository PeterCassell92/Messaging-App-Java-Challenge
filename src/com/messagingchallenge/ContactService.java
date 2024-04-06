package com.messagingchallenge;

import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private static String databaseName = "local";

    private DatabaseService DBService;
    public void addNewContact(String name, String phoneNumber){
        this.DBService.addContact(name, phoneNumber);
    }

    public ContactService() {
        this.DBService = DatabaseService.getInstance(databaseName);
    }

    public List<ConversationPreview> getConversations(){
        List<ConversationPreview> conversationPreviews = new ArrayList<>();
        List<Contact> allContacts = this.DBService.getContactsSortedByName();
        for (Contact contact : allContacts){
            conversationPreviews.add(new ConversationPreview(contact, this.DBService.getMostRecentMessage(contact.getContact_id())));
        }
        return conversationPreviews;
    }

    public List<Contact> getAllContacts(){
        return this.DBService.getContactsSortedByName();
    }
}
