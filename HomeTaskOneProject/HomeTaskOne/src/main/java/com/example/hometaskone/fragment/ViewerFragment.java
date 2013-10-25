package com.example.hometaskone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hometaskone.R;

public class ViewerFragment extends Fragment {

    private String bandName;
    private String albumName;
    private int cover;

    public ViewerFragment(){}

    public ViewerFragment(String bandName, String albumName, int cover){
        this.bandName = bandName;
        this.albumName = albumName;
        this.cover = cover;
    }

    @Override
    public void onResume (){
        super.onResume();
        TextView a = (TextView)getActivity().findViewById(R.id.albumName);
        a.setText(albumName);
        TextView b = (TextView)getActivity().findViewById(R.id.bandName);
        b.setText(bandName);
        ImageView c = (ImageView)getActivity().findViewById(R.id.albumCover);
        c.setImageResource(cover);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewer, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            bandName = savedInstanceState.getString("bandName");
            albumName = savedInstanceState.getString("albumName");
            cover = savedInstanceState.getInt("cover");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("bandName", bandName);
        outState.putString("albumName", albumName);
        outState.putInt("cover", cover);
        super.onSaveInstanceState(outState);
    }

}
