package com.marceme.marcefirebasechat.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Marcel on 11/7/2015.
 */
public class ChatMessage {

    private String message;
    private String sender;
    private String recipient;
    private long createdAt;

    private int mRecipientOrSenderStatus;

    public ChatMessage() {
    }

    public ChatMessage(String message, String sender, String recipient, long createdAt) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
        this.createdAt = createdAt;
    }


    public void setRecipientOrSenderStatus(int recipientOrSenderStatus) {
        this.mRecipientOrSenderStatus = recipientOrSenderStatus;
    }


    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Exclude
    public int getRecipientOrSenderStatus() {
        return mRecipientOrSenderStatus;
    }
}
