package com.iutclermont.lpmobile.localsportmeeting;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by Thomas on 28/11/2014.
 */
public class FragmentProgressBar extends Fragment implements View.OnClickListener {

    private boolean netWorkConnection;

    private ProgressBarCallback callback;

    public FragmentProgressBar() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progress_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!netWorkConnection) {
            getActivity().findViewById(R.id.imageProgress).setVisibility(View.GONE);
            getActivity().findViewById(R.id.textProgress).setVisibility(View.GONE);
            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
            getActivity().findViewById(R.id.textNoNetwork).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.retryButton).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.retryButton).setOnClickListener(this);
        }
    }

    public void setNetworkAvaible(boolean networkConnection,ProgressBarCallback callback ) {
        this.netWorkConnection = networkConnection;
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        callback.retryConnection ();
    }

    public interface ProgressBarCallback {
        public void retryConnection ();
    }
}
