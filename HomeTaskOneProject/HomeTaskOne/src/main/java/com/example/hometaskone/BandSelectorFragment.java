package com.example.hometaskone;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BandSelectorFragment extends ListFragment {

    private String bands[] = new String[] { "Bullet For My Valentine", "Escape The Fate", "Asking Alexandria", "Sleeping With Sirens", "nobody.one" };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, bands);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.band_selector, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        AlbumSelectorFragment albumSelector = null;
        String chosenBand = (String) l.getAdapter().getItem(position);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch(position){
            case 0:
                albumSelector = new AlbumSelectorFragment(chosenBand, new String[] {"The Poison", "Scream Aim Fire", "Fever", "Temper Temper"});
                break;
            case 1:
                albumSelector = new AlbumSelectorFragment(chosenBand, new String[] {"Dying Is Your Latest Fashion", "This War Is Ours", "Escape the Fate", "Ungrateful"});
                break;
            case 2:
                albumSelector = new AlbumSelectorFragment(chosenBand, new String[] {"Stand Up and Scream", "Reckless & Relentless", "From Death To Destiny"});
                break;
            case 3:
                albumSelector = new AlbumSelectorFragment(chosenBand, new String[] {"With Ears to See and Eyes to Hear", "Let's Cheers to This", "Feel"});
                break;
            case 4:
                albumSelector = new AlbumSelectorFragment(chosenBand, new String[] {"Head Movies", "Does", "The Wall Eater"});
                break;
        }
        fragmentTransaction.replace(R.id.container2, albumSelector);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
