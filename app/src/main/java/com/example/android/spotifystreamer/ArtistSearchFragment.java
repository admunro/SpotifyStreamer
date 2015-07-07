package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        ArrayList<String> dummyData = new ArrayList<>();

        // A nice old-fashioned loop to ensure that this Fragment automatically scrolls.
        for (int i = 1; i <= 20; i++)
        {
            dummyData.add("Artist " + Integer.toString(i));
        }

        artistSearchResultsAdapter = new ArrayAdapter<> (getActivity(),
                                                         R.layout.fragment_artist_result,
                                                         R.id.fragment_artist_result_name,
                                                         dummyData);

        ListView listView = (ListView) rootView.findViewById(R.id.artist_search_results);

        listView.setAdapter(artistSearchResultsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String artist = artistSearchResultsAdapter.getItem(position).toString();

                Intent topTenTracks = new Intent(getActivity(), TopTenTracks.class);
                topTenTracks.putExtra(Intent.EXTRA_TEXT, artist);
                startActivity(topTenTracks);
            }
        });





        return rootView;
    }
}
