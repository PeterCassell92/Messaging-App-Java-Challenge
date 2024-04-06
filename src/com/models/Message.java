package com.models;

import java.time.LocalDateTime;

public class Message {
    //The primary key
    private final int id;

    private final LocalDateTime sent;

    private final String content;

    private final int senderContactId;

    private final int receiverContactId;

    public int getId() {
        return id;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public String getContent() {
        return content;
    }

    public int getSenderContactId() {
        return senderContactId;
    }

    public int getReceiverContactId() {
        return receiverContactId;
    }

    public Message(int id,int senderContactId, int receiverContactId, LocalDateTime sent, String content) {
        this.id = id;
        this.sent = sent;
        this.content = content;
        this.senderContactId = senderContactId;
        this.receiverContactId = receiverContactId;
    }
}
