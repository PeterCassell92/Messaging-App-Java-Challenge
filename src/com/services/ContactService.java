package com.services;

import com.models.Contact;
import com.models.ConversationPreview;

import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private static String databaseName = "local";

    private DatabaseService DBService;
    public void addNewContact(String name, String phoneNumber){
        this.DBService.addContact(name, phoneNumber);
    }

    public void deleteContact(Contact contact){
        this.DBService.deleteContact(contact.getContact_id());
        this.DBService.deleteMessagesWithContactId(contact.getContact_id());
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

    public Contact searchForContactByName(String name){
        List<Contact> contacts = this.DBService.getContactsSortedByName();

        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                return contact; // Found the contact, return it
            }
        }

        return null; // Contact not found
    }
}
