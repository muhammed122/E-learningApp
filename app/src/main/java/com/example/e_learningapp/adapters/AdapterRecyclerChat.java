package com.example.e_learningapp.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningapp.R;
import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelChat;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerChat extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<ModelChat> mMessageList;

    public AdapterRecyclerChat(Context context, List<ModelChat> mMessageList) {
       this.mContext = context;
       this.mMessageList = mMessageList;
    }
    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
    @Override
    public int getItemViewType(int position) {
        ModelChat message = (ModelChat) mMessageList.get(position);
        //current id you save in phone from firbase-------------------------->
        if (message.getSenderId().equals("current id")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModelChat message = (ModelChat) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }
    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_me, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_other, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }
    // Passes the message object to a ViewHolder so that the contents can be bound to UI.

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_gchat_message_me);
        }

        void bind(ModelChat message) {
            messageText.setText(message.getMassage());


        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText =itemView.findViewById(R.id.text_gchat_message_other);
        }
        void bind(ModelChat message) {
            messageText.setText(message.getMassage());

        }
    }

}
