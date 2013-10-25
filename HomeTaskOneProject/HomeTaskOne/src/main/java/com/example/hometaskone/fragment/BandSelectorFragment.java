package com.example.hometaskone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hometaskone.R;
import com.example.hometaskone.activity.AlbumSelectorActivity;

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
        String chosenBand = (String) l.getAdapter().getItem(position);
        String[] albums = getAlbums(position);

        Bundle bundle = new Bundle();
        bundle.putString("band", chosenBand);
        bundle.putStringArray("albums", albums);

        if(getResources().getBoolean(R.bool.isTablet)){
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container2, new AlbumSelectorFragment(chosenBand, albums));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Intent intent = new Intent(getActivity(), AlbumSelectorActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private String[] getAlbums(int position){
        switch(position){
            case 0:
                return new String[] {
                        "The Poison",
                        "Scream Aim Fire",
                        "Fever",
                        "Temper Temper"
                };
            case 1:
                return new String[] {
                        "Dying Is Your Latest Fashion",
                        "This War Is Ours",
                        "Escape the Fate",
                        "Ungrateful"
                };
            case 2:
                return new String[] {
                        "Stand Up and Scream",
                        "Reckless & Relentless",
                        "From Death To Destiny"
                };
            case 3:
                return new String[] {
                        "With Ears to See and Eyes to Hear",
                        "Let's Cheers to This",
                        "Feel"
                };
            case 4:
                return new String[] {
                        "Head Movies",
                        "Does",
                        "The Wall Eater"
                };
        }
        return null;
    }

}
