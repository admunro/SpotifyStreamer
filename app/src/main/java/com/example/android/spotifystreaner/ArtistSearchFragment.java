package com.example.android.spotifystreaner;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchFragment extends Fragment
{
    private ArrayAdapter artistSearchResultsAdapter;

    public ArtistSearchFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container, false);

        artistSearchResultsAdapter = new ArrayAdapter<> (getActivity(),
                                                         R.layout.fragment_artist_result,
                                                         R.id.fragment_artist_result_name,
                                                         new ArrayList<String>());

        return inflater.inflate(R.layout.fragment_artist_search, container, false);
    }
}
