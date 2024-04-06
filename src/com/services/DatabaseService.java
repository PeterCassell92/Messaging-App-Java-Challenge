package com.services;

import com.models.Contact;
import com.models.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A DatabaseService class that emulates the functionality of an SQL database
 * Each list represents a database table
 */
public class DatabaseService {

    private String name;
    private static DatabaseService instance;

    public static synchronized DatabaseService getInstance(String name){
        if(null == instance){
            instance = new DatabaseService(name);
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private List<Contact> contacts;
    private int maxContactId = 0;

    private List<Message> messages;

    private int maxMessageId = 0;

    private DatabaseService(String name){
        //load initial data. This would be drawing down from an SQL database in a full system.
        // Initialize lists
        contacts = new ArrayList<>();
        messages = new ArrayList<>();

        // Populate initial data for contacts
        //If this were a persistent database then we would be loading this in from the db at this point.
        Contact me = this.addContact("Me", "07322422398");
        Contact robert = this.addContact("Robert", "07820209181");
        Contact jennifer = this.addContact("Jennifer", "07190198711");
        Contact sinead = this.addContact("Sinead", "07598398123");
        Contact max = this.addContact("Maximilian", "07594398022");


        // Populate initial data for messages
        LocalDateTime currentTime = LocalDateTime.now();
        this.addMessage(me.getContact_id(), robert.getContact_id(), currentTime.minusHours(8), "Hey Robert, how are you?");
        this.addMessage(robert.getContact_id(), me.getContact_id(), currentTime.minusHours(5), "Hi Me! I'm doing well, thank you.");
        this.addMessage(me.getContact_id(), robert.getContact_id(), currentTime.minusHours(2), "That's great to hear!");
        this.addMessage(robert.getContact_id(), me.getContact_id(), currentTime.minusHours(1), "How about you?");
        this.addMessage(me.getContact_id(), robert.getContact_id(), currentTime.minusHours(1).plusMinutes(15), "I'm doing fine too, thanks for asking.");

        // Conversation with Jennifer
        this.addMessage(me.getContact_id(), jennifer.getContact_id(), currentTime.minusDays(2).minusHours(3), "Hi Jennifer, how have you been?");
        this.addMessage(jennifer.getContact_id(), me.getContact_id(), currentTime.minusDays(2).minusHours(2), "Hey Me! I'm doing good, thanks. How about you?");
        this.addMessage(me.getContact_id(), jennifer.getContact_id(), currentTime.minusDays(2).minusHours(1), "I'm doing alright. Just busy with work. How was your weekend?");
        this.addMessage(jennifer.getContact_id(), me.getContact_id(), currentTime.minusDays(1).minusHours(23), "My weekend was great! I went hiking with friends. What about you?");
        this.addMessage(me.getContact_id(), jennifer.getContact_id(), currentTime.minusDays(1).minusHours(22), "That sounds fun! I wish I could've joined. Maybe next time.");

        // Conversation with Sinead
        this.addMessage(me.getContact_id(), sinead.getContact_id(), currentTime.minusDays(5).minusHours(2), "Hey Sinead, how's it going?");
        this.addMessage(sinead.getContact_id(), me.getContact_id(), currentTime.minusDays(5).minusHours(1), "Hi Me! I'm doing well, thanks. What about you?");
        this.addMessage(me.getContact_id(), sinead.getContact_id(), currentTime.minusDays(4).minusHours(23), "I'm doing okay. Just dealing with some work stress. How was your day?");
        this.addMessage(sinead.getContact_id(), me.getContact_id(), currentTime.minusDays(3).minusHours(23), "My day was busy as usual, but nothing too exciting. How's work going?");
        this.addMessage(me.getContact_id(), sinead.getContact_id(), currentTime.minusDays(3).minusHours(22), "Work's been hectic, but I'm managing. Looking forward to the weekend!");

        // Conversation with Max
        this.addMessage(me.getContact_id(), max.getContact_id(), currentTime.minusDays(10).minusHours(3), "Hey Max, how have you been?");
        this.addMessage(max.getContact_id(), me.getContact_id(), currentTime.minusDays(10).minusHours(2), "Hi Me! I've been good, thanks. How about you?");
        this.addMessage(me.getContact_id(), max.getContact_id(), currentTime.minusDays(9).minusHours(23), "I'm doing alright. Just trying to stay productive. How's your project coming along?");
        this.addMessage(max.getContact_id(), me.getContact_id(), currentTime.minusDays(9).minusHours(22), "My project is going well. Making steady progress. We should catch up sometime!");

    }

    public Contact addContact(String name, String phoneNumber){
        Contact contact = new Contact(maxContactId++, name, phoneNumber);
        contacts.add(contact);
        return contact;
    }

    public Message addMessage(int senderId, int receiverId, LocalDateTime sent, String content){
        Message message = new Message(maxMessageId++, senderId, receiverId, sent, content );
        messages.add(message);
        return message;
    }

    public void deleteContact(int contactId) {
        // Remove the contact with the specified contactId
        contacts.removeIf(contact -> contact.getContact_id() == contactId);
    }

    public void deleteMessagesWithContactId(int contactId) {
        // Remove all messages associated with the specified contactId
        messages.removeIf(message -> message.getSenderContactId() == contactId || message.getReceiverContactId() == contactId);
    }

    /**
     * Get all contacts
     * @return List<Contact> a list of all contacts excluding "me"
     */
    public List<Contact> getContactsSortedByName() {
        List<Contact> sortedContacts = new ArrayList<>(contacts);
        Collections.sort(sortedContacts, Comparator.comparing(Contact::getName));

        // Remove the entry where contact_id == 1
        sortedContacts.removeIf(contact -> contact.getContact_id() == 0);


        return sortedContacts;
    }

    //This sort of query would be much more efficient in SQL.
    public Message getMostRecentMessage(int contactId) {
        Message mostRecentMessage = null;
        for (Message message : messages) {
            if (message.getSenderContactId() == contactId || message.getReceiverContactId() == contactId) {
                if (mostRecentMessage == null || message.getSent().isAfter(mostRecentMessage.getSent())) {
                    mostRecentMessage = message;
                }
            }
        }
        return mostRecentMessage;
    }

    public List<Message> getMessagesByContactId(int contactId) {
        List<Message> filteredMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSenderContactId() == contactId || message.getReceiverContactId() == contactId) {
                filteredMessages.add(message);
            }
        }
        return filteredMessages;
    }
}
