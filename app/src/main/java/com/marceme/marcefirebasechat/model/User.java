package com.marceme.marcefirebasechat.model;

import com.google.firebase.database.Exclude;

/**
 * @author Marcelino Yax-marce7j@gmail.com-Android Developer
 * Created on 12/23/2016.
 */

public class User {

    private String displayName;
    private String email;
    private String connection;

    public String getImg_url() {
        return img_url;
    }

    private String img_url = "https://android.radiofoorti.fm/albumart/uploads/ic_avatar_blue.png";
    private String userType = "rj";
    private int avatarId;
    private long createdAt;

    private String mRecipientId;

    public User() {
    }

    public User(String displayName, String email, String connection, int avatarId, long createdAt, String userType, String img_url) {
        this.displayName = displayName;
        this.email = email;
        this.connection = connection;
        this.avatarId = avatarId;
        this.createdAt = createdAt;
        this.userType = userType;
        this.img_url = img_url;
    }


    public String createUniqueChatRef(long createdAtCurrentUser, String currentUserEmail) {
        String uniqueChatRef = "";
        if (createdAtCurrentUser > getCreatedAt()) {
            uniqueChatRef = cleanEmailAddress(currentUserEmail) + "-" + cleanEmailAddress(getUserEmail());
        } else {

            uniqueChatRef = cleanEmailAddress(getUserEmail()) + "-" + cleanEmailAddress(currentUserEmail);
        }
        return uniqueChatRef;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    private String cleanEmailAddress(String email) {
        //replace dot with comma since firebase does not allow dot
        return email.replace(".", "-");
    }

    private String getUserEmail() {
        //Log.e("user email  ", userEmail);
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getConnection() {
        return connection;
    }

    public int getAvatarId() {
        return avatarId;
    }

    @Exclude
    public String getRecipientId() {
        return mRecipientId;
    }

    public String getUserType() {
        return userType;
    }

    public void setRecipientId(String recipientId) {
        this.mRecipientId = recipientId;
    }
}
