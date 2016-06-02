package com.ss.inlivo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ss.inlivo.R;
import com.ss.inlivo.activity.MainActivity;
import com.ss.inlivo.adapter.ChatAdapter;
import com.ss.inlivo.database.Message;
import com.ss.inlivo.database.controller.DatabaseController;
import com.ss.inlivo.listener.AdapterClickListener;

import java.util.List;
import java.util.Random;

/**
 * Created by NAME on 25.5.2016 г..
 */
public class FragmentChat extends Fragment implements AdapterClickListener{

    public static final String TAG = FragmentChat.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private ChatAdapter mAdapter;
    private List<Message> mData;

    private Button mChatSendButton;
    private EditText mMessageEdit;
    private Random mRandom;

    public static FragmentChat newInstance() {

        FragmentChat fragmentChat = new FragmentChat();

        return fragmentChat;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRandom = new Random();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);

        mData = DatabaseController.getInstance().loadAllMessages();
        mAdapter = new ChatAdapter(getActivity(), mData);
        mAdapter.setAdapterClickListener(this);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.chat_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        mChatSendButton = (Button) getView().findViewById(R.id.chatSendButton);
        mMessageEdit = (EditText) getView().findViewById(R.id.messageEdit);

        mChatSendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = mMessageEdit.getText().toString();

                if(message != null && !message.isEmpty()){
                    mData.add(DatabaseController.getInstance().addMessage(message, getRandomUserId()));
                    mAdapter.notifyDataSetChanged();

                    mMessageEdit.setText("");

                    mRecyclerView.scrollToPosition(mData.size() - 1);
                }
            }
        });
    }

    private long getRandomUserId(){

        return (long) (mRandom.nextInt(3) + 1);

    }

    @Override
    public void itemClicked(int position) {

        ((MainActivity)getActivity()).showUserProfileScreen(mData.get(position));
    }
}
