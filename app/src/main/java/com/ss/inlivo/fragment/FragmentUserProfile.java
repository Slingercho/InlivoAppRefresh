package com.ss.inlivo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.ss.inlivo.R;
import com.ss.inlivo.activity.MainActivity;
import com.ss.inlivo.database.Message;
import com.ss.inlivo.fragment.controller.ChartController;
import com.ss.inlivo.listener.OnTaskCompletedListener;
import com.ss.inlivo.widget.CircullarImage;

/**
 * Created by NAME on 25.5.2016 г..
 */
public class FragmentUserProfile extends Fragment implements OnTaskCompletedListener{

    public static final String TAG = FragmentUserProfile.class.getSimpleName();

    private CircullarImage mUserPic;
    private TextView mUserName;
    private Message mCurrentMessageInfo;
    private BarChart mBarChart;

    public static FragmentUserProfile newInstance() {

        FragmentUserProfile fragmentUserProfile = new FragmentUserProfile();

        return fragmentUserProfile;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUserPic = (CircullarImage) getView().findViewById(R.id.userImg);
        mUserName = (TextView) getView().findViewById(R.id.userName);
        mCurrentMessageInfo = ((MainActivity) getActivity()).getCurrentMessage();
        mBarChart = (BarChart) getView().findViewById(R.id.userVitaminChart);

        if (mCurrentMessageInfo != null) {

            mUserPic.setImageResource(getActivity().getResources().getIdentifier(mCurrentMessageInfo.getUser().getUserProfilePicture(), "drawable", getActivity().getPackageName()));
            mUserName.setText(mCurrentMessageInfo.getUser().getUserName());

            ((MainActivity) getActivity()).getUserVitaminsTask(MainActivity.USER_MODE, mCurrentMessageInfo.getUser().getId().toString(), this);

        } else {
            throw new RuntimeException("Shouldn't be null.");
        }

    }


    @Override
    public void onTaskCompleted() {

        mBarChart.setData(ChartController.getInstance((MainActivity) getActivity()).generateChart(((MainActivity) getActivity()).getVitamins()));

        mBarChart.animateY(2000);
    }
}
