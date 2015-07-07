package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class TopTenTracksFragment extends Fragment
{
    private ArrayAdapter albumsAdapter;
    private ArrayAdapter songsAdapter;

    public TopTenTracksFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);

        //ArrayList<String> dummyAlbums = new ArrayList<>();
        //ArrayList<String> dummySongs  = new ArrayList<>();

        //for (int i = 1; i <= 20; i++)
        //{
        //    dummyAlbums.add("Album" + Integer.toString(i));
        //    dummySongs.add ("Song" + Integer.toString(i));
        //}

        //songsAdapter = new ArrayAdapter<> (getActivity(),
        //                                   R.layout.fragment_top_ten_tracks_result,
        //                                   R.id.fragment_top_ten_tracks_song,
        //                                   dummySongs);

        //albumsAdapter = new ArrayAdapter<> (getActivity(),
        //                                    R.layout.fragment_top_ten_tracks_result,
        //                                    R.id.fragment_top_ten_tracks_album,
        //                                    dummyAlbums);



        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {
            // Using getActivity().getActionBar() returns null. I don't know of any other way to
            // access the action bar's subtitle.
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            activity.getSupportActionBar().setSubtitle(intent.getStringExtra(Intent.EXTRA_TEXT));
        }

        return rootView;
    }
}
