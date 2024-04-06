package com.messagingchallenge;

public class Contact {
    private String name;
    //the primary key
    private final int contact_id;

    private String phone_number;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact_id() {
        return contact_id;
    }


    public Contact( int contact_id, String name, String phone_number) {
        this.name = name;
        this.contact_id = contact_id;
        this.name = name;
        this.phone_number = phone_number;
    }
}
