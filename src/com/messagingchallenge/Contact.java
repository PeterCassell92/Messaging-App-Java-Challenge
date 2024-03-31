package com.messagingchallenge;

public class Contact {
    private String name;
    private int contact_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public Contact(String name, int contact_id) {
        this.name = name;
        this.contact_id = contact_id;
    }
}
