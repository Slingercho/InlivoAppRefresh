package com.ss.inlivo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss.inlivo.R;
import com.ss.inlivo.fragment.controller.FragmentController;

/**
 * Created by NAME on 25.5.2016 г..
 */
public class FragmentFooter extends Fragment{

    public static final String TAG = FragmentFooter.class.getSimpleName();

    private TextView tvChat, tvState;


    public static FragmentFooter newInstance() {

        FragmentFooter fragmentFooter = new FragmentFooter();

        return fragmentFooter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_footer, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvChat = (TextView) getView().findViewById(R.id.tvChat);
        tvChat.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_focused_txt));
        tvState = (TextView) getView().findViewById(R.id.tvHealth);

        View chatContainer = getView().findViewById(R.id.chat_container);
        chatContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatScreen();
            }
        });

        View healthContainer = getView().findViewById(R.id.health_container);
        healthContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatsScreen();
            }
        });
    }

    private void showStatsScreen(){
        tvChat.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        tvState.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_focused_txt));
        FragmentController.getInstance().showStatsScreen(false, false);
    }

    private void showChatScreen(){
        tvChat.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_focused_txt));
        tvState.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        FragmentController.getInstance().showChatScreen(false, false);
    }
}
