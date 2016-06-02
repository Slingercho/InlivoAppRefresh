package com.ss.inlivo.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ss.inlivo.R;
import com.ss.inlivo.database.Message;
import com.ss.inlivo.database.controller.DatabaseController;
import com.ss.inlivo.fragment.DialogFragmentLoading;
import com.ss.inlivo.fragment.controller.FragmentController;
import com.ss.inlivo.listener.OnTaskCompletedListener;
import com.ss.inlivo.model.HealthIndex;
import com.ss.inlivo.model.Vitamins;
import com.ss.inlivo.network.HttpGetUserVitaminsTask;

public class MainActivity extends AppCompatActivity {

    public static final String USER_MODE = "user";
    public static final String HEALTH_MODE="health";

    private Message mCurrentMessage;
    private Vitamins mVitamins = new Vitamins();
    private HealthIndex mHealthIndex = new HealthIndex();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentController.init(this);
        DatabaseController.init(this);

        FragmentController.getInstance().showChatScreen(false, false);
        FragmentController.getInstance().showFooterScreen(false, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DatabaseController.getInstance().clearDB();

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void showUserProfileScreen(Message message){

        setCurrentMessage(message);

        FragmentController.getInstance().showUserProfileScreen(false, false);
    }

    public Message getCurrentMessage() {

        return mCurrentMessage;
    }

    public void setCurrentMessage(Message mCurrentMessage) {

        this.mCurrentMessage = mCurrentMessage;
    }


    public Vitamins getVitamins() {
        return mVitamins;
    }

    public void setVitamins(Vitamins mVitamins) {
        this.mVitamins = mVitamins;
    }

    public HealthIndex getHealthIndex() {
        return mHealthIndex;
    }

    public void setHealthIndex(HealthIndex mHealthIndex) {
        this.mHealthIndex = mHealthIndex;
    }

    public void getUserVitaminsTask(String mode, String userID, OnTaskCompletedListener onTaskCompletedListener){

        new HttpGetUserVitaminsTask(this, onTaskCompletedListener).execute(mode, userID);
    }

    public void showLoadingDialog()
    {
        DialogFragmentLoading.newInstance().show(getSupportFragmentManager(), DialogFragmentLoading.TAG);
    }

    public void hideLoadingDialog()
    {

        Fragment loadingFragment = getSupportFragmentManager().findFragmentByTag(DialogFragmentLoading.TAG);

        if(loadingFragment != null)
        {
            ((DialogFragmentLoading) loadingFragment).dismiss();
        }
    }

}
