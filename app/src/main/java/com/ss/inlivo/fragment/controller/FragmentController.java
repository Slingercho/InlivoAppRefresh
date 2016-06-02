package com.ss.inlivo.fragment.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ss.inlivo.R;
import com.ss.inlivo.activity.MainActivity;
import com.ss.inlivo.fragment.FragmentChat;
import com.ss.inlivo.fragment.FragmentFooter;
import com.ss.inlivo.fragment.FragmentStats;
import com.ss.inlivo.fragment.FragmentUserProfile;

/**
 * Created by NAME on 25.5.2016 г..
 */
public class FragmentController {

    private static FragmentController instance;

    private FragmentManager mFragmentManager;

    private int mContainerId;
    private int mFooterId;

    public static void init(MainActivity mainActivity) {
        if (instance == null) {
            synchronized (FragmentController.class) {
                if (instance == null) {
                    instance = new FragmentController(mainActivity.getSupportFragmentManager());
                }
            }
        }
    }

    public static FragmentController getInstance() {

        if (instance == null) {
            throw new RuntimeException("Shouldn't be null. Init it on Activity creation");
        }

        return instance;
    }


    private FragmentController(FragmentManager fragmentManager) {

        this.mFragmentManager = fragmentManager;
        this.mContainerId = R.id.main_container;
        this.mFooterId = R.id.footer_container;
    }

    public void showChatScreen(boolean addToBackStack, boolean clearBackStack){
        showScreen(mContainerId, FragmentChat.newInstance(), FragmentChat.TAG, addToBackStack, clearBackStack);
    }

    public void showStatsScreen(boolean addToBackStack, boolean clearBackStack){
        showScreen(mContainerId, FragmentStats.newInstance(), FragmentStats.TAG, addToBackStack, clearBackStack);
    }

    public void showUserProfileScreen(boolean addToBackStack, boolean clearBackStack){
        showScreen(mContainerId, FragmentUserProfile.newInstance(), FragmentUserProfile.TAG, addToBackStack, clearBackStack);
    }

    public void showFooterScreen(boolean addToBackStack, boolean clearBackStack){
        showScreen(mFooterId, FragmentFooter.newInstance(), FragmentFooter.TAG, addToBackStack, clearBackStack);
    }

    private void showScreen(int mainContainerId, Fragment content,
                            String contentTag, boolean addToBackStack, boolean clearBackStack) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        ft.replace(mainContainerId, content, contentTag);

        if (clearBackStack) {
            mFragmentManager.popBackStackImmediate(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        if (addToBackStack) {
            ft.addToBackStack(String.valueOf(System.identityHashCode(content)));
        }

        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }
}
