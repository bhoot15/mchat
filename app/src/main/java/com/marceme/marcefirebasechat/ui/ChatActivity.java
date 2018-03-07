package com.marceme.marcefirebasechat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marceme.marcefirebasechat.FireChatHelper.ExtraIntent;
import com.marceme.marcefirebasechat.R;
import com.marceme.marcefirebasechat.adapter.ChatMessageChatAdapter;
import com.marceme.marcefirebasechat.model.ChatMessage;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends Activity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    @BindView(R.id.recycler_view_chat)
    RecyclerView mChatRecyclerView;
    @BindView(R.id.edit_text_message)
    EditText mUserMessageChatText;


    private String mRecipientId;
    private String mCurrentUserId;
    private long createdAt;
    private ChatMessageChatAdapter chatMessageChatAdapter;
    private DatabaseReference messageChatDatabase;
    private ChildEventListener messageChatListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_chat);

        bindButterKnife();
        setDatabaseInstance();
        setUsersId();
        setChatRecyclerView();
    }

    private void bindButterKnife() {
        ButterKnife.bind(this);
    }

    private void setDatabaseInstance() {
        String chatRef = getIntent().getStringExtra(ExtraIntent.EXTRA_CHAT_REF);
        messageChatDatabase = FirebaseDatabase.getInstance().getReference().child(chatRef);
    }

    private void setUsersId() {
        mRecipientId = getIntent().getStringExtra(ExtraIntent.EXTRA_RECIPIENT_ID);
        mCurrentUserId = getIntent().getStringExtra(ExtraIntent.EXTRA_CURRENT_USER_ID);
    }

    private void setChatRecyclerView() {
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setHasFixedSize(true);
        chatMessageChatAdapter = new ChatMessageChatAdapter(new ArrayList<ChatMessage>());
        mChatRecyclerView.setAdapter(chatMessageChatAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        messageChatListener = messageChatDatabase.limitToLast(20).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {

                if (dataSnapshot.exists()) {
                    ChatMessage newMessage = dataSnapshot.getValue(ChatMessage.class);
                    if (newMessage.getSender().equals(mCurrentUserId)) {
                        newMessage.setRecipientOrSenderStatus(ChatMessageChatAdapter.SENDER);
                    } else {
                        newMessage.setRecipientOrSenderStatus(ChatMessageChatAdapter.RECIPIENT);
                    }
                    chatMessageChatAdapter.refillAdapter(newMessage);
                    mChatRecyclerView.scrollToPosition(chatMessageChatAdapter.getItemCount() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        if (messageChatListener != null) {
            messageChatDatabase.removeEventListener(messageChatListener);
        }
        chatMessageChatAdapter.cleanUp();

    }

    @OnClick(R.id.btn_send_message)
    public void btnSendMsgListener(View sendButton) {

        String senderMessage = mUserMessageChatText.getText().toString().trim();

        if (!senderMessage.isEmpty()) {

            createdAt = new Date().getTime();

            ChatMessage newMessage = new ChatMessage(senderMessage, mCurrentUserId, mRecipientId,createdAt);
            messageChatDatabase.push().setValue(newMessage);

            mUserMessageChatText.setText("");
        }
    }


}
