package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


public class TopTenTracksFragment extends Fragment
{
    private static final String LOG_TAG = TopTenTracksTask.class.getSimpleName();

    private ArrayAdapter albumsAdapter;
    private ArrayAdapter songsAdapter;

    static SpotifyService spotifyService;
    private TopTenTracksAdapter topTenTracksAdapter;

    public TopTenTracksFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);

        ArrayList<Track> dummyData = new ArrayList<>();

        topTenTracksAdapter = new TopTenTracksAdapter(getActivity(), dummyData);

        ListView listView = (ListView) rootView.findViewById(R.id.top_ten_tracks_results);

        listView.setAdapter(topTenTracksAdapter);


        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {
            // Using getActivity().getActionBar() returns null. I don't know of any other way to
            // access the action bar's subtitle.
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            activity.getSupportActionBar().setSubtitle(intent.getStringExtra(Intent.EXTRA_TEXT));
        }

        if (intent != null && intent.hasExtra(Intent.EXTRA_UID))
        {
            new TopTenTracksTask().execute(intent.getStringExtra(Intent.EXTRA_UID));
        }

        return rootView;
    }

    public class TopTenTracksTask extends AsyncTask<String, Void, List<Track>>
    {
        protected List<Track> doInBackground(String... strings)
        {
            spotifyService = new SpotifyApi().getService();

            HashMap<String, Object> options = new HashMap<>();
            options.put("country", Locale.getDefault().getCountry());


            Tracks results = spotifyService.getArtistTopTrack(strings[0], options);

            if (results.tracks.size() == 0)
            {
                return null;
            }

            for (Track track : results.tracks)
            {
                Log.i(LOG_TAG, track.name);
            }

            return results.tracks;
        }

        protected void onPostExecute(List<Track> tracks)
        {
            if (tracks == null)
            {
                return;
            }

            topTenTracksAdapter.clear();

            for (Track track : tracks)
            {
                topTenTracksAdapter.add(track);
            }
        }
    }
}
