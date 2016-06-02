package com.ss.inlivo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss.inlivo.R;
import com.ss.inlivo.database.Message;
import com.ss.inlivo.listener.AdapterClickListener;
import com.ss.inlivo.widget.CircullarImage;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 26.5.2016 г..
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context mContext;
    private List<Message> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private AdapterClickListener mAdapterClickListener;

    public ChatAdapter(Context context, List<Message> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_chat_screen, null);

        ChatViewHolder holder = new ChatViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        if (mData != null){
            Message message = mData.get(position);

            holder.userImg.setImageResource(mContext.getResources().getIdentifier(message.getUser().getUserProfilePicture(), "drawable", mContext.getPackageName()));

            holder.userMessage.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircullarImage userImg;

        TextView userMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);

            userImg = (CircullarImage) itemView.findViewById(R.id.user_image);
            userImg.setOnClickListener(this);

            userMessage = (TextView) itemView.findViewById(R.id.user_message);

        }

        @Override
        public void onClick(View v) {
            if(mAdapterClickListener != null){
                mAdapterClickListener.itemClicked(getLayoutPosition());
            }
        }
    }

    public void setAdapterClickListener(AdapterClickListener mAdapterClickListener) {

        this.mAdapterClickListener = mAdapterClickListener;
    }
}
