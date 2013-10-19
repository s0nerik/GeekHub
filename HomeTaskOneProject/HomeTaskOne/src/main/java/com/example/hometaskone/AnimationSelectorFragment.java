package com.example.hometaskone;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class AnimationSelectorFragment extends ListFragment {

    Animation animation;
    ImageView sampleImage;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.animation_selector, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"Fade In", "Fade Out", "Zoom In", "Zoom Out"});
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch(position){
            case 0:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                break;
            case 1:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                break;
            case 2:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
                break;
            case 3:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out);
                break;
        }
        sampleImage = (ImageView) getActivity().findViewById(R.id.sampleImage);
        sampleImage.startAnimation(animation);
    }
}
