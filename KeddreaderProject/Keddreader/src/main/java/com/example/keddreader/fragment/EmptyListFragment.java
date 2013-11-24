package com.example.keddreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keddreader.R;
import com.example.keddreader.model.Connectivity;

public class EmptyListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_list, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        RefreshFragment refreshFragment = (RefreshFragment) fragmentManager.findFragmentById(R.id.refresh_fragment);
        NoConnectionFragment noConnectionFragment = (NoConnectionFragment) fragmentManager.findFragmentById(R.id.no_connection_fragment);

        if(!Connectivity.isConnected(getActivity())){
            refreshFragment.getView().setVisibility(View.GONE);
            noConnectionFragment.getView().setVisibility(View.VISIBLE);
        }else{
            refreshFragment.getView().setVisibility(View.VISIBLE);
            noConnectionFragment.getView().setVisibility(View.GONE);
        }
    }
}
