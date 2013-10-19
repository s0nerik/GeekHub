package com.example.hometaskone;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AlbumSelectorFragment extends ListFragment {

    private String albums[] = {};
    private String band = "";

    public AlbumSelectorFragment(){}

    public AlbumSelectorFragment(String band, String[] albums){
        this.albums = albums;
        this.band = band;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            band = savedInstanceState.getString("band");
            albums = (String[]) savedInstanceState.getSerializable("albums");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, albums);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.album_selector, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String album = (String) l.getAdapter().getItem(position);
        ViewerFragment viewer = new ViewerFragment(band, album, getAlbumCover(album));

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container1, viewer, "album_viewer");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("albums", albums);
        outState.putString("band", band);
        super.onSaveInstanceState(outState);
    }

    private int getAlbumCover(String album){
        if (album.equals("The Poison")) {return R.drawable.poison;} else
        if (album.equals("Scream Aim Fire")) {return R.drawable.scream_aim_fire;} else
        if (album.equals("Fever")) {return R.drawable.fever;} else
        if (album.equals("Temper Temper")) {return R.drawable.temper_temper;} else
        if (album.equals("Dying Is Your Latest Fashion")) {return R.drawable.dying_is_your_latest_fashion;} else
        if (album.equals("This War Is Ours")) {return R.drawable.this_war_is_ours;} else
        if (album.equals("Escape the Fate")) {return R.drawable.escape_the_fate;} else
        if (album.equals("Ungrateful")) {return R.drawable.ungrateful;}else
        if (album.equals("Stand Up and Scream")) {return R.drawable.stand_up_and_scream;} else
        if (album.equals("Reckless & Relentless")) {return R.drawable.reckless_and_relentless;} else
        if (album.equals("From Death To Destiny")) {return R.drawable.from_death_to_destiny;} else
        if (album.equals("With Ears to See and Eyes to Hear")) {return R.drawable.with_ears_to_see_and_eyes_to_hear;} else
        if (album.equals("Let's Cheers to This")) {return R.drawable.lets_cheers_to_this;} else
        if (album.equals("Feel")) {return R.drawable.feel;} else
        if (album.equals("Head Movies")) {return R.drawable.head_movies;} else
        if (album.equals("Does")) {return R.drawable.does;} else
        if (album.equals("The Wall Eater")) {return R.drawable.the_wall_eater;}
        return R.drawable.ic_launcher;
    }

}
