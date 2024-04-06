package com.models;

import com.models.Contact;
import com.models.Message;

public class ConversationPreview {
    private Contact contact;
    private Message displayMessage;

    public Contact getContact() {
        return contact;
    }

    public Message getDisplayMessage() {
        return displayMessage;
    }

    public ConversationPreview(Contact contact, Message displayMessage) {
        this.contact = contact;
        this.displayMessage = displayMessage;
    }
}
